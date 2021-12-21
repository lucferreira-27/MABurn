package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;
import javafx.beans.property.*;

public class PartDownloadItemsValues implements ItemDownloadValues {

    private DoubleProperty downloadProgress = new SimpleDoubleProperty();
    private DoubleProperty downloadSize = new SimpleDoubleProperty();
    private DoubleProperty downloadSpeed = new SimpleDoubleProperty();
    private DoubleProperty timeRemain = new SimpleDoubleProperty();
    private DoubleProperty totalDownloaded = new SimpleDoubleProperty();
    private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>(
            DownloadProgressState.WAITING);
    private StringProperty name = new SimpleStringProperty();;
    private StringProperty directLink = new SimpleStringProperty();;
    private StringProperty target = new SimpleStringProperty();
    private StringProperty message = new SimpleStringProperty();;

    public PartDownloadItemsValues() {

    }

    @Override
    public StringProperty getName() {

        return name;
    }

    @Override
    public StringProperty getTarget() {

        return target;
    }

    @Override
    public StringProperty getDirectLink() {

        return directLink;
    }

    @Override
    public DoubleProperty getDownloadProgress() {

        return downloadProgress;
    }

    @Override
    public DoubleProperty getDownloadSize() {

        return downloadSize;
    }

    @Override
    public DoubleProperty getDownloadSpeed() {

        return downloadSpeed;
    }

    @Override
    public DoubleProperty getTotalDownloaded() {

        return totalDownloaded;
    }

    @Override
    public ObjectProperty<DownloadProgressState> getDownloadProgressState() {

        return downloadProgressState;
    }

    @Override
    public DoubleProperty getTimeRemain() {

        return timeRemain;
    }

    @Override
    public String toString() {

        return "[File: " + name.get() + " - Size: " + DataStorageUtil.converterUnit(downloadSize.get())
                + " - Download State: " + downloadProgressState.get() + "] \n" + "[" + directLink.get() + "]\n";
    }

    @Override
    public void setMessage(String msg) {
        this.message.set(msg);
    }

    @Override
    public StringProperty getMessage() {
        return message;
    }

}
