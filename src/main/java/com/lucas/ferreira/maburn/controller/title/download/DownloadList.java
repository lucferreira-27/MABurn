package com.lucas.ferreira.maburn.controller.title.download;

import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardState;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapeState;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;

public class DownloadList {
	
	private NavDownloadList navDownloadList = new NavDownloadList(this);
	private ContentDownloadList contentDownloadList = new ContentDownloadList();
	private ContentFetchList contentFetchList = new ContentFetchList();
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
		FetchCardController fetchCardController = listAddCard.createFetchCardControler(fetchCardLoaded);
		contentFetchList.addCard(fetchCardController, fetchCardLoaded.getFetchCard());
		fetchCardController.show();
		
		scrapingWork.getPropertyScrapeState().addListener((obs, oldvalue, newvalue) -> {
			FetchCardValues fetchCardValues = fetchCardLoaded.getFetchCardValues();
			System.out.println(newvalue);
			if (newvalue == ScrapeState.WORKING) {
				fetchCardValues.getFetchCardState().set(FetchCardState.WORKING);
			}

			else if (newvalue == ScrapeState.SUCCEED) {
				ItemScraped itemScraped;
				try {
					itemScraped = scrapingWork.getWorkResult();
					System.out.println(itemScraped.toString());
					DownloadCardLoaded downloadCardLoaded = cardLoader.load(itemScraped);

					fetchCardValues.getFetchCardState().set(FetchCardState.READY);
					DownloadCardController downloadListController = listAddCard.createDownloadCardController(downloadCardLoaded);
					contentDownloadList.addCard(downloadListController, downloadCardLoaded.getDownloadCard());
					//addDownloadCard(itemScraped, title.getTaggedItems());
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
