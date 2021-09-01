package com.lucas.ferreira.maburn.controller.title.download;

import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardState;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DownloadList {

	private NavDownloadList navDownloadList = new NavDownloadList(this);
	private ContentDownloadList contentDownloadList;
	private ContentFetchList contentFetchList;
	private Title title;

	public DownloadList(Title title) {
		this.title = title;
		contentDownloadList = new ContentDownloadList(navDownloadList, title.getTitleDownload().getvBoxListDownloads());
		contentDownloadList.setOnRemovedCard(() -> {
			updateDownloadListTotal();
			contentDownloadList.updateCardsValues();
			contentFetchList.updateCardsValues();
			return null;
		});
		contentFetchList = new ContentFetchList(navDownloadList, title.getTitleDownload().getvBoxListDownloads());
	}

	public void onScrapingWork(List<ScrapingWork> scrapingWorks) {

		for (ScrapingWork scrapingWork : scrapingWorks) {
			addListener(scrapingWork);
		}

	}

	private void addListener(ScrapingWork scrapingWork) {

		CardLoader cardLoader = new CardLoader(title, this);
		FetchCardFull fetchCardFull = cardLoader.loadFetchCard(scrapingWork);

		contentFetchList.addCard(fetchCardFull);
		updateNavDownloadListCardTotal(
				contentDownloadList.getDownloadCardFulls().size() + contentFetchList.getFetchCardsCardFulls().size());

		scrapingWork.getPropertyScrapeState().addListener((obs, oldvalue, newvalue) -> {

			contentFetchList.updateCardsValues();
			FetchCardValues fetchCardValues = fetchCardFull.getCardValues();

			if (newvalue == ScrapeState.WORKING) {
				fetchCardValues.getFetchCardState().set(FetchCardState.WORKING);
			}

			else if (newvalue == ScrapeState.SUCCEED) {
				ItemScraped itemScraped;
				try {
					itemScraped = scrapingWork.getWorkResult();
					fetchCardValues.getFetchCardState().set(FetchCardState.READY);
					DownloadCardFull downloadCardFull = cardLoader.load(itemScraped);
					contentDownloadList.addCard(downloadCardFull);
					contentFetchList.removeCard(fetchCardFull);
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

	private void updateDownloadListTotal() {
		updateNavDownloadListCardTotal(
				contentDownloadList.getDownloadCardFulls().size() + contentFetchList.getFetchCardsCardFulls().size());
	}

	private void updateNavDownloadListCardTotal(int total) {
		navDownloadList.getTotalCards().set(total);
	}

	public void pause() {
		contentDownloadList.getDownloadCardFulls().forEach(full ->{
			full.getCardController().pause();
		});
	}

	public void resume() {
		contentDownloadList.getDownloadCardFulls().forEach(full ->{
			full.getCardController().resume();
		});
	}

	public void stop() {
		contentDownloadList.getDownloadCardFulls().forEach(full ->{
			full.getCardController().stop();
		});
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
