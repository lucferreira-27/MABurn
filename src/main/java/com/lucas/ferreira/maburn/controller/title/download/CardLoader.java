package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.AnimeDownloadInfo;
import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.MangaDownloadInfo;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.util.MapKeyValue;

public class CardLoader {

	private Title title;
	public CardLoader(Title title) {
		this.title = title;
	}
	
	public DownloadCardLoaded load(ItemScraped itemScraped) {
		return loadDownloadCard(itemScraped);
	}
	
	public FetchCardLoaded loadFetchCard(ScrapingWork scrapingWork) {
		FetchCard fetchCard = new FetchCard();
		System.out.println(title.getTaggedItems());
		System.out.println(scrapingWork);
		String itemName = MapKeyValue.getKeyByValue(title.getTaggedItems().getNamedItemsValues(), scrapingWork.getTarget());
		String itemUrl = scrapingWork.getTarget();
	
		FetchCardValues fetchCardValues = new FetchCardValues(title.getCollectionTitle());		
		FetchCardLoaded fetchCardLoaded = new FetchCardLoaded();
		fetchCardValues.setItemName(itemName);
		fetchCardValues.setItemUrl(itemUrl);
		fetchCardLoaded.setFetchCard(fetchCard);
		fetchCardLoaded.setFetchCardValues(fetchCardValues);
		
		
		return fetchCardLoaded;
	}
	
	private DownloadCardLoaded loadDownloadCard(ItemScraped itemScraped) {
		if (itemScraped.getSite().getCategory() == Category.ANIME) {

			AnimeDownloadInfo animeDownloadInfo = new AnimeDownloadInfo(title.getTaggedItems());
			DownloadInfo downloadInfo = animeDownloadInfo.newEpisodeDownloadInfo(title.getCollectionTitle(),
					(EpisodeScraped) itemScraped);
			return loadDownloadEpisodeCard(downloadInfo);
			 
		
		}
		if (itemScraped.getSite().getCategory() == Category.MANGA) {
			MangaDownloadInfo mangaDownloadInfo = new MangaDownloadInfo(title.getTaggedItems());
			DownloadInfo downloadInfo = mangaDownloadInfo.newChapterDownloadInfo(title.getCollectionTitle(),
					(ChapterScraped) itemScraped);
			System.out.println(downloadInfo);
			return loadDownloadChapterCard(downloadInfo);
			
		}
		return null;
	}


	private DownloadCardLoaded loadDownloadEpisodeCard(DownloadInfo downloadInfo) {
		EpisodeCard episodeCard = new EpisodeCard();
		DownloadCardLoaded downloadCardLoaded = new DownloadCardLoaded();
		downloadCardLoaded.setDownloadCard(episodeCard);
		downloadCardLoaded.setCardFXML(CardFXML.DOWNLOAD_EPISODE_CARD);
		downloadCardLoaded.setDownloadInfo(downloadInfo);
		return downloadCardLoaded;

	}

	private DownloadCardLoaded loadDownloadChapterCard(DownloadInfo downloadInfo) {
		ChapterCard chapterCard = new ChapterCard();
		DownloadCardLoaded downloadCardLoaded = new DownloadCardLoaded();
		downloadCardLoaded.setDownloadCard(chapterCard);
		downloadCardLoaded.setCardFXML(CardFXML.DOWNLOAD_CHAPTER_CARD);
		downloadCardLoaded.setDownloadInfo(downloadInfo);
		return downloadCardLoaded;
	}
	



}
