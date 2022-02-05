package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.enums.DefaultNullValue;
import com.lucas.ferreira.maburn.util.MathUtil;
import com.lucas.ferreira.maburn.util.TimeText;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.application.Platform;

public abstract class DownloadCardValuesBinder {

    protected DownloadCard downloadCard;
    protected DownloadValues downloadValues;
    private DownloadStateStyle downloadStateStyle = new DownloadStateStyle();

    public void binder(DownloadCard downloadCard, DownloadValues downloadValues) {
        Platform.runLater(() -> {
            this.downloadCard = downloadCard;
            this.downloadValues = downloadValues;
            setAllCardText();
            addAllCardDownloadInfo();
            customBinder();
        });
    }

    protected abstract void customBinder();

    private void addAllCardDownloadInfo() {
        addCardDownloadConcluded();
        addCardDownloadStateListener();
        addCardDownloadProgressListener();
        addCardDownloadSizeListener();
        addCardDownloadSpeed();
        addCardDownloadConcluded();
        addCardDownloadTimeRemain();
        addCardDownloadLink();

    }

    private void setAllCardText() {
        setCardDownloadLink();
        setCardDownloadSpeed();
        setCardItemTitleText();
        setCardTotalSize();
        setCardTimeRemain();
        setCardDownloadState();

    }

    private void setCardDownloadState() {
        if (downloadValues.getMessage().get() == null || downloadValues.getMessage().get().isEmpty())
            downloadCard.getLabelDownloadState().setText(String.valueOf(downloadValues.getDownloadProgressState().get()));
        else
            downloadCard.getLabelDownloadState().setText(downloadValues.getMessage().get());

        downloadStateStyle.setNodeStyleByState(downloadValues.getDownloadProgressState().get(),
                downloadCard.getLabelDownloadState());

    }

    private void setCardTimeRemain() {
        String value = downloadValues.getTimeRemain().get() == 0 ? DefaultNullValue.STRING_NULL.getValue()
                : TimeText.secondsToText(downloadValues.getTimeRemain().intValue());
        downloadCard.getLabelTimeRemain().setText(value);

    }

    private void setCardDownloadLink() {
        downloadCard.getLabelDownloadLink().setText(downloadValues.getTarget().get());

    }

    private void setCardItemTitleText() {
        String value = downloadValues.getName().get().isEmpty() ? DefaultNullValue.STRING_NULL.getValue()
                : downloadValues.getName().get();
        downloadCard.getLabelItemTitle().setText(value);

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

            Platform.runLater(() -> {
                downloadStateStyle.setNodeStyleByState(newvalue, downloadCard.getLabelDownloadState());
                if (downloadValues.getMessage().get() == null || downloadValues.getMessage().get().isEmpty())
                    downloadCard.getLabelDownloadState().setText(String.valueOf(newvalue.name()));
                else
                    downloadCard.getLabelDownloadState().setText(downloadValues.getMessage().get());

            });
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

    private void addCardDownloadConcluded() {
        downloadValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {

            Platform.runLater(() -> downloadCard.getLabelCompletedDownload()
                    .setText(DataStorageUtil.converterUnit(newvalue.doubleValue())));

        });
    }

    private void addCardDownloadTimeRemain() {
        downloadValues.getTimeRemain().addListener((obs, oldvalue, newvalue) -> {

            Platform.runLater(() -> downloadCard.getLabelTimeRemain()
                    .setText(String.valueOf(TimeText.secondsToText(newvalue.intValue()))));

        });
    }

    private void addCardDownloadLink() {
        downloadValues.getTarget().addListener((obs, oldvalue, newvalue) -> {

            Platform.runLater(
                    () -> downloadCard.getLabelDownloadLink().setText(downloadValues.getTarget().get())
            );
        });
    }
}
