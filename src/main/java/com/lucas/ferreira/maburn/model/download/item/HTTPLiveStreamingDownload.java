package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.channel.VideoDownloadByChannel;

public class HTTPLiveStreamingDownload implements ItemDownload{
    private DownloadInfo downloadInfo;
    private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();
    private EpisodeDownloadItemValues episodeDownloadItemValues;
    private VideoDownloadByChannel videoDownloadByChannel;

    public HTTPLiveStreamingDownload(DownloadInfo downloadInfo, DownloadValues downloadValues){
        this.downloadInfo = downloadInfo;
        this.episodeDownloadItemValues = (EpisodeDownloadItemValues) downloadValues;
    }


    @Override
    public void download() throws Exception {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void showDownloadValuesRealTimeInfo() {

    }

    @Override
    public void hideDownloadValuesRealTimeInfo() {

    }
}
