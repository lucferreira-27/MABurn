package com.lucas.ferreira.maburn.controller.title.download.title;

import com.lucas.ferreira.maburn.controller.title.download.DownloadList;
import com.lucas.ferreira.maburn.model.Initialize;

import javafx.application.Platform;

public class TitleDownloadListCard implements Initialize {

	private Title title;
	private DownloadList downloadList;

	public TitleDownloadListCard(Title title) {
		this.title = title;
	}

	public void initialize() {
		downloadList = new DownloadList(title);
		downloadList.getNavDownloadList().getTotalCards().addListener((obs, oldvalue, newvalue) -> {
			String total = "Total: ";
			Platform.runLater(() -> {
				title.getTitleDownload().getLabelCardsTotal().setText(total + newvalue.intValue());
			});
		});
		downloadList.getNavDownloadList().getDownloadingCards().addListener((obs, oldvalue, newvalue) -> {
			String downloading = "Downloading: ";

			Platform.runLater(() -> {
				title.getTitleDownload().getLabelCardsDownloading().setText(downloading + newvalue.intValue());
			});
		});
		downloadList.getNavDownloadList().getCompletedCards().addListener((obs, oldvalue, newvalue) -> {
			String completed = "Completed: ";

			Platform.runLater(() -> {
				title.getTitleDownload().getLabelCardsCompleted().setText(completed + newvalue.intValue());
			});
		});
		downloadList.getNavDownloadList().getFailedCards().addListener((obs, oldvalue, newvalue) -> {
			String failed = "Failed: ";

			Platform.runLater(() -> {
				title.getTitleDownload().getLabelCardsFailed().setText(failed + newvalue.intValue());
			});
		});
		downloadList.getNavDownloadList().getFetchingCards().addListener((obs, oldvalue, newvalue) -> {
			String fetching = "Fetching: ";

			Platform.runLater(() -> {
				title.getTitleDownload().getLabelCardsFetching().setText(fetching + newvalue.intValue());
			});
		});
	}

	public DownloadList getDownloadList() {
		return downloadList;
	}

	public Title getTitle() {
		return title;
	}

}
