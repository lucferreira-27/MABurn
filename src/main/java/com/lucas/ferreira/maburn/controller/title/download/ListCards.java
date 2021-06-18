package com.lucas.ferreira.maburn.controller.title.download;

import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardValuesBinder;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.download.item.ItemDownload;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ChapterScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.MangaScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.util.MapKeyValue;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class ListCards {

	private ListDownloadsCards listDownloadsCards;
	private ListFetchCards listFetchCards;
	private final VBox vBoxListDownloads;
	private ObservableList<ItemScraped> obsItemScrapeds;
	private CollectionTitle collectionTitle;

	public ListCards(CollectionTitle collectionTitle, VBox vBoxListDownloads) {
		this.vBoxListDownloads = vBoxListDownloads;
		this.collectionTitle = collectionTitle;
		listDownloadsCards = new ListDownloadsCards(vBoxListDownloads);
		listFetchCards = new ListFetchCards(vBoxListDownloads);
	}

	public void onAddScrapingDone(TaggedItems taggedItems) {
		obsItemScrapeds.addListener(new ListChangeListener<ItemScraped>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends ItemScraped> c) {

				c.next();
				ItemScraped itemScraped = c.getList().get(c.getFrom());

				if (itemScraped.getSite().getCategory() == Category.ANIME) {
					EpisodeScraped episodeScraped = (EpisodeScraped) itemScraped;
					String directDownload = episodeScraped.getVideoLinks().get(Definition.DEFINITION_1080);

					String itemName = MapKeyValue.getKeyByValue(taggedItems.getNamedItemsValues(),
							itemScraped.getUrl());
					DownloadInfo downloadInfo = new DownloadInfo();
					downloadInfo.setFilename(itemName);
					downloadInfo.setPath(collectionTitle.getDestination());
					downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
					downloadInfo.setReferer(itemScraped.getSite().getUrl());
					downloadInfo.setUrl(directDownload);
					EpisodeDownloadItemValues episodeDownloadItemValues = new EpisodeDownloadItemValues();
					EpisodeDownload episodeDownload = new EpisodeDownload(downloadInfo, episodeDownloadItemValues);
					addDownloadCard(episodeDownload, downloadInfo);

				}
			}

		});
	}

	public void addDownloadCard(EpisodeDownload episodeDownload, DownloadInfo downloadInfo) {
		EpisodeCard episodeCard = new EpisodeCard();
		listDownloadsCards.add(episodeCard, downloadInfo);

	}

	public void addFetchCard(FetchCardValues fetchCardValues) {
		FetchCard fetchCard = new FetchCard();
		listFetchCards.add(fetchCard, fetchCardValues);
	}
	
	public void replaceFethCardToDownloadCard(Node newDownloadCard, String name) {
		Node fetchCard = vBoxListDownloads.getChildren().stream().filter(child -> child.getUserData().equals(name)).findFirst().get();
		vBoxListDownloads.getChildren().set(vBoxListDownloads.getChildren().indexOf(fetchCard),newDownloadCard);
	}

	public ListDownloadsCards getListDownloadsCards() {
		return listDownloadsCards;
	}

	public ListFetchCards getListFetchCards() {
		return listFetchCards;
	}

	public ObservableList<ItemScraped> getObsItemScrapeds() {
		return obsItemScrapeds;
	}

	public void setObsItemScrapeds(ObservableList<ItemScraped> obsItemScrapeds) {
		this.obsItemScrapeds = obsItemScrapeds;
	}

	public VBox getVBoxListDownloads() {
		return vBoxListDownloads;
	}

}
