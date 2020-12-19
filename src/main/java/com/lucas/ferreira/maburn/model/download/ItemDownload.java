package com.lucas.ferreira.maburn.model.download;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.download.service.DownloadService;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.DownloadType;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.update.Fetcher;
import com.lucas.ferreira.maburn.model.update.ItemUpdate;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.FutureResponseUtil;
import com.lucas.ferreira.maburn.util.ResponseUtil;

import javafx.concurrent.Task;

public class ItemDownload extends Task<Void> {
	private DownloadService downloadService;
	private ResponseUtil futureResponse;
	private WebScraping scraping;
	private CollectionItem collectionItem;
	private DownloadType downloadType;
	private int index = 5;
	private int current = 0;
	private int begin = -1;
	private int end = -1;

	private final ExecutorService exec = Executors.newFixedThreadPool(5);

	public ItemDownload(CollectionItem item, DownloadType downloadType) {
		this.collectionItem = item;
		this.downloadType = downloadType;
		this.scraping = item.getWebScraping();

	}

	public ItemDownload(CollectionItem item, DownloadType downloadType, int index) {
		this.collectionItem = item;
		this.scraping = item.getWebScraping();
		this.index = index;
		this.downloadType = downloadType;
	}

	public ItemDownload(CollectionItem item, DownloadType downloadType, int begin, int end) {
		this.collectionItem = item;
		this.scraping = item.getWebScraping();
		this.begin = begin;
		this.end = end;
		this.downloadType = downloadType;
	}

	public List<ItemWebData> synchronizeItem() {

		TitleWebData title = null;
		List<ItemWebData> items;

		if (collectionItem.getCategory() == Category.ANIME) {
			title = new AnimeWebData(collectionItem.getName());
		} else if (collectionItem.getCategory() == Category.MANGA) {
			title = new MangaWebData(collectionItem.getName());
		}
		title.setUrl(collectionItem.getLink());
		items = scraping.fecthTitle(title).getWebDatas();

		return items;

	}

	private List<Future<ItemWebData>> fetchAllItems(List<ItemWebData> items) {
		List<Future<ItemWebData>> futureItemWebData = new ArrayList<Future<ItemWebData>>();

		for (int i = 0; i < items.size(); i++) {

			futureItemWebData.add(exec.submit(new Fetcher(items.get(i), scraping)));
			try {
				// Delay to avoid limit-rate
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return futureItemWebData;
	}

	private List<Future<ItemWebData>> fetchRangeItems(List<ItemWebData> items) {
		List<Future<ItemWebData>> futureItemWebData = new ArrayList<Future<ItemWebData>>();

		for (current = begin; current <= end; current++) {

			futureItemWebData.add(exec.submit(new Fetcher(items.get(current), scraping)));
			try {
				// Delay to avoid limit-rate
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return futureItemWebData;
	}

	private List<Future<ItemWebData>> fetchSelectedItem(List<ItemWebData> items) {
		List<Future<ItemWebData>> futureItemWebData = new ArrayList<Future<ItemWebData>>();

		futureItemWebData.add(exec.submit(new Fetcher(items.get(index), scraping)));

		return futureItemWebData;
	}

	private List<Future<ItemWebData>> fetchUpdateItems(List<ItemWebData> items) {
		List<Future<ItemWebData>> futureItemWebData = new ArrayList<Future<ItemWebData>>();
		ItemUpdate itemUpdate = new ItemUpdate(items, collectionItem);
		items = itemUpdate.update();
	
		for (int i = 0; i < items.size(); i++) {
			// The Fetcher will fetch all items in data one by one and add them to the
			// future list.
			futureItemWebData.add(exec.submit(new Fetcher(items.get(i), scraping)));
			try {
				// Delay to avoid limit-rate
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return futureItemWebData;
	}

	private List<Future<ItemWebData>> fetchItems(List<ItemWebData> items) {
		List<Future<ItemWebData>> futureItemWebData = new ArrayList<Future<ItemWebData>>();
		ExecutorService exec = Executors.newFixedThreadPool(10);

		switch (downloadType) {
		case DOWNLOAD_ALL:
			return fetchAllItems(items);
		case DOWNLOAD_RANGE:
			return fetchRangeItems(items);
		case DOWNLOAD_SELECT:
			return fetchSelectedItem(items);
		case DOWNLOAD_UPDATE:
			return fetchUpdateItems(items);
		default:
			break;
		}

		return futureItemWebData;
	}

	public List<ItemWebData> download() {
		List<ItemWebData> items = synchronizeItem();
		List<Future<ItemWebData>> futureItemWebData = fetchItems(items);
		futureResponse = new FutureResponseUtil<ItemWebData>(futureItemWebData);
		futureResponse.await();
		items.clear();
		futureItemWebData.forEach(future ->{
			try {
				items.add(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		switch (downloadType) {
		case DOWNLOAD_ALL:
			downloadAll(items);
			break;
		case DOWNLOAD_RANGE:
			downloadWithRange(items);
			break;
		case DOWNLOAD_SELECT:
			downloadSelected(items);
			break;
		case DOWNLOAD_UPDATE:
			downloadUpdate(items);
			break;
		default:
			break;
		}
		return items;
	}

	private void downloadUpdate(List<ItemWebData> items) {
		downloadService = new DownloadService(items, collectionItem.getCollections());
		exec.submit(downloadService);
	}

	private void downloadWithRange(List<ItemWebData> items) {

		downloadService = new DownloadService(items, collectionItem.getCollections());
		exec.submit(downloadService);

	}

	private void downloadSelected(List<ItemWebData> items) {

		ItemWebData item = items.get(0);

		downloadService = new DownloadService(Arrays.asList(item), collectionItem.getCollections());
		exec.submit(downloadService);

	}

	private void downloadAll(List<ItemWebData> items) {

		downloadService = new DownloadService(items, collectionItem.getCollections());
		exec.submit(downloadService);

	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public DownloadService getDownloadService() {
		return downloadService;
	}

	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
