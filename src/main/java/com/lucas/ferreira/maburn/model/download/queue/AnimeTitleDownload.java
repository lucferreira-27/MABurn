package com.lucas.ferreira.maburn.model.download.queue;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.AlertWindowView;

public class AnimeTitleDownload extends TitleDownload {
	public AnimeTitleDownload(List<ItemWebData> items, Collections collections) {
		super(items, collections);

	}
	public AnimeTitleDownload() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void checkProgress(Downloader<CollectionSubItem> downloader) {
		downloader.progressProperty().addListener((obs, oldvalue, newvalue) -> {
			CustomLogger.log("Anime: Progress " + newvalue.doubleValue());
		});

		downloader.sizeProperty().addListener((observable, oldvalue, newvalue) -> {
			sizeProperty.set(sizeProperty.subtract(oldvalue.doubleValue()).get());
			sizeProperty.set(sizeProperty.add(newvalue.doubleValue()).get());
		});

		downloader.downloadStateProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue.equals(String.valueOf(DownloadState.FAILED))
					|| newvalue.equals(String.valueOf(DownloadState.CANCELING))) {

				Double value = downloader.completedProperty().get();
				completedProperty.set(completedProperty.subtract(value).get());

			}

		});

		downloader.setOnFailed(event -> {
			System.err.println("The task failed with the following exception:");
			AlertWindowView.errorAlert("Download Service", "The task failed with the following exception:",
					exceptionProperty().get().getMessage());
			exceptionProperty().get().printStackTrace();

		});
	}
}
