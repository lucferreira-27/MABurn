package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.util.MathUtil;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.application.Platform;

public class EpisodeCardValuesBinder implements CardValuesBinder {

	@Override
	public void binder(DownloadCard downloadCard, DownloadValues downloadValues) {
		Platform.runLater(() -> {
			setAllCardText(downloadCard, downloadValues);
			addAllCardDownloadInfo(downloadCard, downloadValues);
		});
	}




	

	private void addAllCardDownloadInfo(DownloadCard downloadCard, DownloadValues downloadValues) {
		addCardDownloadConclued(downloadCard, downloadValues);
		addCardDownloadStateListener(downloadCard, downloadValues);
		addCardDownloadProgressListener(downloadCard, downloadValues);
		addCardDownloadSizeListener(downloadCard, downloadValues);
		addCardDownloadSpeed(downloadCard, downloadValues);
		addCardDownloadConclued(downloadCard, downloadValues);
		addCardDownloadTimeRemain(downloadCard, downloadValues);
	}

	private void setAllCardText(DownloadCard downloadCard, DownloadValues downloadValues) {
		setCardDownloadSpeed(downloadCard, downloadValues);
		setCardItemTitleText(downloadCard, downloadValues);
		setCardTotalSize(downloadCard, downloadValues);
	}

	private void setCardItemTitleText(DownloadCard downloadCard, DownloadValues downloadValues) {
		downloadCard.getLabelItemTitle().setText(downloadValues.getName());

	}

	private void setCardTotalSize(DownloadCard downloadCard, DownloadValues downloadValues) {
		downloadCard.getLabelTotalSize().setText(DataStorageUtil.converterUnit(downloadValues.getDownloadSize().get()));

	}

	private void setCardDownloadSpeed(DownloadCard downloadCard, DownloadValues downloadValues) {
		downloadCard.getLabelDownloadSpeed()
				.setText(DataStorageUtil.converterUnit(downloadValues.getDownloadSize().doubleValue()));

	}

	private void addCardDownloadProgressListener(DownloadCard downloadCard, DownloadValues downloadValues) {

		downloadCard.getProgressBarDownload().progressProperty().bind(downloadValues.getDownloadProgress());
		downloadValues.getDownloadProgress().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> downloadCard.getLabelPorcentageConcluded()
					.setText(String.valueOf(MathUtil.roundDouble(newvalue.doubleValue(), 2)) + "%"));
		});

	}

	private void addCardDownloadStateListener(DownloadCard downloadCard, DownloadValues downloadValues) {

		downloadValues.getDownloadProgressState().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> downloadCard.getLabelDownloadState().setText(String.valueOf(newvalue.name())));
		});
	}

	private void addCardDownloadSizeListener(DownloadCard downloadCard, DownloadValues downloadValues) {

		downloadValues.getDownloadSize().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> downloadCard.getLabelTotalSize()
					.setText(DataStorageUtil.converterUnit(newvalue.doubleValue())));

		});
	}

	private void addCardDownloadSpeed(DownloadCard downloadCard, DownloadValues downloadValues) {

		downloadValues.getDownloadSpeed().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> downloadCard.getLabelDownloadSpeed()
					.setText(DataStorageUtil.converterSpeedUnit(newvalue.doubleValue())));

		});
	}

	private void addCardDownloadConclued(DownloadCard downloadCard, DownloadValues downloadValues) {
		downloadValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> downloadCard.getLabelCompletedDownload()
					.setText(DataStorageUtil.converterUnit(newvalue.doubleValue())));

		});
	}

	private void addCardDownloadTimeRemain(DownloadCard downloadCard, DownloadValues downloadValues) {
		downloadValues.getTimeRemain().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> downloadCard.getLabelTimeRemain().setText(String.valueOf(newvalue.intValue())));

		});
	}

}
