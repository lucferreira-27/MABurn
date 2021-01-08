package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.bean.GenericItem;
import com.lucas.ferreira.maburn.model.bean.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadMultipleServiceModel;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadServiceModel;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class ChapterWebData extends ItemWebData {
	private MangaWebData mangaWebData;
	private Downloader<CollectionSubItem> download = new DownloadServiceModel();
	private String name;
	private String url;
	private List<String> listPagesUrl = new ArrayList<>();
	private static ExecutorService exec = Executors.newFixedThreadPool(3);

	public ChapterWebData(MangaWebData mangaWebData) {
		// TODO Auto-generated constructor stub
		this.mangaWebData = mangaWebData;
	}

	public List<String> getListPagesUrl() {
		return listPagesUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void addPagesUrl(String url) {
		listPagesUrl.add(url);
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return mangaWebData.getSite();
	}

	public Downloader<CollectionSubItem> getDownloader() {
		return download;
	}

	@Override
	public Downloader<CollectionSubItem> download(Collections collections) {
		List<File> listFile = new ArrayList<>();
		String itemName = collections.getActualItem().getName();
		for (int i = 0; i < listPagesUrl.size(); i++) {
			String destination = collections.getDestination() + "\\" + itemName + "\\" + name + "\\" + i;
			listFile.add(new File(destination));
		}
		ChapterDownloaded chapter = new ChapterDownloaded();
		chapter.setName(name);
		download.initialize(listPagesUrl, chapter, listFile, this);
		exec.submit(download);
		return download;
	}


}


