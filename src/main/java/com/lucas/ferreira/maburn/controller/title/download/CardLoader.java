package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.AnimeDownloadInfo;
import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.MangaDownloadInfo;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.util.MapKeyValue;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class CardLoader {

	private Title title;
	private DownloadList downloadList;
	public CardLoader(Title title, DownloadList downloadList) {
		this.title = title;
		this.downloadList = downloadList;
	}
	private FXMLViewLoader<StackPane> fxmlViewLoader = new FXMLViewLoader<StackPane>();

	public DownloadCardFull load(ItemScraped itemScraped) {
		return loadDownloadCard(itemScraped);
	}
	
	public FetchCardFull loadFetchCard(ScrapingWork scrapingWork) {
		FetchCard fetchCard = new FetchCard();
		
		
		String itemName = MapKeyValue.getKeyByValue(title.getTaggedItems().getNamedItemsValues(), scrapingWork.getTarget());
		String itemUrl = scrapingWork.getTarget();
	
		FetchCardValues fetchCardValues = new FetchCardValues(title.getCollectionTitle());		
		fetchCardValues.setItemName(itemName);
		fetchCardValues.setItemUrl(itemUrl);
		
		
		Node node = loadCardFxml(fetchCard, itemName, CardFXML.FETCH_CARD);
		FetchCardFull cardFull= new FetchCardController(fetchCard, fetchCardValues).initialize();
		cardFull.setNode(node);
		return cardFull;
		
	}
	
	private DownloadCardFull loadDownloadCard(ItemScraped itemScraped) {
		
		
		
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
			
			return loadDownloadChapterCard(downloadInfo);
			
		}
		return null;
	}


	private DownloadCardFull loadDownloadEpisodeCard(DownloadInfo downloadInfo) {
		EpisodeCard episodeCard = new EpisodeCard();

		Node node =loadCardFxml(episodeCard, downloadInfo.getFilename(), CardFXML.DOWNLOAD_EPISODE_CARD);
		
		DownloadCardFull downloadCardFull = new EpisodeCardController(episodeCard, downloadInfo, downloadList.getContentDownloadList()).initialize();
		downloadCardFull.setNode(node);
		return downloadCardFull;
		
	}

	private DownloadCardFull loadDownloadChapterCard(DownloadInfo downloadInfo) {
		ChapterCard chapterCard = new ChapterCard();

		Node node =loadCardFxml(chapterCard, downloadInfo.getFilename(), CardFXML.DOWNLOAD_CHAPTER_CARD);
		
		DownloadCardFull downloadCardFull = new ChapterCardController(chapterCard, downloadInfo, downloadList.getContentDownloadList()).initialize();
		downloadCardFull.setNode(node);

		 return downloadCardFull;

	}

	
	public Node loadCardFxml(Initializable initializable, String name, CardFXML cardFxml) {
		StackPane stackPane = new StackPane();
		try {
			StackPane item = fxmlViewLoader.load(cardFxml.getFxml(), initializable, stackPane);
			defineId(name, item);
			return item;

		} catch (IOException e) {
			 
			e.printStackTrace();
			return null;
		}
	}

	private void defineId(String name, StackPane item) {
		String definedId = name;
		item.setUserData(definedId);
	}

}
