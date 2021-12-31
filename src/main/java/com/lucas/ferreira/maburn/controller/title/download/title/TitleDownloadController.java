package com.lucas.ferreira.maburn.controller.title.download.title;

import com.lucas.ferreira.maburn.controller.title.download.FetchAction;
import com.lucas.ferreira.maburn.controller.title.download.FetchActionAutomatic;
import com.lucas.ferreira.maburn.controller.title.download.FetchActionManual;
import com.lucas.ferreira.maburn.controller.title.download.FetchActionRecover;
import com.lucas.ferreira.maburn.controller.title.download.controllers.*;
import com.lucas.ferreira.maburn.controller.title.download.installer.BrowserInstallerController;
import com.lucas.ferreira.maburn.controller.title.download.register.*;
import com.lucas.ferreira.maburn.exceptions.BrowserInstallerException;
import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.browser.BrowserFilesLocal;
import com.lucas.ferreira.maburn.model.browser.BrowserInstallerLaunch;
import com.lucas.ferreira.maburn.model.browser.Binaries;
import com.lucas.ferreira.maburn.model.browser.CheckBrowserFiles;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.fetch.item.FetchItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.states.ControllerStateAdapter;
import com.lucas.ferreira.maburn.model.webscraping.navigate.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListEpisodeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.view.ShadeLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TitleDownloadController implements ControllerStateAdapter {
	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private FetchInfo fetchInfo;
	private TitleScraped titleScraped;
	private FetchTypeSelect fetchTypeSelect;
	private OrganizeFetchResult organizeFetchResult;
	private ItemsSelectedBetween itemsSelectedBetween;
	private ItemsSelectedAll itemsSelectedAll;
	private ItemsSelectedSingle<String> itemsSelectedSingle;
	private ItemsSelectedUpdate itemsSelectedUpdate;
	private TitleDownloadModel titleDownload;
	private TitleDownloadInitialize titleDownloadInitialize;
	private CollectionTitle collectionTitle;
	private Title title;

	private boolean blockFetch = false;

	public TitleDownloadController(TitleDownloadModel titleDownload) {

		this.titleDownload = titleDownload;

	}

	@Override
	public void initialize() {
		titleDownloadInitialize = new TitleDownloadInitialize(titleDownload, this);
		titleDownloadInitialize.initialize();
		organizeFetchResult = new OrganizeFetchResult(titleDownload.getCbItems(), titleDownload.getCbSource(),
				titleDownloadInitialize.getTitle().getCollectionTitle());
		collectionTitle = titleDownloadInitialize.getTitle().getCollectionTitle();
		title = titleDownloadInitialize.getTitle();

	}

	private void fetch(Runnable fetch) {

		LOGGER.config("Click on Fetch");

		new Thread(() -> {
			try {
				if (isFetchPossible()) {
					if (!blockFetch) {
						blockFetch = true;
						fetch.run();
					}
					return;
				} else {
					browserLaunch();
				}

				if (isFetchPossible()) {
					if (!blockFetch) {
						blockFetch = true;
						fetch.run();
					}
				} else {
					throw new Exception("BROWSER FILES ERROR");

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private boolean isFetchPossible() {
		BrowserFilesLocal browserFilesLocal = new BrowserFilesLocal();
		String local = browserFilesLocal.getLocal(new UserSystem().getUserPlataform());
		CheckBrowserFiles checkBrowserFiles = new CheckBrowserFiles();
		boolean foundFirefox = checkBrowserFiles.hasBrowserFilesAvailable(local, Binaries.FIREFOX);
		boolean foundFfmpeg = checkBrowserFiles.hasBrowserFilesAvailable(local, Binaries.FFMPEG);
		return foundFirefox && foundFfmpeg;
	}

	private void browserLaunch() {
		BrowserInstallerLaunch browserInstallerLaunch = new BrowserInstallerLaunch();
		try {
			BrowserInstallerController browserInstallerController = browserInstallerLaunch
					.openBrowserInstaller(titleDownload.getSpMainPane());

			try {
				browserInstallerController.install(Binaries.FIREFOX, Binaries.FFMPEG, Binaries.FFMPEG_COMPLETE);
			} catch (BrowserInstallerException e) {
				e.printStackTrace();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void fetchNormal() {
		LOGGER.config("Initializing FetchNormal");
		registerFetch(new FetchActionAutomatic(new RegisterTitleSearcher(titleDownload.getTxtFetchMsg())));
	}

	private void fetchRecover() {
		LOGGER.config("Initializing FetchRecover");
		registerFetch(new FetchActionRecover());
	}

	private void fetchManual() {
		LOGGER.config("Initializing FetchManual");
		registerFetch(new FetchActionManual(titleDownloadInitialize.getManualSearchAlertController()));
	}

	private void registerFetch(FetchAction fetchAction) {
		new Thread(() -> {
			try {
				RegisterTitleFetcher registerTitleFetcher = new RegisterTitleFetcher(titleDownload.getTxtFetchMsg());
				titleScraped = registerTitleFetcher.fetch(fetchAction,
						new FetchableTittle(collectionTitle, titleDownload.getCbSource().getValue()));
				if (titleScraped != null) {
					TaggedItems taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped,
							collectionTitle.getCategory());
					title.setTaggedItems(taggedItems);
					initializeFetchResults();
				}
			} catch (Exception e) {
				blockFetch = false;
				e.printStackTrace();
			}
			blockFetch = false;
		}).start();
	}

	private void initializeFetchResults() {
		initializeControllers();
		itemsSelectedBetween.validates();
		titleDownload.getCbSelect().setDisable(false);
		loadFetchInfo();
		fetchInfo.showInfo();
	}

	public void onClickFetch() {
		fetch(this::fetchNormal);
	}

	public void onClickRecover() {
		fetch(this::fetchRecover);
	}

	public void onClickManualSearch() {

		fetch(this::fetchManual);

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

		List<ScrapingWork> scrapingWorks = new ArrayList<ScrapingWork>();
		
		new Thread(() -> {
			choosedItems.forEach((name,url)->{
				SiteValues siteValues = new SiteValues();
				siteValues.setRegisteredSite(titleDownload.getCbSource().getValue());
				siteValues.setTarget(name);
				siteValues.setUrl(url);
				scrapingWorks.add(new ScrapingWork(siteValues));
			});
			

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
		FetchTextDetails fetchTextDetails = new FetchTextDetails(titleDownload);
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
	
	public TitleDownloadInitialize getTitleDownloadInitialize() {
		return titleDownloadInitialize;
	}
	public Title getTitle() {
		return title;
	}

}
