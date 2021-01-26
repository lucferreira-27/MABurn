package com.lucas.ferreira.maburn.model.download.queue;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

public abstract class Downloader<T> extends Task<T> {

	private TitleDownload titleDownload;

	private boolean firstInstance = true;

	protected HttpURLConnection httpConn;

	protected CollectionSubItem subItem;

	protected List<File> listFile;

	protected List<String> listLink;

	protected ItemWebData webData;

	protected static final int BUFFER_SIZE = 8192;

	protected SimpleStringProperty nameProperty = new SimpleStringProperty();

	protected SimpleDoubleProperty sizeProperty = new SimpleDoubleProperty();

	protected SimpleDoubleProperty speedProperty = new SimpleDoubleProperty();

	protected SimpleDoubleProperty completedProperty = new SimpleDoubleProperty();

	protected SimpleIntegerProperty downloadFile = new SimpleIntegerProperty();

	protected SimpleObjectProperty<DownloadState> stateProperty = new SimpleObjectProperty<DownloadState>();

	protected SimpleDoubleProperty downloadProgress = new SimpleDoubleProperty();

	private final Button btnAction1 = new Button();
	private final Button btnAction2 = new Button();

	private final Button btnOpen = new Button("OPEN");
	private final Button btnPause = new Button("PAUSE");
	private final Button btnResume = new Button("RESUME");
	private final Button btnCancel = new Button("CANCEL");
	private final Button btnRemove = new Button("REMOVE");
	private final Button btnRefresh = new Button("REFRESH");

	protected SimpleStringProperty actionPauseProperty = new SimpleStringProperty();

	protected SimpleStringProperty actionCancelProperty = new SimpleStringProperty();

	protected BooleanProperty pauseProperty = new SimpleBooleanProperty();

	protected BooleanProperty cancelProperty = new SimpleBooleanProperty();

	protected BooleanProperty failedProperty = new SimpleBooleanProperty();

	public Downloader() {
		// TODO Auto-generated constructor stub
		stateProperty.set(DownloadState.PREPARING);
		cancelProperty.set(false);
		pauseProperty.set(false);

		btnOpen.setOnAction(event -> actionOnOpen());
		btnPause.setOnAction(event -> actionOnPause());
		btnResume.setOnAction(event -> actionOnResume());
		btnCancel.setOnAction(event -> actionOnCancel());
		btnRefresh.setOnAction(event -> actionOnRefresh());
		btnRemove.setOnAction(event -> actionOnRemove());

		btnAction1.setText(btnPause.getText());
		btnAction1.setOnAction(btnPause.getOnAction());

		btnAction2.setText(btnCancel.getText());
		btnAction2.setOnAction(btnCancel.getOnAction());

	}

	public String getName() {

		return nameProperty.get();
	}

	public DownloadState getDownloadState() {
		return stateProperty.get();
	}

	public Double getSize() {
		return sizeProperty.get();
	}

	public Double getSpeed() {
		return speedProperty.get();
	}

	public Double getCompleted() {
		return completedProperty.get();
	}

	public Double getDownloadProgress() {
		return downloadProgress.get();
	}

	public String getActionPause() {
		return actionPauseProperty.get();
	}

	public String getActionCancel() {
		return actionCancelProperty.get();
	}

	public Button getBtnPause() {
		return btnAction1;
	}

	public Button getBtnCancel() {
		return btnAction2;
	}

	public boolean isFirstInstance() {
		return firstInstance;
	}

	public CollectionSubItem getSubItem() {
		return subItem;
	}

	public ItemWebData getItemWebData() {
		return webData;
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	public BooleanProperty getCancelProperty() {
		return cancelProperty;
	}

	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	public SimpleDoubleProperty sizeProperty() {
		return sizeProperty;
	}

	public SimpleDoubleProperty speedProperty() {
		return speedProperty;
	}

	public SimpleDoubleProperty completedProperty() {
		return completedProperty;
	}

	public SimpleIntegerProperty downloadFileProperty() {
		return downloadFile;
	}

	public SimpleObjectProperty<DownloadState> downloadStateProperty() {
		return stateProperty;
	}

	public SimpleStringProperty actionPauseProperty() {
		return actionPauseProperty;
	}

	public SimpleStringProperty actionCancelProperty() {
		return actionCancelProperty;
	}

	public SimpleDoubleProperty getDownloadProgressProperty() {
		return downloadProgress;
	}

	public HttpURLConnection getHttpConn() {
		return httpConn;
	}

	public void setDownloadState(DownloadState state) {
		stateProperty.set(state);
	}

	protected abstract File download() throws IOException;

	protected void updateSize(double completed) {
		sizeProperty.set(completed);
	}

	protected void updateCompleted(double lenght) {

		completedProperty.set(lenght);

	}

	protected void updateDonwloadFile(int n) {
		downloadFile.set(n);
	}

	protected void updateSpeed(double speed) {

		speedProperty.set(speed);
	}

	protected void updateName(String name) {
		nameProperty.set(name);
	}

	protected void updateState(DownloadState state) {
		Platform.runLater(() -> {
			action(state);
		});
		stateProperty.set(state);
	}

	private void action(DownloadState state) {
		switch (state) {
		case FINISH:
			btnAction1.setText(btnOpen.getText());
			btnAction1.setOnAction(btnOpen.getOnAction());
			break;
		case FAILED:
			btnAction1.setText(btnRefresh.getText());
			btnAction1.setOnAction(btnRefresh.getOnAction());

			btnAction2.setText(btnRemove.getText());
			btnAction2.setOnAction(btnRemove.getOnAction());
			break;
		case PAUSE:
			btnAction1.setText(btnResume.getText());
			btnAction1.setOnAction(btnResume.getOnAction());
			
			break;
		case PAUSING:
			CustomLogger.log("Pausing please wait!");
			
			break;
		case CANCELING:
			CustomLogger.log("Pausing please wait!");
			
			break;
		case DOWNLOADING:
			btnAction1.setText(btnPause.getText());
			btnAction1.setOnAction(btnPause.getOnAction());
			break;
		default:
			break;
		}
	}

	private void actionOnOpen() {
		try {
			DirectoryModel.openDirectory(subItem.getDestination());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void actionOnPause() {
		pause();
	}

	private void actionOnResume() {

		resume();
	}

	private void actionOnCancel() {
		kill();
	}

	private void actionOnRemove() {

		DownloadQueue.getInstance().getDownload(titleDownload.getId()).removeItem(this);

	}

	private void actionOnRefresh() {
		DownloadQueue.getInstance().getDownload(titleDownload.getId()).refreshItem(this);

	}

	public void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, ItemWebData webData,
			TitleDownload titleDownload) {
		this.listLink = listLink;
		this.subItem = subItem;
		this.listFile = listFile;
		this.webData = webData;
		this.titleDownload = titleDownload;
		cancelProperty.set(false);
		pauseProperty.set(false);

		updateMessage("[" + webData.getName() + "]");

	}

	public abstract long speedCalculation(Double downloadSpeed, long start, long end, int i);

	public abstract void pause();

	public abstract void resume();

	public void kill() {
		pauseProperty.set(false);
		cancelProperty.set(true);
		updateState(DownloadState.CANCELING);
	}

	public abstract void refresh();

	@Override
	protected T call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Downloader [\nnameProperty=" + nameProperty + ", \nsizeProperty=" + sizeProperty + ", \nspeedProperty="
				+ speedProperty + ", \ncompletedProperty=" + completedProperty + ", \nstateProperty=" + stateProperty
				+ ", \nprogressProperty=" + null + ", \npauseProperty=" + pauseProperty + ", \ncancelProperty="
				+ cancelProperty + ", \nfetched= " + webData.isFetched() + "]";
	}

}
