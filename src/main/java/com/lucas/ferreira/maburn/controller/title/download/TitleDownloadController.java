package com.lucas.ferreira.maburn.controller.title.download;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.TitleController;
import com.lucas.ferreira.maburn.controller.title.download.controllers.Controllers;
import com.lucas.ferreira.maburn.controller.title.download.controllers.FetchTypeSelect;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemCombox;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemValueTextField;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedAll;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedBetween;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedSingle;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsSelectedUpdate;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItem;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemAll;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemBetween;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemSingle;
import com.lucas.ferreira.maburn.controller.title.download.register.ChooseItemUpdate;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchInfo;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchTextDetails;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleFetcher;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;
import com.lucas.ferreira.maburn.controller.title.download.register.ScreenshotFullDetails;
import com.lucas.ferreira.maburn.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlert;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.ShadeLayer;
import com.lucas.ferreira.maburn.view.navigator.Navigator;
import com.microsoft.playwright.Playwright;

import javafx.application.Platform;
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

public class TitleDownloadController implements Initializable {
//
	@FXML
	private AnchorPane apShade;
	@FXML
	private AnchorPane apManualSearch;

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

//	private RegisterFetch registerFetch;
	private FetchInfo fetchInfo;
	private RegisterTitleFetcher titleDownloadFetcher;
	private TitleScraped titleScraped;
	private ManualSearchAlertController manualSearchAlertController;
	private FetchTypeSelect fetchTypeSelect;
	private FetchInSystem fetchInSystem = new FetchInSystem();
	private Category category;
	private List<String> taggedItems;
	private ItemsSelectedBetween itemsSelectedBetween;
	private ItemsSelectedAll itemsSelectedAll;
	private ItemsSelectedSingle<String> itemsSelectedSingle;
	private ItemsSelectedUpdate itemsSelectedUpdate;
	private CollectionTitle title;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initializeTitle();
			initializeButtons();
			initializeIcons();
			initializeSelectItemsValues();
			initializeState();
		} catch (IllegalAccessError e) {
			// TODO: handle exception
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
		title = controller.getTitle();
		titleDownloadFetcher = new RegisterTitleFetcher(title, txtFetchMsg);

	}

	private void initializeState() {

//		RegisterFetchSource fetchSource = new RegisterFetchSource(cbSource, imgFetch, imgRecover, imgManualSearch, txtFetchMsg);
//		RegisterFetchChoose fetchChoose = new RegisterFetchChoose(cbSelect, cbItems, btnDownload, txtFetchMsg);
//		registerFetch = new RegisterFetch(fetchSource, fetchChoose);

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
				titleScraped = titleDownloadFetcher.automaticFetch(cbSource.getValue(),
						new RegisterTitleSearcher(txtFetchMsg));
				organizeFetchResult();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	public void onClickRecover() {
		new Thread(() -> {
			try {
				titleScraped = titleDownloadFetcher.systemFetch(cbSource.getValue(), fetchInSystem);
				organizeFetchResult();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

	public void onClickManualSearch() {

		new Thread(() -> {
			try {
				titleScraped = titleDownloadFetcher.manualFetch(cbSource.getValue(), manualSearchAlertController);
				if (titleScraped != null)
					organizeFetchResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
		
		
		
		
		ItemCombox itemCombox = new ItemCombox();
		Map<String, String> namedItemsValues = itemCombox.itemsValuesMap(titleScraped.getItemsScraped(), taggedItems);
		List<String> valuesItems = taggedItems;
		
		 List<ChooseItem> chooseItems = Arrays.asList(
				 new ChooseItemSingle<String>(itemsSelectedSingle, namedItemsValues),
				 new ChooseItemBetween(itemsSelectedBetween, valuesItems, namedItemsValues),
				 new ChooseItemAll(itemsSelectedAll,valuesItems, namedItemsValues),
				 new ChooseItemUpdate(itemsSelectedUpdate,valuesItems, namedItemsValues));

		
		FetchItemType fetchItemType = fetchTypeSelect.getFetchItemType();
		
		ChooseItem chooseItem = chooseItems.stream()
				.filter(choose -> choose.getController().getFetchItemType() == fetchItemType)
				.findFirst()
				.get();

		List<String> choosedItems = chooseItem.getChoosedItems();
		System.out.println(choosedItems);
	}

	private void organizeFetchResult() {
		if (titleScraped != null) {
			ItemCombox itemCombox = new ItemCombox();
			String tag = category == Category.ANIME ? "Episode " : "Chapter ";
			taggedItems = itemCombox.valuesToNumberTagItems(titleScraped.getItemsScraped(), tag);
			Platform.runLater(() -> cbItems.getItems().setAll(taggedItems));
			initializeTextFields();
			itemsSelectedBetween.validates();

			loadFetchInfo();
			fetchInfo.showInfo();

			cbItems.setDisable(false);
			cbSelect.setDisable(false);

			fetchInSystem.save(title, cbSource.getValue(), titleScraped.getTitleUrl());

		}
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

	public void initializeTextFields() {


		ItemValueTextField itemValueTextFieldFirst = new ItemValueTextField(txtStartItemValue, taggedItems.size(),
				txtAreaFieldFirstMsg);
		ItemValueTextField itemValueTextFieldLast = new ItemValueTextField(txtEndItemValue, taggedItems.size(),
				txtAreaFieldLastMsg);
		
		
		
		
		itemsSelectedBetween = new ItemsSelectedBetween(itemValueTextFieldFirst, itemValueTextFieldLast,
				txtAreaChooseMsg);
		int totalItems = titleScraped.getItemsScraped().size();
		Message messageSelectedAll = new SucceedMessage(txtAreaUpdateItems);
		messageSelectedAll.setMessageText("All " + totalItems + " items selected!");
		Message messageSelectedUpdate = new SucceedMessage(txtAreaTotalItems);
		messageSelectedUpdate.setMessageText(totalItems + " new items available!");
		
		itemsSelectedAll = new ItemsSelectedAll(txtAreaTotalItems, messageSelectedAll);
		itemsSelectedSingle = new ItemsSelectedSingle<String>(cbItems);

		
		itemsSelectedUpdate = new ItemsSelectedUpdate(txtAreaUpdateItems, messageSelectedUpdate);
		List<Controllers> controllers = Arrays.asList(itemsSelectedBetween, itemsSelectedSingle, itemsSelectedAll,
				itemsSelectedUpdate);
		

		fetchTypeSelect = new FetchTypeSelect(controllers);

		cbSelect.valueProperty().addListener(fetchTypeSelect);

	}

}
