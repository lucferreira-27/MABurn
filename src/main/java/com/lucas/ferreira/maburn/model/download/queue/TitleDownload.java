package com.lucas.ferreira.maburn.model.download.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.MathUtil;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TitleDownload {
	
	private final static int MAXIMUM_NUMBER = 10;
	private Integer downloadType = null;
	private ExecutorService executorDownloader = Executors.newFixedThreadPool(MAXIMUM_NUMBER);

	private CollectionItem collectionItem;
	private Collections collections;
	private TitleWebData titleWebData;

	private List<Downloader<CollectionSubItem>> downloads = new ArrayList<Downloader<CollectionSubItem>>();
	private IntegerProperty id = new SimpleIntegerProperty();
	private ObservableList<Downloader<CollectionSubItem>> obsDownloads;
	private int obsDownloadRealSize;

	private LongProperty downloadTime = new SimpleLongProperty();
	private StringProperty remain = new SimpleStringProperty("Remain: ");
	private DoubleProperty speedProperty = new SimpleDoubleProperty();
	private BooleanProperty pauseProperty = new SimpleBooleanProperty();
	private BooleanProperty cancelProperty = new SimpleBooleanProperty();
	private DoubleProperty totalProgressPropery = new SimpleDoubleProperty();
	private IntegerProperty concludedDownlods = new SimpleIntegerProperty(0);
	private IntegerProperty failedDownlods = new SimpleIntegerProperty(0);
	private IntegerProperty totalDownlods = new SimpleIntegerProperty(0);

	private double totalProgress;
	protected ObjectProperty<DownloadState> state = new SimpleObjectProperty<DownloadState>(DownloadState.PREPARING);

	{
		pauseProperty.addListener((obss, oldvalue, newvalue) -> {
			if (newvalue) {
				obsDownloads.forEach(download -> {

					download.pause();
				});
				state.set(DownloadState.PAUSE);

			} else
				obsDownloads.forEach(download -> {
					download.resume();
				});
			state.set(DownloadState.DOWNLOADING);

		});

		cancelProperty.addListener((obs, oldvalue, newvalue) -> {
			if (newvalue) {
				obsDownloads.forEach(download -> {
					download.kill();

				});
				state.set(DownloadState.FAILED);
			}
		});
		totalProgressPropery.addListener((obs, oldvalue, newvalue) -> {

			if (newvalue.intValue() == 1.0) {
				state.set(DownloadState.FINISH);
			}
		});

		downloadTime.addListener((obs, oldvalue, newvalue) -> {

		});

		remain.addListener((obs, oldvalue, newvalue) -> {
			System.out.println("Remain: " + remain);
		});

		state.addListener((obs, oldvalue, newvalue) -> {
			CustomLogger.log("Change state from " + oldvalue + " to " + newvalue);
			CustomLogger.log("[DownloadState] " + newvalue);
		});

	}

	public TitleDownload(Collections collections, int id) {
		// TODO Auto-generated constructor stub
		this.collections = collections;
		this.id.set(id);
		this.collectionItem = collections.getActualItem();
		obsDownloads = FXCollections.observableArrayList(downloads);
		checkSpeed();
	}

	public void addItem(ItemWebData item) {
		if (!item.isFetched())
			throw new DownloadServiceException("Item need be fetched first!");

		Downloader<CollectionSubItem> downloader = item.download(collections, this);
		totalDownlods.set(totalDownlods.get() + 1);
		checkProgress(downloader);
		obsDownloads.add(downloader);
		obsDownloadRealSize += 1;
	}

	public void refreshItem(Downloader<?> targetDownloader) {

		Downloader<CollectionSubItem> downloader = targetDownloader.getItemWebData().download(collections, this);

		obsDownloads.set(obsDownloads.indexOf(targetDownloader), downloader);

		checkProgress(downloader);
		obsDownloadRealSize += 1;
	}

	public void removeItem(Downloader<?> targetDownloader) {

		obsDownloads.remove(targetDownloader);
		obsDownloadRealSize -= 1;
	}

	public void checkProgress(Downloader<CollectionSubItem> downloader) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			downloader.progressProperty().addListener((obs, oldvalue, newvalue) -> {
				if (Double.isNaN(newvalue.doubleValue())) {
					return;
				}
				if (oldvalue.doubleValue() != -1.0) {
					totalProgress -= oldvalue.doubleValue();
				}

				totalProgress += newvalue.doubleValue();
				totalProgressPropery.set(MathUtil.roundDouble(totalProgress / obsDownloadRealSize, 5));

			});
		});

		downloader.cancelProperty.addListener((observable, oldvalue, newvalue) -> {
			totalProgress -= downloader.progressProperty().doubleValue();

		});

		downloader.failedProperty.addListener((observable, oldvalue, newvalue) -> {
			obsDownloadRealSize -= 1;
			totalProgressPropery.set(MathUtil.roundDouble(totalProgress / obsDownloadRealSize, 5));

		});

		downloader.stateProperty.addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				if (oldvalue == newvalue) {
					return;
				}

				if (newvalue == DownloadState.FAILED) {
					totalProgressPropery.set(MathUtil.roundDouble(totalProgress / obsDownloadRealSize, 5));
					failedDownlods.set(failedDownlods.get() + 1);
					return;
				}
				if (newvalue == DownloadState.FINISH) {
					concludedDownlods.set(concludedDownlods.get() + 1);
				}
			});
		});



		Platform.runLater(() -> {
			downloader.setOnFailed(event -> {
				System.err.println("The task failed with the following exception:");

			});
		});

	}

	public CollectionItem getCollectionItem() {
		return collectionItem;
	}

	public void setCollectionItem(CollectionItem collectionItem) {
		this.collectionItem = collectionItem;
	}

	public int getId() {
		return id.getValue();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public List<Downloader<CollectionSubItem>> getDownloads() {
		return downloads;
	}

	public ObservableList<Downloader<CollectionSubItem>> getObsDownloads() {
		return obsDownloads;
	}

	public TitleWebData getTitleWebData() {
		return titleWebData;
	}

	public void setTitleWebData(TitleWebData titleWebData) {
		this.titleWebData = titleWebData;
	}

	public DoubleProperty getSpeedProperty() {
		return speedProperty;
	}

	public void setSpeedProperty(DoubleProperty speedProperty) {
		this.speedProperty = speedProperty;
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	public void setPauseProperty(BooleanProperty pauseProperty) {
		this.pauseProperty = pauseProperty;
	}

	public DoubleProperty getTotalProgressPropery() {
		return totalProgressPropery;
	}

	public IntegerProperty getFailedDownlods() {
		return failedDownlods;
	}

	public IntegerProperty getConcludedDownlods() {
		return concludedDownlods;
	}

	public IntegerProperty getTotalDownlods() {
		return totalDownlods;
	}
	
	public Integer getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
	}
	public void setState(DownloadState state) {
		this.state.set(state);
	}

	public ObjectProperty<DownloadState> getState() {
		return state;
	}

	public ExecutorService getExecutorDownloader() {
		return executorDownloader;
	}

	public boolean isPausing() {

		return obsDownloads.stream().anyMatch(d -> d.getDownloadState() == DownloadState.PAUSING);
	}

	public void speedCalculate() {
		LongProperty b = new SimpleLongProperty(System.currentTimeMillis());
		LongProperty e = new SimpleLongProperty(0);
		totalProgressPropery.addListener((obs, oldvalue, newvalue) -> {
			e.set(System.currentTimeMillis());
			CustomLogger.log("[Speed] " + (newvalue.doubleValue()));

		});

	}

	public StringProperty getRemain() {
		return remain;
	}

	public void setRemain(StringProperty remain) {
		this.remain = remain;
	}

	public void checkSpeed() {

		new Thread(() -> {
			double start = 0;
			double end = 1;
			while (state.get() != DownloadState.FINISH) {

				try {
					start = totalProgressPropery.get();
					Thread.sleep(1000);
					end = totalProgressPropery.get();

					double downloadeSpeed = end - start;
					if (downloadeSpeed == 0) {
						// remain.set(9999);
						continue;
					}
					calculateTimeRemain((1 - totalProgressPropery.get()) / downloadeSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(() -> {
			while (true) {
				long b = System.currentTimeMillis();
				try {
					Thread.sleep(1000);
					long end = System.currentTimeMillis();

					downloadTime.setValue((end - b) + downloadTime.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void calculateTimeRemain(Double newvalue) {
		Platform.runLater(() -> {
			int time = newvalue.intValue();
			String text;
			if (time > 60) {
				time = time / 60;
				if (time == 1)
					text = " Minute";
				else
					text = " Minutes";
			} else if (time == 1) {
				text = " Second";
			} else {
				text = " Seconds";
			}
			remain.set("Remain: " + time + text);
		});
	}

	public void pause() {
		pauseProperty.set(true);
	}

	public void resume() {
		pauseProperty.set(false);
	}

	public void cancel() {
		cancelProperty.set(true);

	}

}
