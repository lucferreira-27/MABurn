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

		String itemName = MapKeyValue.getKeyByValue(taggedItems.getNamedItemsValues(), chapterScraped.getUrl());

		DownloadInfo downloadInfo = new DownloadInfo();

		downloadInfo.setFilename(itemName);
		downloadInfo.setPath(collectionTitle.getDestination());
		downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
		downloadInfo.setReferer(chapterScraped.getSite().getUrl());
		downloadInfo.setUrl(chapterScraped.getUrl());
		downloadInfo.getListUrls().addAll(pageslinks);
		return downloadInfo;

	}
}
