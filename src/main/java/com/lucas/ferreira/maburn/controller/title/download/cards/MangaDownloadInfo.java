package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraped;
import com.lucas.ferreira.maburn.util.MapKeyValue;

public class MangaDownloadInfo {

	private TaggedItems taggedItems;

	public MangaDownloadInfo(TaggedItems taggedItems) {
		this.taggedItems = taggedItems;
	}

	public DownloadInfo newChapterDownloadInfo(CollectionTitle collectionTitle, ChapterScraped chapterScraped) {
		List<String> pageslinks = chapterScraped.getPagesLinks();


		DownloadInfo downloadInfo = new DownloadInfo();

		downloadInfo.setFilename(chapterScraped.getName());
		downloadInfo.setRoot(collectionTitle.getDestination());
		downloadInfo.setPrefFiletype(FileTypeAccept.JPG);
		downloadInfo.setReferer(chapterScraped.getRegisteredSite().getSiteConfig().getHomeUrl());
		downloadInfo.setUrl(chapterScraped.getSiteResult().getPageInfo().getUrl());
		downloadInfo.getListUrls().addAll(pageslinks);
		return downloadInfo;

	}
}
