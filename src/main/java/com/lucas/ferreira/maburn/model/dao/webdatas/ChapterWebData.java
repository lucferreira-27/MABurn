package com.lucas.ferreira.maburn.model.dao.webdatas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.dao.downloaded.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.download.DownloadServiceModel;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

public class ChapterWebData extends ItemWebData {
	private MangaWebData mangaWebData;
	private Downloader<CollectionItem> download;
	private String name;
	private String url;
	private List<String> listPagesUrl = new ArrayList<>();

	public ChapterWebData(MangaWebData mangaWebData) {
		
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
		
		return mangaWebData.getSite();
	}



	@Override
	public Downloader<CollectionItem> download(Collections collections, TitleDownload titleDownload) {
		download = new DownloadServiceModel();

		List<File> listFile = new ArrayList<>();
		String itemName = collections.getSelectedItem().getName();
		for (int i = 0; i < listPagesUrl.size(); i++) {
			String destination = collections.getDestination() + "\\" + itemName + "\\" + name + "\\" + i;
			listFile.add(new File(destination));
		}
		ChapterDownloaded chapter = new ChapterDownloaded();
		chapter.setName(name);
		download.initialize(listPagesUrl, chapter, listFile, this, titleDownload);
		titleDownload.getExecutorDownloader().submit(download);
		
		return download;
	}

	@Override
	public String toString() {
		return "ChapterWebData [\n - anime= " + mangaWebData.getName() + ",\n - name=" + name + ",\n - url=" + url
				+ ",\n - fetched= " + fetched + "\n]";
	}
}
