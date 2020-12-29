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
import com.lucas.ferreira.maburn.model.download.ItemDownload;
import com.lucas.ferreira.maburn.model.enums.DownloadType;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.model.webscraping.sites.GoyabuScraping;
import com.lucas.ferreira.maburn.model.webscraping.sites.MangaYabuScraping;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.application.Platform;

public class ItemDownloadTest {

	@Test
	public void downloadAnime() throws Exception {
		int index = 0;
		
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
		item.setLink("https://goyabu.com/assistir/munou-na-nana/");
		item.setDestination("D:\\AnimeBurn\\Munou na Nana");
		item.setCollections(collections);
		item.setWebScraping(new GoyabuScraping());

		ItemDownload itemDownload = new ItemDownload(item, DownloadType.DOWNLOAD_SELECT, index);
		List<ItemWebData> list = itemDownload.download();
		try {
			while (list.get(index).getDownloader() == null) {
				Thread.sleep(1000);
				System.out.println("Waiting service start");
			}
			Downloader<CollectionSubItem> service = list.get(index).getDownloader();

			while (!service.isDone()) {
				System.out.println(service.getDownloadProgress());
				Thread.sleep(150);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Test
	public void downloadManga() throws Exception {
		int index = 50;
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
		item.setLink("https://mangayabu.top/manga/one-punch-man/");
		item.setCollections(collections);
		item.setWebScraping(new MangaYabuScraping());

		ItemDownload itemUpdate = new ItemDownload(item, DownloadType.DOWNLOAD_SELECT, index);
		List<ItemWebData> list = itemUpdate.download();
		try {

			Downloader<CollectionSubItem> service = list.get(index).getDownloader();

			while (!service.isDone()) {
				System.out.println(service.getDownloadProgress());
				Thread.sleep(1500);
			}
			System.out.println(service.getDownloadProgress());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}
