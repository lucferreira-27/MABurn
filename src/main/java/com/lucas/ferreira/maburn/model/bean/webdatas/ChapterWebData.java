package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.DownloadServiceModel;
import com.lucas.ferreira.maburn.model.bean.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class ChapterWebData implements ItemWebData {
	private MangaWebData mangaWebData;
	private String name;
	private String url;
	private List<String> listPagesUrl = new ArrayList<>();
	private final ExecutorService exec = Executors.newFixedThreadPool(50, r -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

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
	public CollectionSubItem download(Collections collections) {
		ChapterDownloaded chapterDownloaded = new ChapterDownloaded();
		for (int i = 0; i < listPagesUrl.size(); i++) {
			String destination = collections.getDestination() + "\\" + mangaWebData.getName() + "\\" + name + "\\" + i
					+ ".jpg";
			String pageUrl = listPagesUrl.get(i);
			System.out.println(destination);
			DownloadServiceModel download = new DownloadServiceModel(pageUrl, new File(destination));
			try {

				download.download(pageUrl, mangaWebData.getSite());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		chapterDownloaded.setDestination(collections.getDestination() + "\\" + mangaWebData.getName() + "\\" + name);
		return chapterDownloaded;
	}
}
