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

public class TitleDownloadController implements Initializable {
//
	@FXML
	private AnchorPane apShade;
	@FXML
	private AnchorPane apManualSearch;

	@FXML
	private VBox vBoxListDownloads;

	@FXML
	private HBox hboxItemsFields;
	@FXML
	private TextField txtStartItemValue;

	@FXML
	private TextField txtEndItemValue;

	@FXML
	private BorderPane bpThumbnailFull;

	@FXML
	private Button btnDownload;

	@FXML
	private ImageView imgThumbnailFullSize;
	@FXML
	private ImageView imgThumbnail;
	@FXML
	private ImageView imgSource;
	@FXML
	private ImageView imgZoomIn;

	@FXML
	private ImageView imgZoomOut;

	@FXML
	private ImageView imgFetch;
	@FXML
	private ImageView imgRecover;

	@FXML
	private ImageView imgManualSearch;

	@FXML
	private ImageView imgChoose;

	@FXML
	private ImageView imgDownloadStart;

	@FXML
	private ComboBox<FetchItemType> cbSelect;

	@FXML
	private ComboBox<String> cbItems;

	@FXML
	private ComboBox<Sites> cbSource;

	@FXML
	private TextArea txtFetchMsg;
	@FXML
	private TextArea txtAreaChooseMsg;

	@FXML
	private TextArea txtAreaFieldFirstMsg;
	@FXML
	private TextArea txtAreaFieldLastMsg;
	@FXML
	private TextArea txtAreaTotalItems;
	@FXML
	private TextArea txtAreaUpdateItems;
	@FXML
	private Label lblTotal;

	@FXML
	private Label lblComplete;

	@FXML
	private Label lblFailed;

	@FXML
	private Label lblTitle;
	@FXML
	private Label lblMainTitle;

	@FXML
	private Label lblFetch;

	@FXML
	private Label lblRecover;

	@FXML
	private Label lblManualSearch;

	@FXML
	private Label lblStart;

	@FXML
	private Label lblUrl;

	@FXML
	private Label lblSource;

	@FXML
	private Label lblItemsTotal;

	@FXML
	private Label lblSiteTitle;

	@FXML
	private Label lblItems;
	private Navigator navigator = new Navigator();

	private String ICON_PATH = "icons/";
	private CollectionTitle collectionTitle;

	private FetchInfo fetchInfo;
	private TitleScraped titleScraped;
	private ManualSearchAlertController manualSearchAlertController;
	private FetchTypeSelect fetchTypeSelect;
	private Category category;
	private TaggedItems taggedItems;
	private OrganizeFetchResult organizeFetchResult;
	private ItemsSelectedBetween itemsSelectedBetween;
	private ItemsSelectedAll itemsSelectedAll;
	private ItemsSelectedSingle<String> itemsSelectedSingle;
	private ItemsSelectedUpdate itemsSelectedUpdate;
	private ListCards listCards;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initializeTitle();
			initializeButtons();
			initializeIcons();
			initializeSelectItemsValues();
			initializeState();
		} catch (IllegalAccessError e) {
			if (Navigator.getInterfacesList().size() > 1)
				navigator.back();
			else
				navigator.open(Interfaces.HOME);
		}
	}

	private void initializeButtons() {
		btnDownload.setOnAction(event -> onClickDownloadStart());
	}

	private void initializeTitle() {
		TitleController controller = (TitleController) Navigator.getMapNavigator().get(Interfaces.TITLE);
		if (controller == null || controller.getTitle() == null) {
			throw new IllegalAccessError("Title can't be null");
		}
		category = controller.getTitle().getCategory();
		collectionTitle = controller.getTitle();
		lblMainTitle.setText(collectionTitle.getTitleDataBase());
		organizeFetchResult = new OrganizeFetchResult(cbItems, cbSource, collectionTitle);

	}

	private void initializeState() {
		listCards = new ListCards(collectionTitle, vBoxListDownloads);

		manualSearchAlertController = new ManualSearchAlertController(new ManualSearchAlert(apManualSearch));

	}

	public void initializeIcons() {

		Icon iconFetch = new Icon(imgFetch, new IconConfig(ICON_PATH, Icons.FETCH));
		iconFetch.setProperties((event) -> onClickFetch());
		Icon iconRecover = new Icon(imgRecover, new IconConfig(ICON_PATH, Icons.RECOVER));
		iconRecover.setProperties((event) -> onClickRecover());
		Icon iconManualSearch = new Icon(imgManualSearch, new IconConfig(ICON_PATH, Icons.MANUAL_SEARCH));
		iconManualSearch.setProperties((event) -> onClickManualSearch());
		Icon iconDownloadStart = new Icon(imgDownloadStart, new IconConfig(ICON_PATH, Icons.DOWNLOAD_START));
		iconDownloadStart.setProperties((event) -> onClickDownloadStart());
		Icon iconSource = new Icon(imgSource, new IconConfig(ICON_PATH, Icons.SOURCE));
		iconSource.setProperties();
		Icon iconChoose = new Icon(imgChoose, new IconConfig(ICON_PATH, Icons.CHOOSE));
		iconChoose.setProperties();
		Icon iconZoomIn = new Icon(imgZoomIn, new IconConfig(ICON_PATH, Icons.ZOOM_IN_BIG));
		iconZoomIn.setProperties((event) -> onClickZoomIn());
		Icon iconZoomOut = new Icon(imgZoomOut, new IconConfig(ICON_PATH, Icons.ZOOM_OUT_BIG));
		iconZoomOut.setProperties((event) -> onClickZoomOut());

	}

	public void onClickFetch() {

		new Thread(() -> {
			try {
				FetchActionAutomatic fetchActionAutomatic = new FetchActionAutomatic(
						new FetchableTittle(collectionTitle, cbSource.getValue()),
						new RegisterTitleFetcher(txtFetchMsg), new RegisterTitleSearcher(txtFetchMsg));
				titleScraped = fetchActionAutomatic.automaticFetch();

				taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, category);

				initializeFetchResults();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}).start();

	}

	private void initializeFetchResults() {
		initializeControllers();
		itemsSelectedBetween.validates();
		cbSelect.setDisable(false);
		loadFetchInfo();
		fetchInfo.showInfo();
	}

	public void onClickRecover() {
		new Thread(() -> {
			try {
				FetchActionRecover fetchActionRecover = new FetchActionRecover(
						new FetchableTittle(collectionTitle, cbSource.getValue()),
						new RegisterTitleFetcher(txtFetchMsg), new FetchInSystem());
				titleScraped = fetchActionRecover.recoverFetch();

				taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, category);

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
						new FetchableTittle(collectionTitle, cbSource.getValue()),
						new RegisterTitleFetcher(txtFetchMsg), manualSearchAlertController);
				titleScraped = fetchActionManual.manualFetch();

				taggedItems = organizeFetchResult.organizeAndSaveFetch(titleScraped, category);

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
			List<ScrapingWork> scrapingWorks = new ArrayList<String>(choosedItems.values())
					.stream()
					.map(url -> new ScrapingWork(url))
					.collect(Collectors.toList());

			ListItemScraping listItemScraping = category != Category.ANIME
					? new ListChapterScraping(cbSource.getValue(), new MyBrowser(true))
					: new ListEpisodeScraping(cbSource.getValue(), new MyBrowser(true));
							
			listCards.onAddScrapingDone(taggedItems, scrapingWorks);
			fetchItem.fetch(listItemScraping, scrapingWorks);

		}).start();

	}

	private void loadFetchInfo() {
		ShadeLayer shadeLayer = new ShadeLayer(apShade);
		ScreenshotFullDetails screenshotFullDetails = new ScreenshotFullDetails(bpThumbnailFull, shadeLayer,
				imgThumbnailFullSize, imgZoomOut);
		FetchTextDetails fetchTextDetails = new FetchTextDetails(lblSiteTitle, lblSource, lblUrl, lblItemsTotal);
		fetchInfo = new FetchInfo(titleScraped.getPageInfo(), imgThumbnail, screenshotFullDetails, fetchTextDetails);
	}

	public void initializeSelectItemsValues() {

		List<Sites> sites = List.of(Sites.values()).stream().filter((f) -> f.getCategory() == category)
				.collect(Collectors.toList());
		cbSource.getItems().addAll(sites);

		cbSelect.getItems().addAll(Arrays.asList((FetchItemType.values())));
	}

	public void initializeControllers() {

		ItemValueTextField itemValueTextFieldFirst = new ItemValueTextField(txtStartItemValue,
				taggedItems.getValuesItems().size(), txtAreaFieldFirstMsg);
		ItemValueTextField itemValueTextFieldLast = new ItemValueTextField(txtEndItemValue,
				taggedItems.getValuesItems().size(), txtAreaFieldLastMsg);

		int totalItems = titleScraped.getItemsScraped().size();

		itemsSelectedBetween = new ItemsSelectedBetween(itemValueTextFieldFirst, itemValueTextFieldLast,
				txtAreaChooseMsg);
		itemsSelectedAll = new ItemsSelectedAll(txtAreaTotalItems, totalItems);
		itemsSelectedSingle = new ItemsSelectedSingle<String>(cbItems);
		itemsSelectedUpdate = new ItemsSelectedUpdate(txtAreaUpdateItems, totalItems);

		List<Controllers> controllers = Arrays.asList(itemsSelectedBetween, itemsSelectedSingle, itemsSelectedAll,
				itemsSelectedUpdate);

		fetchTypeSelect = new FetchTypeSelect(controllers);

		cbSelect.valueProperty().addListener(fetchTypeSelect);

	}

}
