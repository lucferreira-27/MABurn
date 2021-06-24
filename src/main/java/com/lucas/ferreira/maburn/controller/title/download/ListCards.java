package com.lucas.ferreira.maburn.controller.title.download;

import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardValuesBinder;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.AnimeDownloadInfo;
import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardState;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.MangaDownloadInfo;
import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.download.item.ItemDownload;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.util.MapKeyValue;
import com.lucas.ferreira.maburn.view.Components;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class ListCards {

	private ListDownloadsCards listDownloadsCards;
	private ListFetchCards listFetchCards;
	private final VBox vBoxListDownloads;
	private ObservableList<ScrapingWork> obsScrapingWorks;
	private CollectionTitle collectionTitle;

	public ListCards(CollectionTitle collectionTitle, VBox vBoxListDownloads) {
		this.vBoxListDownloads = vBoxListDownloads;
		this.collectionTitle = collectionTitle;
		listDownloadsCards = new ListDownloadsCards(vBoxListDownloads);
		listFetchCards = new ListFetchCards(vBoxListDownloads);
	}

	public void onAddScrapingDone(TaggedItems taggedItems) {
		
		
		
		
		listenObservableScrapingWorks(taggedItems);

		for (ScrapingWork scrapingWork : obsScrapingWorks) {
			listenScrapingWork(taggedItems, scrapingWork);
	
		}

	}

	private void listenObservableScrapingWorks(TaggedItems taggedItems) {
		obsScrapingWorks.addListener(new ListChangeListener<ScrapingWork>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends ScrapingWork> c) {

				c.next();
				ScrapingWork scrapingWork = c.getList().get(c.getFrom());
				System.out.println(scrapingWork);
				listenScrapingWork(taggedItems, scrapingWork);

			}

		});
	}

	private void listenScrapingWork(TaggedItems taggedItems, ScrapingWork scrapingWork) {
		
		
		String itemName = MapKeyValue.getKeyByValue(taggedItems.getNamedItemsValues(), scrapingWork.getTarget());
		String itemUrl = scrapingWork.getTarget();
		FetchCardValues fetchCardValues = new FetchCardValues(collectionTitle);
	
		fetchCardValues.setItemName(itemName);
		fetchCardValues.setItemUrl(itemUrl);
		fetchCardValues.getFetchCardState().set(FetchCardState.IN_QUEUE);
	
		addFetchCard(fetchCardValues);

		scrapingWork.getPropertyScrapeState().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == ScrapeState.SUCCEED) {
				ItemScraped itemScraped;
				try {
					itemScraped = scrapingWork.getWorkResult();
					System.out.println(itemScraped.toString());

					addDownloadCard(itemScraped, taggedItems);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (newvalue == ScrapeState.FAILED) {
				try {
					ItemScraped itemScraped = scrapingWork.getWorkResult();
					throw itemScraped.getException();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	private void addDownloadCard(ItemScraped itemScraped, TaggedItems taggedItems) {
		if (itemScraped.getSite().getCategory() == Category.ANIME) {

			AnimeDownloadInfo animeDownloadInfo = new AnimeDownloadInfo(taggedItems);
			DownloadInfo downloadInfo = animeDownloadInfo.newEpisodeDownloadInfo(collectionTitle,
					(EpisodeScraped) itemScraped);
			addDownloadEpisodeCard(downloadInfo);
			return;
		}
		if (itemScraped.getSite().getCategory() == Category.MANGA) {
			MangaDownloadInfo mangaDownloadInfo = new MangaDownloadInfo(taggedItems);
			DownloadInfo downloadInfo = mangaDownloadInfo.newChapterDownloadInfo(collectionTitle,
					(ChapterScraped) itemScraped);
			addDownloadChapterCard(downloadInfo);
			return;
		}
	}

	public void addDownloadEpisodeCard(DownloadInfo downloadInfo) {
		EpisodeCard episodeCard = new EpisodeCard();
		listDownloadsCards.add(episodeCard, downloadInfo, CardFXML.DOWNLOAD_EPISODE_CARD);

	}

	public void addDownloadChapterCard(DownloadInfo downloadInfo) {
		ChapterCard chapterCard = new ChapterCard();
		listDownloadsCards.add(chapterCard, downloadInfo, CardFXML.DOWNLOAD_CHAPTER_CARD);

	}

	public void addFetchCard(FetchCardValues fetchCardValues) {
		FetchCard fetchCard = new FetchCard();
		listFetchCards.add(fetchCard, fetchCardValues);
	}

	public void replaceFethCardToDownloadCard(Node newDownloadCard, String name) {
		Node fetchCard = vBoxListDownloads.getChildren().stream().filter(child -> child.getUserData().equals(name))
				.findFirst().get();
		vBoxListDownloads.getChildren().set(vBoxListDownloads.getChildren().indexOf(fetchCard), newDownloadCard);
	}

	public ListDownloadsCards getListDownloadsCards() {
		return listDownloadsCards;
	}

	public ListFetchCards getListFetchCards() {
		return listFetchCards;
	}

	public ObservableList<ScrapingWork> getObsScrapingWorks() {
		return obsScrapingWorks;
	}

	public void setObsScrapingWorks(ObservableList<ScrapingWork> obsScrapingWorks) {
		System.out.println("setObs");
		this.obsScrapingWorks = obsScrapingWorks;
	}

	public VBox getVBoxListDownloads() {
		return vBoxListDownloads;
	}

}
