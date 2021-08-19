package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.util.MapKeyValue;

public class AnimeDownloadInfo {

	private TaggedItems taggedItems;

	public AnimeDownloadInfo(TaggedItems taggedItems) {
		this.taggedItems = taggedItems;
	}

	public DownloadInfo newEpisodeDownloadInfo(CollectionTitle collectionTitle, EpisodeScraped episodeScraped) {
		String directDownload = episodeScraped.getVideoLinks().get(Definition.DEFINITION_1080);
	

		DownloadInfo downloadInfo = new DownloadInfo();

		downloadInfo.setFilename(episodeScraped.getName());
		downloadInfo.setRoot(collectionTitle.getDestination());
		downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
		downloadInfo.setReferer(episodeScraped.getRegisteredSite().getSiteConfig().getHomeUrl());
		downloadInfo.setUrl(directDownload);
		return downloadInfo;

	}
	
}
