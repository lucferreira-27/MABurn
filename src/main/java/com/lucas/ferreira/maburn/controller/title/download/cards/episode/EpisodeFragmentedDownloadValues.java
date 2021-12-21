package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.GroupDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EpisodeFragmentedDownloadValues implements GroupDownloadValues {
    private DoubleProperty downloadProgress = new SimpleDoubleProperty();
    private IntegerProperty totalVideoFragmentDownloaded = new SimpleIntegerProperty();
    private DoubleProperty totalDownloadConcluded = new SimpleDoubleProperty();
    private DoubleProperty downloadItemSpeed = new SimpleDoubleProperty();
    private DoubleProperty timeRemain = new SimpleDoubleProperty();
    private DoubleProperty episodeSize = new SimpleDoubleProperty();
    private List<ItemDownloadValues> listPartDownloadItemValues = new ArrayList<ItemDownloadValues>();
    private ObjectProperty<DownloadProgressState> downloadProgressState = new SimpleObjectProperty<DownloadProgressState>(DownloadProgressState.WAITING);
    private ObservableList<ItemDownloadValues> obsListPartDownloadItemsValues = FXCollections.observableArrayList();
    private StringProperty target = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();;
    private StringProperty message =  new SimpleStringProperty();;

    @Override
    public DoubleProperty getTotalDownloaded() {

        return totalDownloadConcluded;
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
    public DoubleProperty getDownloadProgress() {
        return downloadProgress;
    }

    @Override
    public List<ItemDownloadValues> getListItemsDownloadValues() {

        return listPartDownloadItemValues;
    }

    public ObservableList<ItemDownloadValues> getObsListNewPartDownloadItemsValues() {
        return obsListPartDownloadItemsValues;
    }

    @Override
    public DoubleProperty getDownloadItemSpeed() {

        return downloadItemSpeed;
    }

    @Override
    public IntegerProperty getTotalItemsDownloaded() {

        return totalVideoFragmentDownloaded;
    }

    @Override
    public DoubleProperty getTimeRemain() {

        return timeRemain;
    }

    @Override
    public ObjectProperty<DownloadProgressState> getDownloadProgressState() {

        return downloadProgressState;
    }

    public DoubleProperty getEpisodeSize() {
        return episodeSize;
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s\nDownloadProgress: %s\nTotalVideoFragmentDownloaded: %s\nDownloadItemSpeed: %s\nSize: %s\nTimeRemain: %s\nListPartDownloadItemValues:\n%s\nDownloadProgressState: %s\n",
                name, downloadProgress.get(), totalVideoFragmentDownloaded.get(), downloadItemSpeed.get(), episodeSize.get(), timeRemain.get(),
                listPartDownloadItemValues.stream().map(DownloadValues::toString)
                        .collect(Collectors.joining("\n")),
                downloadProgressState.get());
    }

    @Override
    public DoubleProperty getDownloadSize() {

        return episodeSize;
    }

    @Override
    public DoubleProperty getDownloadSpeed() {

        return downloadItemSpeed;
    }

    @Override
    public StringProperty getMessage() {
        // TODO Auto-generated method stub
        return message;
    }
}
