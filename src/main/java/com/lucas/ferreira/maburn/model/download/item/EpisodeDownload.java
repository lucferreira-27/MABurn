package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.exceptions.EpisodeDownloadException;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;
import com.lucas.ferreira.maburn.model.download.channel.VideoDownloadByChannel;

public class EpisodeDownload implements ItemDownload {

	private DownloadInfo downloadInfo;
	private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();
	private EpisodeDownloadItemValues episodeDownloadItemValues;
	private VideoDownloadByChannel videoDownloadByChannel;

	public EpisodeDownload(DownloadInfo downloadInfo, DownloadValues downloadValues) {
		this.downloadInfo = downloadInfo;
		this.episodeDownloadItemValues = (EpisodeDownloadItemValues) downloadValues;
	}

	@Override
	public void download() throws Exception {
		try {
			videoDownloadByChannel = new VideoDownloadByChannel(episodeDownloadItemValues);
			videoDownloadByChannel.download(downloadInfo);
		} catch (Exception e) {
			throw new EpisodeDownloadException(e.getMessage());
		}

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
		if (videoDownloadByChannel != null)
			videoDownloadByChannel.pause();
	}

	@Override
	public void resume() {
		if (videoDownloadByChannel != null)
			videoDownloadByChannel.resume();
	}

	@Override
	public void stop() {
		if (videoDownloadByChannel != null)
			videoDownloadByChannel.stop();
	}
	public EpisodeDownloadItemValues getEpisodeDownloadItemValues() {
		return episodeDownloadItemValues;
	}
	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

}
