package com.lucas.ferreira.maburn.controller.title.download;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.TitleController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
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
import com.lucas.ferreira.maburn.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.fetch.item.FetchItem;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlert;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;
import com.lucas.ferreira.maburn.model.download.DownloadFilename;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.browser.MyBrowser;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ItemScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListChapterScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListEpisodeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ListItemScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.ScrapingWork;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.ShadeLayer;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TitleDownloadController {

	private Navigator navigator = new Navigator();
	private FetchInfo fetchInfo;
	private TitleScraped titleScraped;
	private FetchTypeSelect fetchTypeSelect;
	private TaggedItems taggedItems;
	private OrganizeFetchResult organizeFetchResult;
	private ItemsSelectedBetween itemsSelectedBetween;
	private ItemsSelectedAll itemsSelectedAll;
	private ItemsSelectedSingle<String> itemsSelectedSingle;
	private ItemsSelectedUpdate itemsSelectedUpdate;
	private TitleDownload titleDownload;
	private TitleDownloadInitialize titleDownloadInitialize;
	private CollectionTitle collectionTitle;

	public TitleDownloadController(TitleDownload titleDownload) {

		this.titleDownload = titleDownload;
		titleDownloadInitialize = new TitleDownloadInitialize(titleDownload, this);
		titleDownloadInitialize.initialize();
		organizeFetchResult = new OrganizeFetchResult(titleDownload.getCbItems(), titleDownload.getCbSource(),
				titleDownloadInitialize.getTitle().getCollectionTitle());
		this.collectionTitle = titleDownloadInitialize.getTitle().getCollectionTitle();
	}

	public void onClickFetch() {

		new Thread(() -> {
			try {
				FetchActionAutomatic fetchActionAutomatic = new FetchActionAutomatic(
						new RegisterTitleSearcher(titleDownload.getTxtFetchMsg()));
				RegisterTitleFetcher registerTitleFetcher = new RegisterTitleFetcher(titleDownload.getTxtFetchMsg());
				titleScraped = registerTitleFetcher.fetch(fetchActionAutomatic,
						new FetchableTittle(collectionTitle, titleDownload.getCbSource().getValue()));

				taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, collectionTitle.getCategory());

				initializeFetchResults();

			} catch (Exception e) {
				// TODO: handle exception
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

				taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, collectionTitle.getCategory());

				initializeFetchResults();

			} catch (Exception e) {
				// TODO: handle exception
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

				taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, collectionTitle.getCategory());

				initializeFetchResults();

			} catch (Exception e) {
				// TODO: handle exception
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

		Map<String, String> namedItemsValues = taggedItems.getNamedItemsValues();

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

			titleDownloadInitialize.getTitleDownloadListCard().getListCards().onAddScrapingDone(taggedItems,
					scrapingWorks);
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
