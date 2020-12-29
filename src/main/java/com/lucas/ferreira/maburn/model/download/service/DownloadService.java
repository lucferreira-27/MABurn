package com.lucas.ferreira.maburn.model.download.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.ItemWebDataResponseUtil;
import com.lucas.ferreira.maburn.util.ResponseUtil;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

public class DownloadService extends Task<Downloader<ItemWebData>> {
	private List<ItemWebData> items = new ArrayList<ItemWebData>();
	private Collections collections;
	private DoubleProperty propertyProgress = new SimpleDoubleProperty();
	private BooleanProperty pauseProperty = new SimpleBooleanProperty();
	private BooleanProperty cancelProperty = new SimpleBooleanProperty();

	private DoubleProperty sizeProperty = new SimpleDoubleProperty();

	private DoubleProperty speedProperty = new SimpleDoubleProperty();

	private DoubleProperty completedProperty = new SimpleDoubleProperty();
	private DownloadState state;
	private boolean speedCheckOn = false;
	private List<Future<Downloader<CollectionSubItem>>> futureItemWebData = new ArrayList<Future<Downloader<CollectionSubItem>>>();
	private ExecutorService exec = Executors.newFixedThreadPool(5);

	public DownloadService(List<ItemWebData> items, Collections collections) {
		// TODO Auto-generated constructor stub
		this.items.addAll(items);

		this.collections = collections;

		propertyProgress.addListener((observable, oldvalue, newvalue) -> {
			updateProgress(completedProperty.get(), sizeProperty.get());
//			System.err.println("progress: " + progressProperty());
//			System.err.println("Size: " + sizeProperty.get());
//			System.err.println("Completed: " + completedProperty.get());
//			System.err.println("Speed: " + speedProperty.get());

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
				Downloader<CollectionSubItem> downloader = item.getDownloader();

				downloader.sizeProperty().addListener((observable, oldvalue, newvalue) -> {
					sizeProperty.set(sizeProperty.add(newvalue.doubleValue()).get());
				});

				downloader.completedProperty().addListener((observable, oldvalue, newvalue) -> {
					completedProperty.set(completedProperty.subtract(oldvalue.doubleValue()).get());
					completedProperty.set(completedProperty.add(newvalue.doubleValue()).get());
				});
				
				downloader.downloadStateProperty().addListener((observable, oldvalue, newvalue) -> {
					if (newvalue.equals(String.valueOf(DownloadState.FAILED))
							|| newvalue.equals(String.valueOf(DownloadState.CANCELING))) {
						
						Double value = downloader.completedProperty().get();
						completedProperty.set(completedProperty.subtract(value).get());

					}

				});

				downloader.progressProperty().addListener((observable, oldvalue, newvalue) -> {
					checkProgress();
				});

				downloader.setOnFailed(event -> {
					System.err.println("The task failed with the following exception:");
					exceptionProperty().get().printStackTrace();

				});

				item.download(collections);

				return downloader;

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
		addItemInFuture(item);
	}

	public void addAllItem(List<ItemWebData> items) {
		this.items.addAll(items);
		for (ItemWebData item : items) {
			addItemInFuture(item);
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

		});
		sizeProperty.set(0);
		completedProperty.set(0);
		cancelProperty.set(true);
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

	public void setDownloadState(DownloadState state) {
		this.state = state;
	}
}
