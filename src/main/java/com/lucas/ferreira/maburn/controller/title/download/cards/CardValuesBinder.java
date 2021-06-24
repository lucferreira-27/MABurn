package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.util.MathUtil;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.application.Platform;

public abstract class CardValuesBinder {
	
	
	protected DownloadCard downloadCard; 
	protected DownloadValues downloadValues;
	
	public void binder(DownloadCard downloadCard, DownloadValues downloadValues) {
		Platform.runLater(() -> {
			this.downloadCard = downloadCard;
			this.downloadValues =  downloadValues;
			setAllCardText();
			addAllCardDownloadInfo();
			customBinder();
		});
	}


	protected abstract void customBinder();

	

	private void addAllCardDownloadInfo() {
		addCardDownloadConclued();
		addCardDownloadStateListener();
		addCardDownloadProgressListener();
		addCardDownloadSizeListener();
		addCardDownloadSpeed();
		addCardDownloadConclued();
		addCardDownloadTimeRemain();
	}

	private void setAllCardText() {
		setCardDownloadSpeed();
		setCardItemTitleText();
		setCardTotalSize();
	}

	private void setCardItemTitleText() {
		downloadCard.getLabelItemTitle().setText(downloadValues.getName());

	}

	private void setCardTotalSize() {
		downloadCard.getLabelTotalSize().setText(DataStorageUtil.converterUnit(downloadValues.getDownloadSize().get()));

	}

	private void setCardDownloadSpeed() {
		downloadCard.getLabelDownloadSpeed()
				.setText(DataStorageUtil.converterUnit(downloadValues.getDownloadSize().doubleValue()));

	}

	private void addCardDownloadProgressListener() {

		downloadCard.getProgressBarDownload().progressProperty().bind(downloadValues.getDownloadProgress());
		downloadValues.getDownloadProgress().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> downloadCard.getLabelPorcentageConcluded()
					.setText(String.valueOf(MathUtil.roundDouble(newvalue.doubleValue(), 2) * 100) + "%"));
		});

	}

	private void addCardDownloadStateListener() {

		downloadValues.getDownloadProgressState().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> downloadCard.getLabelDownloadState().setText(String.valueOf(newvalue.name())));
		});
	}

	private void addCardDownloadSizeListener() {

		downloadValues.getDownloadSize().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> downloadCard.getLabelTotalSize()
					.setText(DataStorageUtil.converterUnit(newvalue.doubleValue())));

		});
	}

	private void addCardDownloadSpeed() {

		downloadValues.getDownloadSpeed().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> downloadCard.getLabelDownloadSpeed()
					.setText(DataStorageUtil.converterSpeedUnit(newvalue.doubleValue())));

		});
	}

	private void addCardDownloadConclued() {
		downloadValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {

			Platform.runLater(() -> downloadCard.getLabelCompletedDownload()
					.setText(DataStorageUtil.converterUnit(newvalue.doubleValue())));

		});
	}

	private void addCardDownloadTimeRemain() {
		downloadValues.getTimeRemain().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> downloadCard.getLabelTimeRemain().setText(String.valueOf(newvalue.intValue())));

		});
	}
      
}
