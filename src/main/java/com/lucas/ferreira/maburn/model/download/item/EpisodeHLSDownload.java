package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PartDownloadItemsValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeFragmentedDownloadValues;
import com.lucas.ferreira.maburn.exceptions.ChapterDownloadException;
import com.lucas.ferreira.maburn.exceptions.VideoComposerException;
import com.lucas.ferreira.maburn.model.VideoComposer;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;
import com.microsoft.playwright.Video;
import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EpisodeHLSDownload extends EpisodeDownload {

    private static final int NUMBER_PAGES_THREADS = 5;

    private String folderPath;
    private DownloadInfo episodeDownloadInfo;
    private EpisodeFragmentedDownloadValues episodeDownloadItemValues;
    private List<PartDownload> partDownloads = new ArrayList<>();
    private List<PartDownloadItemsValues> partValues = new ArrayList<>();

    private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();
    private EpisodeInfoRefresher episodeInfoRefresher;

    private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_PAGES_THREADS);

    public EpisodeHLSDownload(DownloadValues downloadValues, DownloadInfo episodeDownloadInfo) {
        this.episodeDownloadItemValues = (EpisodeFragmentedDownloadValues) downloadValues;
        this.episodeDownloadInfo = episodeDownloadInfo;
        episodeInfoRefresher = newEpisodeInfoRefresher();

    }

    @Override
    public void download() throws ChapterDownloadException {
        try {
            createEpisodeFragmentsFolder();
            List<ItemDownloadValues> transportStreamDownloadValues = episodeDownloadItemValues.getListItemsDownloadValues();
            forEachTs(transportStreamDownloadValues);
            waitUntilDownloadFinish();
            onDownloadComplete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChapterDownloadException(e.getMessage());
        }

    }

    private void waitUntilDownloadFinish() {
        episodeDownloadItemValues.getDirectLink().set(episodeDownloadInfo.getUrl());
        new Thread(() -> {
            updateAllValues();
        }).start();

        partValues.get(0).getDownloadProgressState().addListener((obs, oldvalue, newvalue) ->{
            if(newvalue == DownloadProgressState.COMPLETED){
                PartDownload partDownload = partDownloads.get(0);
                VideoMetadata videoMetadata = partDownload.readPartMetadata();
                episodeDownloadItemValues.getResolution().set(videoMetadata.getHeight());
            }
        });

        while (episodeDownloadItemValues.getObsListNewPartDownloadItemsValues().size() < episodeDownloadItemValues
                .getListItemsDownloadValues().size()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    private EpisodeInfoRefresher newEpisodeInfoRefresher() {
        return new EpisodeInfoRefresher(episodeDownloadItemValues);
    }

    public void createEpisodeFragmentsFolder() {
        folderPath = episodeDownloadInfo.getRoot() + "\\" + episodeDownloadInfo.getFilename() + "\\";

        File file = new File(folderPath);
        file.mkdir();
    }

    public void deleteEpisodeFragmentsFolder(File folder) {

        File[] allContents = folder.listFiles();
        Arrays.asList(allContents).forEach(file -> file.delete());
        folder.delete();

    }

    public String fragmentNameForPosition(int position) {
        String name = "ts-";
        if (position <= 9) {
            name += "0";
        }
        name += String.valueOf(position);
        return name;
    }

    private void forEachTs(List<ItemDownloadValues> transportStream) {

        for (int tsPosition = 0; tsPosition < transportStream.size(); tsPosition++) {
            PartDownloadItemsValues fragment = (PartDownloadItemsValues) transportStream.get(tsPosition);

            DownloadInfo fragmentDownloadInfo = fillFragmentDownloadInfo(tsPosition, fragment);

            PartDownload partDownload = new PartDownload(fragment, fragmentDownloadInfo);
            partDownload.onDownloadComplete(downloadedFragment -> {
                synchronized (this) {
                    readSize(partDownload.readPartSize(), downloadedFragment);
                    notifyPartDownloadComplete(downloadedFragment);
                    checkState();
                }

            });
            partDownload.onDownloadFailed(downloadedPage -> notifyPartDownloadFailed(fragment));

            Thread thDownloadPage = new Thread(() -> downloadPart(partDownload));
            executorService.execute(thDownloadPage);
            partDownloads.add(partDownload);
            partValues.add(fragment);
        }

    }

    private void checkState() {


        if (episodeDownloadItemValues.getDownloadProgressState().get() != DownloadProgressState.FAILED
                && episodeDownloadItemValues.getDownloadProgressState().get() != DownloadProgressState.CANCELED) {

            boolean allMath = episodeDownloadItemValues.getListItemsDownloadValues().stream()
                    .allMatch(part -> part.getDownloadProgressState().get() == DownloadProgressState.COMPLETED);
            if (allMath) {
                downloadAllPartsComplete();
            } else {
                episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.DOWNLOADING);

            }
        }


    }

    private void updateAllValues() {
        updateTargetURL();
        while (!isDownloadFinish()) {
            updateStatus();
            updateDownloadSpeed();
            updateTotalSize();
        }
    }

    private void updateStatus() {
        if (episodeDownloadItemValues.getListItemsDownloadValues().stream()
                .anyMatch(part -> part.getDownloadProgressState().get() == DownloadProgressState.FAILED)) {
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.FAILED);
            return;
        }
        if (episodeDownloadItemValues.getListItemsDownloadValues().stream()
                .anyMatch(part -> part.getDownloadProgressState().get() == DownloadProgressState.CANCELED)) {
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.CANCELED);
            return;
        }
        if (episodeDownloadItemValues.getListItemsDownloadValues().stream()
                .anyMatch(part -> part.getDownloadProgressState().get() == DownloadProgressState.DOWNLOADING)) {
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.DOWNLOADING);
            return;
        }
        if (episodeDownloadItemValues.getListItemsDownloadValues().stream()
                .anyMatch(part -> part.getDownloadProgressState().get() == DownloadProgressState.PAUSE)) {
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.PAUSE);
            return;
        }


    }

    private void updateDownloadSpeed() {

        double downloadSpeed = episodeDownloadItemValues.getListItemsDownloadValues()
                .stream()
                .map(itemDownloadValues -> itemDownloadValues.getDownloadSpeed().doubleValue())
                .reduce(0.0, (subtotal, value) -> subtotal + value);
        episodeDownloadItemValues.getDownloadSpeed().set(downloadSpeed);

    }

    private void updateTargetURL() {
        String target = episodeDownloadItemValues.getTarget().getValue();
        episodeDownloadItemValues.getTarget().set("");
        episodeDownloadItemValues.getTarget().set(target);
    }

    private void updateTotalSize() {
        double totalSize = episodeDownloadItemValues.getListItemsDownloadValues()
                .stream()
                .map(itemDownloadValues -> itemDownloadValues.getTotalDownloaded().doubleValue())
                .reduce(0.0, (subtotal, value) -> subtotal + value);
        episodeDownloadItemValues.getTotalDownloaded().set(totalSize);
    }

    private void updateResolution() {
        PartDownloadItemsValues partDownloadItemsValues = (PartDownloadItemsValues) episodeDownloadItemValues.getListItemsDownloadValues().get(0);
        while(!isDownloadFinish()){

        }

    }

    private boolean isDownloadFinish() {
        if (episodeDownloadItemValues.getDownloadProgressState().get() == DownloadProgressState.COMPLETED)
            return true;
        if (episodeDownloadItemValues.getDownloadProgressState().get() == DownloadProgressState.FAILED)
            return true;
        if (episodeDownloadItemValues.getDownloadProgressState().get() == DownloadProgressState.CANCELED)
            return true;
        return false;
    }

    private DownloadInfo fillFragmentDownloadInfo(int fragmentPosition, PartDownloadItemsValues fragmentValues) {
        DownloadInfo fragmentDownloadInfo = new DownloadInfo();
        String partName = fragmentNameForPosition(fragmentPosition);

        fragmentDownloadInfo.setRoot(folderPath);
        fragmentDownloadInfo.setUrl(fragmentValues.getDirectLink().get());
        fragmentDownloadInfo.setFilename(partName);
        fragmentDownloadInfo.setPrefFiletype(FileTypeAccept.TS);
        fragmentDownloadInfo.setReferer(episodeDownloadInfo.getReferer());
        return fragmentDownloadInfo;
    }

    private void downloadPart(PartDownload partDownload) {
        try {
            partDownload.download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyPartDownloadFailed(PartDownloadItemsValues part) {

        episodeInfoRefresher.decreaseAmount();
        episodeInfoRefresher.refreshProgress();
        episodeDownloadItemValues.getObsListNewPartDownloadItemsValues().add(part);
        episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.FAILED);

    }

    private void notifyPartDownloadComplete(PartDownloadItemsValues part) {

        episodeInfoRefresher.increaseAmount();
        episodeInfoRefresher.refreshProgress();
        episodeDownloadItemValues.getObsListNewPartDownloadItemsValues().add(part);

    }

    private void readSizeFromMetadata(VideoMetadata videoMetadata, PartDownloadItemsValues partDownloadItemsValues) {

        long bytesSize = videoMetadata.getLongSize();
        double megabytesSize = BytesUtil.convertBytesToMegasBytes(bytesSize);
        partDownloadItemsValues.getDownloadSize().set(megabytesSize);
        episodeInfoRefresher.increaseFinalSize(megabytesSize);
    }

    private void readSize(long size, PartDownloadItemsValues partDownloadItemsValues) {

        long bytesSize = size;
        double megabytesSize = BytesUtil.convertBytesToMegasBytes(bytesSize);
        partDownloadItemsValues.getDownloadSize().set(megabytesSize);
        episodeInfoRefresher.increaseFinalSize(megabytesSize);
    }

    private void downloadAllPartsComplete() {
        try {
            onDownloadComplete();
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.COMPLETED);

        } catch (Exception e) {
            e.printStackTrace();
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.FAILED);
        }
    }

    private void onDownloadComplete() throws VideoComposerException, IOException {
        try {
            File videoCompose = composeEpisodeFromParts();
            if (videoCompose.exists()) {
                episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.COMPLETED);
            } else {
                episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.FAILED);
            }
        } catch (Exception e) {
            episodeDownloadItemValues.getDownloadProgressState().set(DownloadProgressState.FAILED);
            throw e;
        } finally {
            deleteEpisodeFragmentsFolder(new File(folderPath));
        }

    }

    private File composeEpisodeFromParts() throws VideoComposerException, IOException {
        VideoComposer videoComposer = new VideoComposer();

        String output = "\"" + episodeDownloadInfo.getRoot() + "\\" + episodeDownloadInfo.getFilename() + "." + episodeDownloadInfo.getPrefFiletype().getName() + "\"";
        File file = new File(folderPath);
        File[] files = file.listFiles((File f, String name) -> name.endsWith(FileTypeAccept.TS.getName()));
        List<File> fileParts = Arrays.asList(files);

        File videoCompose = videoComposer.compose(fileParts, output);
        return videoCompose;


    }

    @Override
    public void showDownloadValuesRealTimeInfo() {

        downloadRealTimeInfo.showInfoWithProgress(episodeDownloadItemValues);

    }

    @Override
    public void hideDownloadValuesRealTimeInfo() {
        downloadRealTimeInfo.stopShowInfo();
    }

    @Override
    public void pause() {
        partDownloads.forEach(part -> {
            part.pause();
        });
    }

    @Override
    public void resume() {
        partDownloads.forEach(part -> {
            part.resume();
        });
    }

    @Override
    public void stop() {
        partDownloads.forEach(part -> {
            part.stop();
        });
        executorService.shutdownNow();

    }

}
