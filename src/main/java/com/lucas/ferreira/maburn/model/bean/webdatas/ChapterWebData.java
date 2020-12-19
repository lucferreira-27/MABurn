package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.bean.GenericItem;
import com.lucas.ferreira.maburn.model.bean.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadMultipleServiceModel;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class ChapterWebData implements ItemWebData, GenericItem {
	private MangaWebData mangaWebData;
	private Downloader<CollectionSubItem> download = new DownloadMultipleServiceModel();
	private String name;
	private String url;
	private List<String> listPagesUrl = new ArrayList<>();

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

	public Downloader<CollectionSubItem> getDownloader() {
		return download;
	}

	@Override
	public Downloader<CollectionSubItem> download(Collections collections) {
		List<File> listFile = new ArrayList<>();
		for (int i = 0; i < listPagesUrl.size(); i++) {
			String destination = collections.getDestination() + "\\" + mangaWebData.getName() + "\\" + name + "\\" + i;
			listFile.add(new File(destination));
		}
		download.initialize(listPagesUrl, new ChapterDownloaded(), listFile, mangaWebData.getSite());
		ExecutorService exec = Executors.newFixedThreadPool(5);
		exec.submit(download);
		System.out.println(download.getProgress());
		return download;
	}
}
