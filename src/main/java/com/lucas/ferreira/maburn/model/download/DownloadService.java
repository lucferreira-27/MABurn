package com.lucas.ferreira.maburn.model.download;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.ItemWebDataResponseUtil;
import com.lucas.ferreira.maburn.util.ResponseUtil;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

public class DownloadService extends Task<Downloader<ItemWebData>> {
	private List<ItemWebData> items;
	private State actualState = State.READY;
	private Collections collections;
	private IntegerProperty element = new SimpleIntegerProperty();
	private IntegerProperty propertyProgress = new SimpleIntegerProperty();
	private BooleanProperty pauseProperty = new SimpleBooleanProperty();

	public DownloadService(List<ItemWebData> items, Collections collections) {
		// TODO Auto-generated constructor stub
		this.items = items;

		this.collections = collections;
		if (items.stream().anyMatch(item -> item.getDownloader() == null))
			throw new DownloadServiceException("Items need be fetched first!");

		propertyProgress.addListener((observable, oldvalue, newvalue) -> {

			this.updateProgress(newvalue.doubleValue(), items.size());

		});
		items.forEach(item -> {
			pauseProperty.bindBidirectional(item.getDownloader().getPauseProperty());
		});

	}

	@Override
	protected Downloader<ItemWebData> call() throws Exception {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newFixedThreadPool(5);
		List<Future<Downloader<CollectionSubItem>>> futureItemWebData = new ArrayList<Future<Downloader<CollectionSubItem>>>();
		while (element.get() < items.size()) {
			futureItemWebData.add(exec.submit(new Callable<Downloader<CollectionSubItem>>() {
				int i = element.get();

				@Override
				public Downloader<CollectionSubItem> call() {
					// TODO Auto-generated method stub
					Downloader<CollectionSubItem> downloader = items.get(i).getDownloader();

					downloader.setOnFailed(event -> {
						System.err.println("The task failed with the following exception:");
						exceptionProperty().get().printStackTrace();
						
					});

					downloader.progressProperty().addListener((observable, oldvalue, newvalue) -> {
						if (newvalue.intValue() == 1) {
							propertyProgress.setValue(propertyProgress.add(1).get());

						}

					});

					items.get(i).download(collections);

					return downloader;

				}

			}));

			element.set(element.add(1).get());
		}
		ResponseUtil futureResponse = new ItemWebDataResponseUtil(this.items);
		futureResponse.await();
		updateProgress(1, 1);

		return null;
	}

	public void pause() {
		pauseProperty.set(true);
	}

	public void unpause() {
		pauseProperty.set(false);

	}

	public List<ItemWebData> getItems() {

		return items;
	}

	public IntegerProperty getPropertyProgress() {
		return propertyProgress;
	}

}
