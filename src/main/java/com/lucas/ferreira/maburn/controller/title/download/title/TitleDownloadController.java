package com.lucas.ferreira.maburn.controller.title.download.title;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.download.FetchActionAutomatic;
import com.lucas.ferreira.maburn.controller.title.download.FetchActionManual;
import com.lucas.ferreira.maburn.controller.title.download.FetchActionRecover;
import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.FetchTypeSelect;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemValueTextField;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedAll;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedBetween;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedSingle;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedUpdate;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemAll;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemBetween;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemSingle;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemUpdate;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchInfo;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchItemsLinks;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchTextDetails;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.controller.title.download.register.OrganizeFetchResult;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleFetcher;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;
import com.lucas.ferreira.maburn.controller.title.download.register.ScreenshotFullDetails;
import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.fetch.item.FetchItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListEpisodeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.view.ShadeLayer;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

public class TitleDownloadController {

	private Navigator navigator = new Navigator();
	private FetchInfo fetchInfo;
	private TitleScraped titleScraped;
	private FetchTypeSelect fetchTypeSelect;
	private OrganizeFetchResult organizeFetchResult;
	private ItemsSelectedBetween itemsSelectedBetween;
	private ItemsSelectedAll itemsSelectedAll;
	private ItemsSelectedSingle<String> itemsSelectedSingle;
	private ItemsSelectedUpdate itemsSelectedUpdate;
	private TitleDownload titleDownload;
	private TitleDownloadInitialize titleDownloadInitialize;
	private CollectionTitle collectionTitle;
	private Title title;
	
	public TitleDownloadController(TitleDownload titleDownload) {

		this.titleDownload = titleDownload;
		titleDownloadInitialize = new TitleDownloadInitialize(titleDownload, this);
		titleDownloadInitialize.initialize();
		organizeFetchResult = new OrganizeFetchResult(titleDownload.getCbItems(), titleDownload.getCbSource(),
				titleDownloadInitialize.getTitle().getCollectionTitle());
		this.collectionTitle = titleDownloadInitialize.getTitle().getCollectionTitle();
		this.title = titleDownloadInitialize.getTitle();
	}

	public void onClickFetch() {

		new Thread(() -> {
			try {
				FetchActionAutomatic fetchActionAutomatic = new FetchActionAutomatic(
						new RegisterTitleSearcher(titleDownload.getTxtFetchMsg()));
				RegisterTitleFetcher registerTitleFetcher = new RegisterTitleFetcher(titleDownload.getTxtFetchMsg());
				titleScraped = registerTitleFetcher.fetch(fetchActionAutomatic,
						new FetchableTittle(collectionTitle, titleDownload.getCbSource().getValue()));

				TaggedItems taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, collectionTitle.getCategory());
				title.setTaggedItems(taggedItems);
				initializeFetchResults();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}).start();

	}

	public void onClickRecover() {
		new Thread(() -> {
			try {
				FetchActionRecover fetchActionRecover = new FetchActionRecover();
				RegisterTitleFetcher registerTitleFetcher = new RegisterTitleFetcher(titleDownload.getTxtFetchMsg());
				titleScraped = registerTitleFetcher.fetch(fetchActionRecover,
						new FetchableTittle(collectionTitle, titleDownload.getCbSource().getValue()));

				TaggedItems taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, collectionTitle.getCategory());
				title.setTaggedItems(taggedItems);

				initializeFetchResults();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}).start();

	}

	public void onClickManualSearch() {

		new Thread(() -> {
			try {
				FetchActionManual fetchActionManual = new FetchActionManual(
						titleDownloadInitialize.getManualSearchAlertController());
				RegisterTitleFetcher registerTitleFetcher = new RegisterTitleFetcher(titleDownload.getTxtFetchMsg());
				titleScraped = registerTitleFetcher.fetch(fetchActionManual,
						new FetchableTittle(collectionTitle, titleDownload.getCbSource().getValue()));

				TaggedItems taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, collectionTitle.getCategory());
				title.setTaggedItems(taggedItems);

				initializeFetchResults();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}).start();

	}

	public void onClickZoomIn() {
		fetchInfo.zoomIn();
	}

	public void onClickZoomOut() {
		fetchInfo.zoomOut();
	}

	public void onClickDownloadStart() {

		Map<String, String> namedItemsValues = title.getTaggedItems().getNamedItemsValues();

		FetchItemsLinks directLinks = new FetchItemsLinks(new ChooseItemSingle(itemsSelectedSingle, namedItemsValues),
				new ChooseItemBetween(itemsSelectedBetween, namedItemsValues),
				new ChooseItemAll(itemsSelectedAll, namedItemsValues),
				new ChooseItemUpdate(itemsSelectedUpdate, namedItemsValues));

		Map<String, String> choosedItems = directLinks.selectedLinks(fetchTypeSelect);

		FetchItem fetchItem = new FetchItem();

		new Thread(() -> {
			List<ScrapingWork> scrapingWorks = new ArrayList<String>(choosedItems.values()).stream()
					.map(url -> new ScrapingWork(url)).collect(Collectors.toList());

			ListItemScraping listItemScraping = collectionTitle.getCategory() != Category.ANIME
					? new ListChapterScraping(titleDownload.getCbSource().getValue(), new MyBrowser(true))
					: new ListEpisodeScraping(titleDownload.getCbSource().getValue(), new MyBrowser(true));

			titleDownloadInitialize.getTitleDownloadListCard().getDownloadList().onScrapingWork(scrapingWorks);
			fetchItem.fetch(listItemScraping, scrapingWorks);

		}).start();

	}

	private void loadFetchInfo() {
		ShadeLayer shadeLayer = new ShadeLayer(titleDownload.getApShade());
		ScreenshotFullDetails screenshotFullDetails = new ScreenshotFullDetails(titleDownload.getBpThumbnailFull(),
				shadeLayer, titleDownload.getImgThumbnailFullSize(), titleDownload.getImgZoomOut());
		FetchTextDetails fetchTextDetails = new FetchTextDetails(titleDownload.getLblSiteTitle(),
				titleDownload.getLblSource(), titleDownload.getLblUrl(), titleDownload.getLblItemsTotal());
		fetchInfo = new FetchInfo(titleScraped.getPageInfo(), titleDownload.getImgThumbnail(), screenshotFullDetails,
				fetchTextDetails);
	}

	public void initializeControllers() {
		TaggedItems taggedItems = title.getTaggedItems();
		
		ItemValueTextField itemValueTextFieldFirst = new ItemValueTextField(titleDownload.getTxtStartItemValue(),
				taggedItems.getValuesItems().size(), titleDownload.getTxtAreaFieldFirstMsg());
		ItemValueTextField itemValueTextFieldLast = new ItemValueTextField(titleDownload.getTxtEndItemValue(),
				taggedItems.getValuesItems().size(), titleDownload.getTxtAreaFieldLastMsg());

		int totalItems = titleScraped.getItemsScraped().size();

		itemsSelectedBetween = new ItemsSelectedBetween(itemValueTextFieldFirst, itemValueTextFieldLast,
				titleDownload.getTxtAreaChooseMsg());
		itemsSelectedAll = new ItemsSelectedAll(titleDownload.getTxtAreaTotalItems(), totalItems);
		itemsSelectedSingle = new ItemsSelectedSingle<String>(titleDownload.getCbItems());
		itemsSelectedUpdate = new ItemsSelectedUpdate(titleDownload.getTxtAreaUpdateItems(), totalItems);

		List<Controllers> controllers = Arrays.asList(itemsSelectedBetween, itemsSelectedSingle, itemsSelectedAll,
				itemsSelectedUpdate);

		fetchTypeSelect = new FetchTypeSelect(controllers);

		titleDownload.getCbSelect().valueProperty().addListener(fetchTypeSelect);

	}

	private void initializeFetchResults() {
		initializeControllers();
		itemsSelectedBetween.validates();
		titleDownload.getCbSelect().setDisable(false);
		loadFetchInfo();
		fetchInfo.showInfo();
	}

}