package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import java.io.File;

import com.lucas.ferreira.maburn.controller.title.download.ContentDownloadList;
import com.lucas.ferreira.maburn.controller.title.download.cards.*;
import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardIconVisibility;
import com.lucas.ferreira.maburn.controller.title.download.cards.icons.DownloadCardInteractIcons;
import com.lucas.ferreira.maburn.exceptions.InitializeIconsException;
import com.lucas.ferreira.maburn.model.DeleteFile;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.download.item.EpisodeHLSDownload;
import com.lucas.ferreira.maburn.model.effects.DefaultAnimationCard;
import com.lucas.ferreira.maburn.model.media.EpisodeDirectoryModel;

public class EpisodeCardController implements DownloadCardController {

    private EpisodeCard episodeCard;
    private EpisodeCardIcons cardIcons;
    private DefaultAnimationCard defaultAnimationCard;
    private DownloadValues downloadValues;
    /*
    private EpisodeDownloadItemValues episodeDownloadItemValues;
    private EpisodeFragmentedDownloadValues episodeFragmentedDownloadValues;
    */
    private EpisodeDownload episodeDownload;
    private DownloadInfo downloadInfo;
    private ContentDownloadList contentDownloadList;

    public EpisodeCardController(EpisodeCard episodeCard, DownloadInfo downloadInfo,
                                 ContentDownloadList contentDownloadList) {
        this.episodeCard = episodeCard;
        this.downloadInfo = downloadInfo;
        this.contentDownloadList = contentDownloadList;
    }

    public DownloadCardFull initialize() {

        initializeValuesCard();
        try {
            initializeIcons();
        } catch (InitializeIconsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DownloadCardFull downloadCardFull;
        if (downloadInfo.getListUrls().size() > 1) {
            downloadCardFull = new EpisodeCardFull<EpisodeFragmentedDownloadValues>();
        } else {
            downloadCardFull = new EpisodeCardFull<EpisodeDownloadItemValues>();
        }
        downloadCardFull.setCard(episodeCard);
        downloadCardFull.setCardController(this);
        downloadCardFull.setCardValues(downloadValues);

        DownloadCardInteractIcons downloadCardInteractIcons = new DownloadCardInteractIcons(this, episodeCard);
        downloadCardInteractIcons.interactTurnOn();
        DownloadCardIconVisibility episodeCardIconVisibility = new DownloadCardIconVisibility(
                downloadCardInteractIcons);
        episodeCardIconVisibility.onDownloadState(downloadValues.getDownloadProgressState());
        initializeAnimations();
        return downloadCardFull;
    }

    public void initializeValuesCard() {
        EpisodeCardValuesBinder episodeCardValuesBinder;

        if (downloadInfo.getListUrls().size() > 1) {
            downloadValues = new EpisodeFragmentedDownloadValues();
            downloadValues.getTarget().set(downloadInfo.getUrl());
            episodeCardValuesBinder = new EpisodeCardValuesBinder<EpisodeFragmentedDownloadValues>();
        } else {
            downloadValues = new EpisodeDownloadItemValues();
            ((EpisodeDownloadItemValues) downloadValues).getDirectLink().set(downloadInfo.getUrl());
            downloadValues.getTarget().set(downloadInfo.getUrl());

            episodeCardValuesBinder = new EpisodeCardValuesBinder<EpisodeDownloadItemValues>();
        }

        downloadValues.getName().set(downloadInfo.getFilename());
        episodeCardValuesBinder.binder(episodeCard, downloadValues);

    }

    private void initializeIcons() throws InitializeIconsException {
        cardIcons = new EpisodeCardIcons(episodeCard, this);
        try {
            cardIcons.initialize();
        } catch (Exception e) {
            System.err.println();
            e.printStackTrace();
            throw new InitializeIconsException();
        }
    }

    private void initializeAnimations() {
        defaultAnimationCard = new DefaultAnimationCard(episodeCard);
        defaultAnimationCard.initAnimationCard();
    }

    public void initializeDownload() throws Exception {


        if (downloadInfo.getListUrls().size() > 1) {
            downloadInfo.getListUrls().forEach(link -> {
                PartDownloadItemsValues partDownloadItemsValues = new PartDownloadItemsValues();
                partDownloadItemsValues.getDirectLink().set(link);
                ((EpisodeFragmentedDownloadValues) downloadValues).getListItemsDownloadValues().add(partDownloadItemsValues);
            });
            episodeDownload = new EpisodeHLSDownload(downloadValues, downloadInfo);
        } else {
            episodeDownload = new EpisodeDownload(downloadInfo, downloadValues);
        }

        episodeDownload.download();

    }

    @Override
    public void start() throws Exception {
        initializeDownload();

    }

    @Override
    public void resume() {
        episodeDownload.resume();
    }

    @Override
    public void pause() {
        episodeDownload.pause();
    }

    @Override
    public void stop() {
        new Thread(() -> {
            episodeDownload.stop();
            resetValues();
        }).start();

    }

    @Override
    public void openFolder() {
        EpisodeDirectoryModel episodeDirectoryModel = new EpisodeDirectoryModel(downloadInfo);
        episodeDirectoryModel.openFolder();

    }

    @Override
    public void openTitleMedia() {
        EpisodeDirectoryModel episodeDirectoryModel = new EpisodeDirectoryModel(downloadInfo);
        episodeDirectoryModel.openFile();
    }

    private void resetValues() {
        downloadValues.getDownloadSpeed().set(0);
        downloadValues.getTimeRemain().set(0);
    }

    @Override
    public void remove() {
        contentDownloadList.removeCard(episodeCard);

        String filePath = downloadInfo.getRoot() + "\\" + downloadInfo.getFilename() + "."
                + downloadInfo.getPrefFiletype().getName();
        DeleteFile deleteFile = new DeleteFile();
        deleteFile.delete(filePath);

    }

    @Override
    public void refresh() throws Exception {
        initializeDownload();
    }

}
