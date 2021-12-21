package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PartDownloadItemsValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.channel.ImageDownloadByChannel;
import com.lucas.ferreira.maburn.model.download.channel.TransportStreamDownloadByChannel;
import com.lucas.ferreira.maburn.model.download.channel.VideoDownloadByChannel;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;

import java.util.function.Consumer;

public class PartDownload implements ItemDownload {

    private PartDownloadItemsValues partDownloadItemsValues;
    private DownloadInfo downloadInfo;
    private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();
    private TransportStreamDownloadByChannel transportStreamDownloadByChannel;
    private Consumer<PartDownloadItemsValues> onDownloadComplete;
    private Consumer<PartDownloadItemsValues> onDownloadFailed;
    private boolean pause = false;
    private boolean stop = false;

    public PartDownload(PartDownloadItemsValues partDownloadItemsValues, DownloadInfo downloadInfo) {
        this.partDownloadItemsValues = partDownloadItemsValues;
        this.downloadInfo = downloadInfo;
    }

    @Override
    public void download() throws Exception {

        try {
            transportStreamDownloadByChannel = new TransportStreamDownloadByChannel(partDownloadItemsValues);
            transportStreamDownloadByChannel.download(downloadInfo);

            if(pause) {
                pause();
            }
            if(stop) {
                stop();
                throw new Exception("Download stoped");
            }

            onDownloadComplete.accept(partDownloadItemsValues);
        } catch (Exception e) {
            onDownloadFailed.accept(partDownloadItemsValues);
        }
    }

    public VideoMetadata readPartMetadata() {
        if (transportStreamDownloadByChannel != null) {
            return transportStreamDownloadByChannel.readVideoMetadatas();
        } else
            return null;
    }

    public void onDownloadComplete(Consumer<PartDownloadItemsValues> onDownloadComplete) {
        this.onDownloadComplete = onDownloadComplete;
    }

    public void onDownloadFailed(Consumer<PartDownloadItemsValues> onDownloadFailed) {
        this.onDownloadFailed = onDownloadFailed;
    }

    @Override
    public void showDownloadValuesRealTimeInfo() {

        downloadRealTimeInfo.showInfoWithProgress(partDownloadItemsValues);

    }

    @Override
    public void hideDownloadValuesRealTimeInfo() {
        downloadRealTimeInfo.stopShowInfo();
    }

    @Override
    public void pause() {
        pause = true;
        if (transportStreamDownloadByChannel != null)
            transportStreamDownloadByChannel.pause();
    }

    @Override
    public void resume() {
        pause = false;
        if (transportStreamDownloadByChannel != null)
            transportStreamDownloadByChannel.resume();
    }

    @Override
    public void stop() {
        stop = true;
        if (transportStreamDownloadByChannel != null)
            transportStreamDownloadByChannel.stop();
    }

}