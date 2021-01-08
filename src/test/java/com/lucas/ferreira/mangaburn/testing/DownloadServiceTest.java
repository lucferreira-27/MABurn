package com.lucas.ferreira.mangaburn.testing;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.download.ItemDownload;
import com.lucas.ferreira.maburn.model.download.service.DownloadService;
import com.lucas.ferreira.maburn.model.enums.DownloadType;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaYabuScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.application.Platform;
import javafx.concurrent.Worker.State;

public class DownloadServiceTest {

	@Test
	public void callDownloadServiceAnimeTest() {
		MainInterfaceView mainInterfaceView = new MainInterfaceView();
		mainInterfaceView.setVisibility(false);
		mainInterfaceView.initAndShowGUI();
		MainLoader loader = new MainLoader(new AnimeCollection());
		Collections collections = null;
		try {
			collections = (Collections) loader.loadCollection("D:\\AnimeBurn").get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CollectionItem item = new AnimeDownloaded();

		item.setName("Munou na Nana");
		item.setDestination("D:\\AnimeBurn\\Munou na Nana");
		item.setWebScraping(new GoyabuScraping());
		item.setCollections(collections);
		item.setLink("https://goyabu.com/assistir/munou-na-nana/");
		ItemDownload itemDownload = new ItemDownload(item, DownloadType.DOWNLOAD_ALL);
		itemDownload.download();
		DownloadService service = itemDownload.getDownloadService();

		ExecutorService exec = Executors.newFixedThreadPool(5);

		exec.submit(service);
		try {
			while (!service.isDone()) {
				Platform.runLater(() -> {

					CustomLogger.log("Service Progress: " + service.getProgress() * 100 + "%");

				});
				for (ItemWebData subService : service.getItems()) {
					CustomLogger.log("Item Progress: [" + subService.getName() + "] "
							+ subService.getDownloader().getDownloadProgress() * 100 + "%");
				}
				Thread.sleep(1000);

			}

			Thread.sleep(1000);

			Platform.runLater(() -> CustomLogger.log("Service Progress: " + service.getProgress() * 100 + "%"));
	
			for (ItemWebData subService : service.getItems()) {
				CustomLogger.log("Item Progress: [" + subService.getName() + "] "
						+ subService.getDownloader().getDownloadProgress()* 100 + "%");
			}

			Platform.runLater(() -> {
				service.getItems().stream().filter(i -> {
					return i.getDownloader().getState() == State.FAILED;
				}).collect(Collectors.toList()).forEach(i -> CustomLogger.log(i.getName()));

			});

			Platform.runLater(() -> {
				System.err.println("----- Erros ----");
				service.getItems().stream().filter(i -> i.getDownloader().getState() == State.FAILED).forEach(i -> {

					CustomLogger.log("Name: " + i.getName());
					CustomLogger.log("URL: " + i.getUrl());

				});
				;
				System.err.println("---------------");

			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void callDownloadServiceMangaTest() {
		MainInterfaceView mainInterfaceView = new MainInterfaceView();
		mainInterfaceView.setVisibility(false);
		mainInterfaceView.initAndShowGUI();
		MainLoader loader = new MainLoader(new MangaCollection());
		Collections collections = null;
		try {
			collections = (Collections) loader.loadCollection("D:\\UnionDownload\\host").get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CollectionItem item = new MangaDownloaded();

		item.setName("One Punch-Man");
		item.setDestination("D:\\UnionDownload\\host\\One Punch-Man");
		item.setCollections(collections);
		item.setWebScraping(new MangaYabuScraping());
		item.setLink("https://mangayabu.top/manga/one-punch-man/");
		ItemDownload itemDownload = new ItemDownload(item, DownloadType.DOWNLOAD_UPDATE);
		itemDownload.download();
		DownloadService service = itemDownload.getDownloadService();

		while (!service.isDone()) {
			try {
				Platform.runLater(() -> CustomLogger.log("Service Progress: " + service.getProgress() * 100 + "%"));
				for (ItemWebData subService : service.getItems()) {
					CustomLogger.log("Item Progress: [" + subService.getName() + "] "
							+ subService.getDownloader().getDownloadProgress() * 100 + "%");
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(1000);

			Platform.runLater(() -> CustomLogger.log("Service Progress: " + service.getProgress() * 100 + "%"));
			for (ItemWebData subService : service.getItems()) {
				CustomLogger.log("Item Progress: [" + subService.getName() + "] "
						+ subService.getDownloader().getDownloadProgress()* 100 + "%");
			}
			Platform.runLater(() -> {
				System.err.println("----- Erros ----");
				service.getItems().stream().filter(i -> i.getDownloader().getState() == State.FAILED).forEach(i -> {

					CustomLogger.log("Name: " + i.getName());
					CustomLogger.log("URL: " + i.getUrl());

				});
				;
				System.err.println("---------------");

			});

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
