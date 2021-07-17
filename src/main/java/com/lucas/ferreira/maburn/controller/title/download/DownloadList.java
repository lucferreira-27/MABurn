package com.lucas.ferreira.maburn.controller.title.download;

import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardState;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DownloadList {

	private NavDownloadList navDownloadList = new NavDownloadList(this);
	private ContentDownloadList contentDownloadList = new ContentDownloadList(navDownloadList);
	private ContentFetchList contentFetchList = new ContentFetchList(navDownloadList);
	private Title title;
	private ListAddCard listAddCard;

	public DownloadList(Title title) {
		this.title = title;
		
	}

	public void onScrapingWork(List<ScrapingWork> scrapingWorks) {

		for (ScrapingWork scrapingWork : scrapingWorks) {
			addListener(scrapingWork);
		}

	}

	private void addListener(ScrapingWork scrapingWork) {

		CardLoader cardLoader = new CardLoader(title);
		FetchCardLoaded fetchCardLoaded = cardLoader.loadFetchCard(scrapingWork);
		listAddCard = new ListAddCard(title.getTitleDownload().getvBoxListDownloads());
		FetchCardFull fetchCardFull = listAddCard.createFetchCardFull(fetchCardLoaded);

		contentFetchList.addCard(fetchCardFull);
		updateNavDownloadListCardTotal(navDownloadList.getTotalCards().get() + 1);
		fetchCardFull.getCardController().show();

		scrapingWork.getPropertyScrapeState().addListener((obs, oldvalue, newvalue) -> {
			contentFetchList.updateCardsValues();
			FetchCardValues fetchCardValues = fetchCardLoaded.getFetchCardValues();
			
			if (newvalue == ScrapeState.WORKING) {
				fetchCardValues.getFetchCardState().set(FetchCardState.WORKING);
			}
			

			else if (newvalue == ScrapeState.SUCCEED) {
				ItemScraped itemScraped;
				try {
					itemScraped = scrapingWork.getWorkResult();
					
					DownloadCardLoaded downloadCardLoaded = cardLoader.load(itemScraped);

					fetchCardValues.getFetchCardState().set(FetchCardState.READY);
					DownloadCardFull downloadCardFull = listAddCard.createDownloadCardFull(downloadCardLoaded);
					contentDownloadList.addCard(downloadCardFull);
					downloadCardFull.getCardValues().getDownloadProgressState().addListener(navUpdate());
				} catch (Exception e) {

					e.printStackTrace();
				}

			} else if (newvalue == ScrapeState.FAILED) {
				try {
					fetchCardValues.getFetchCardState().set(FetchCardState.ERROR);
					ItemScraped itemScraped = scrapingWork.getWorkResult();
					throw itemScraped.getException();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	private ChangeListener<DownloadProgressState> navUpdate() {
		return new ChangeListener<DownloadProgressState>() {

			@Override
			public void changed(ObservableValue<? extends DownloadProgressState> observable,
					DownloadProgressState oldValue, DownloadProgressState newValue) {
				contentDownloadList.updateCardsValues();
				contentFetchList.updateCardsValues();
			}

		};
	}

	private void updateNavDownloadListCardTotal(int total) {
		navDownloadList.getTotalCards().set(total);
	}

	public ContentDownloadList getContentDownloadList() {
		return contentDownloadList;
	}

	public ContentFetchList getContentFetchList() {
		return contentFetchList;
	}

	public NavDownloadList getNavDownloadList() {
		return navDownloadList;
	}

	public Title getTitle() {
		return title;
	}

}
