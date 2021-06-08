package com.lucas.ferreira.maburn.model.download.item;

import java.util.function.Consumer;

import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.channel.ImageDownloadByChannel;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;

public class PageDownload implements ItemDownload {

	private PageDownloadItemValues pageDownloadItemValues;
	private DownloadInfo downloadInfo;
	private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();
	private ImageDownloadByChannel imageDownloadByChannel;
	private Consumer<PageDownloadItemValues> onDownloadComplete;
	private Consumer<PageDownloadItemValues> onDownloadFailed;

	public PageDownload(PageDownloadItemValues pageDownloadItemValues, DownloadInfo downloadInfo) {
		this.pageDownloadItemValues = pageDownloadItemValues;
		this.downloadInfo = downloadInfo;
	}

	@Override
	public void download() throws Exception {
		try {
			imageDownloadByChannel = new ImageDownloadByChannel(pageDownloadItemValues);
			imageDownloadByChannel.download(downloadInfo);
			onDownloadComplete.accept(pageDownloadItemValues);
		} catch (Exception e) {
			onDownloadFailed.accept(pageDownloadItemValues);
		}
	}

	public ImageMetadata readPageMetadata() {
		if (imageDownloadByChannel != null) {
			return imageDownloadByChannel.readImageMetadatas();
		} else
			return null;
	}

	public void onDownloadComplete(Consumer<PageDownloadItemValues> onDownloadComplete) {
		this.onDownloadComplete = onDownloadComplete;
	}

	public void onDownloadFailed(Consumer<PageDownloadItemValues> onDownloadFailed) {
		this.onDownloadFailed = onDownloadFailed;
	}

	@Override
	public void showDownloadValuesRealTimeInfo() {

		downloadRealTimeInfo.showInfoWithProgress(pageDownloadItemValues);

	}

	@Override
	public void hideDownloadValuesRealTimeInfo() {
		downloadRealTimeInfo.stopShowInfo();
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

}
