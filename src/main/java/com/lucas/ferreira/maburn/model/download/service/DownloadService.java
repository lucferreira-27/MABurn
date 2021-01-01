package com.lucas.ferreira.maburn.model.download.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.view.AlertWindowView;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public class DownloadService extends Task<Downloader<ItemWebData>> {
	private List<ItemWebData> items = new ArrayList<ItemWebData>();
	private Collections collections;
	private DoubleProperty propertyProgress = new SimpleDoubleProperty();
	private BooleanProperty pauseProperty = new SimpleBooleanProperty();
	private BooleanProperty cancelProperty = new SimpleBooleanProperty();

	private DoubleProperty sizeProperty = new SimpleDoubleProperty();

	private IntegerProperty downloadFile = new SimpleIntegerProperty();

	private IntegerProperty totalDownload = new SimpleIntegerProperty();

	private DoubleProperty speedProperty = new SimpleDoubleProperty();

	private DoubleProperty completedProperty = new SimpleDoubleProperty();
	private DownloadState state;
	private boolean speedCheckOn = false;
	private List<Future<Downloader<CollectionSubItem>>> futureItemWebData = new ArrayList<Future<Downloader<CollectionSubItem>>>();
	private ExecutorService exec = Executors.newFixedThreadPool(1);

	public DownloadService(List<ItemWebData> items, Collections collections) {
		// TODO Auto-generated constructor stub
		this.items.addAll(items);

		this.collections = collections;

		progressProperty().addListener((observable, oldvalue, newvalue) -> {
			// updateProgress(completedProperty.get(), sizeProperty.get());
			System.err.println("progress: " + progressProperty());
			System.err.println("Size: " + sizeProperty.get());
			System.err.println("Completed: " + completedProperty.get());
			System.err.println("Speed: " + speedProperty.get());

		});
		totalDownload.addListener((obs, old, newvalue) -> {
			updateProgress(downloadFile.get(), totalDownload.get());
		});

	}

	private void checkProgress() {
		updateProgress(completedProperty.get(), sizeProperty.get());
		if (!speedCheckOn) {
			checkSpeed();
		} else {
			Platform.runLater(() -> {
				if (progressProperty().get() == 1) {
					state = DownloadState.FINISH;
					speedCheckOn = false;
				}
			});
		}

	}

	private void addItemInFuture(ItemWebData item) {
		futureItemWebData.add(exec.submit(new Callable<Downloader<CollectionSubItem>>() {

			@Override
			public Downloader<CollectionSubItem> call() {
				// TODO Auto-generated method stub
				try {
					Downloader<CollectionSubItem> downloader = item.getDownloader();
					if (!downloader.isFirstInstance()) {
						downloader.reset();
					}
					downloader.sizeProperty().addListener((observable, oldvalue, newvalue) -> {
						// System.out.println("Size : "+ newvalue);
						sizeProperty.set(sizeProperty.subtract(oldvalue.doubleValue()).get());
						sizeProperty.set(sizeProperty.add(newvalue.doubleValue()).get());
					});

					System.out.println("Category: " + item.getSite().getCategory());
//					if (item.getSite().getCategory() == Category.ANIME) {
//						downloader.completedProperty().addListener((observable, oldvalue, newvalue) -> {
//							// System.out.println("Completed : "+ newvalue);
//							completedProperty.set(completedProperty.subtract(oldvalue.doubleValue()).get());
//							completedProperty.set(completedProperty.add(newvalue.doubleValue()).get());
//						});
//						downloader.progressProperty().addListener((observable, oldvalue, newvalue) -> {
//							checkProgress();
//						});
//
//					}

					// if (item.getSite().getCategory() == Category.MANGA)
					totalDownload.set(items.size());

					downloader.downloadStateProperty().addListener((observable, oldvalue, newvalue) -> {
						if (newvalue.equals(String.valueOf(DownloadState.FINISH))
								|| newvalue.equals(String.valueOf(DownloadState.FAILED))) {
							downloadFile.set(downloadFile.add(1).get());
							updateProgress(downloadFile.get(), items.size());

						}

					});

					downloader.downloadStateProperty().addListener((observable, oldvalue, newvalue) -> {
						if (newvalue.equals(String.valueOf(DownloadState.FAILED))
								|| newvalue.equals(String.valueOf(DownloadState.CANCELING))) {

							Double value = downloader.completedProperty().get();
							// System.out.println("State : "+ newvalue);

							completedProperty.set(completedProperty.subtract(value).get());

						}

					});

					downloader.setOnFailed(event -> {
						System.err.println("The task failed with the following exception:");
						AlertWindowView.errorAlert("Download Service", "The task failed with the following exception:",
								exceptionProperty().get().getMessage());
						exceptionProperty().get().printStackTrace();

					});

					item.download(collections);
					return downloader;

				} catch (Exception e) {
					// TODO: handle exception
					AlertWindowView.errorAlert("Download Service", "The task failed with the following exception:",
							exceptionProperty().get().getMessage());

					e.printStackTrace();
					return null;
				}

			}

		}));
	}

	@Override
	protected Downloader<ItemWebData> call() throws Exception {
		// TODO Auto-generated method stub
		for (ItemWebData item : items) {

			addItemInFuture(item);

		}

		return null;
	}

	public void addItem(ItemWebData item) {

		System.out.println(items);

		items.add(item);
		// totalDownload.set(items.size());
		addItemInFuture(item);
	}

	public void addAllItem(List<ItemWebData> items) {
		cancelProperty.set(false);
		this.items.addAll(items);
		// totalDownload.set(this.items.size());

		for (ItemWebData item : items) {
			addItemInFuture(item);
			System.out.println(item.getName());
		}
	}

	public void checkSpeed() {

		new Thread(() -> {
			double start = 0;
			double end = 1;
			while (speedCheckOn) {

				try {
					start = completedProperty.get();
					Thread.sleep(1000);
					end = completedProperty.get();

					double downloadeSpeed = end - start;

					speedProperty.set(downloadeSpeed);

					if (state == DownloadState.FAILED || state == DownloadState.FINISH) {
						speedProperty.set(0);
						break;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void pause() {
		items.forEach(it -> {

			System.out.println("[+] Pause");
			System.out.println("Name: " + it.getName());
			System.out.println("State: " + it.getDownloader().getState());
			System.out.println("========================================");
			it.getDownloader().setDownloadState(DownloadState.PAUSING);
			it.getDownloader().pause();

		});
		while (items.stream()
				.allMatch(it -> it.getDownloader().getDownloadState().equals(String.valueOf(DownloadState.PAUSE)))) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setDownloadState(DownloadState.PAUSE);

		pauseProperty.set(true);
	}

	public void resume() {
		items.forEach(it -> {

			System.out.println("[+] Pause");
			System.out.println("Name: " + it.getName());
			System.out.println("State: " + it.getDownloader().getDownloadState());
			System.out.println("========================================");
			it.getDownloader().resume();

		});
		setDownloadState(DownloadState.DOWNLOADING);
		pauseProperty.set(false);

	}

	public void cancelDownload() {

		items.forEach(it -> {

			System.out.println("[+] Cancel");
			System.out.println("Name: " + it.getName());
			System.out.println("State: " + it.getDownloader().getState());
			System.out.println("========================================");
			it.getDownloader().setDownloadState(DownloadState.CANCELING);
			it.getDownloader().resume();
			it.getDownloader().kill();
			// items.remove(it);

		});
		items.clear();
		sizeProperty.set(0);
		completedProperty.set(0);
		cancelProperty.set(true);
	}

	@Override
	public boolean isDone() {

		boolean allDownloaderFinish = items.stream().allMatch(it -> {
			System.out.println(it.getDownloader().downloadStateProperty().get());
			return it.getDownloader().downloadStateProperty().get().equals(String.valueOf(DownloadState.FINISH))
					|| it.getDownloader().downloadStateProperty().get().equals(String.valueOf(DownloadState.FAILED));
		});
		if (allDownloaderFinish) {
			return true;
		}
		return false;
	}

	public List<ItemWebData> getItems() {

		return items;
	}

	public DoubleProperty getPropertyProgress() {
		return propertyProgress;
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	public DownloadState getDownloadState() {
		return state;
	}

	public IntegerProperty getNumberDownloadFile() {
		return downloadFile;
	}

	public IntegerProperty getTotalDownloadFile() {
		return totalDownload;
	}

	public void setDownloadState(DownloadState state) {
		this.state = state;
	}
}
