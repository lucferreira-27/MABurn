package com.lucas.ferreira.mangaburn.testing;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.model.update.ItemUpdate;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemUpdateTest {
	// @Test
	public void updateAnime() throws Exception {

		MainInterfaceView mainInterfaceView = new MainInterfaceView();
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

		ItemUpdate itemUpdate = new ItemUpdate((List<ItemWebData>) item,null);
		List<ItemWebData> list = itemUpdate.update();
		try {
			while (list.get(0).getDownloader() == null) {
				Thread.sleep(1000);
				System.out.println("Waiting service start");
			}
			Downloader<CollectionSubItem> service = list.get(0).getDownloader();

			while (!service.isDone()) {
				System.out.println(service.getDownloadProgress());
				Thread.sleep(1500);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	// @Test
	public void updateManga() {

		MainInterfaceView mainInterfaceView = new MainInterfaceView();
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
		
		ItemUpdate itemUpdate = new ItemUpdate((List<ItemWebData>) item, null);
		List<ItemWebData> list = itemUpdate.update();
		try {

			while (list.get(0).getDownloader() == null) {
				Thread.sleep(1000);
				System.out.println("Waiting service start");
			}
			Downloader<CollectionSubItem> service = list.get(0).getDownloader();
			while (!service.isDone()) {
				Thread.sleep(1000);
				System.out.println(service.getDownloadProgress());

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testProperty() {
		IntegerProperty property = new SimpleIntegerProperty();
		IntegerProperty property2 = new SimpleIntegerProperty();

		
		property2.bindBidirectional(property);
		
		property.addListener((observable, oldvalue, newvalue) -> {

			System.out.println("Test");
		});
		property2.addListener((observable, oldvalue, newvalue) -> {

			System.out.println("Test-2");
		});
		
		for(; property.get() < 10;) {
			property.set(property.add(1).get());
		}
		


	}
}
