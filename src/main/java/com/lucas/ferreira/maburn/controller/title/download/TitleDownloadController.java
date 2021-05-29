package com.lucas.ferreira.maburn.controller.title.download;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.title.TitleController;
import com.lucas.ferreira.maburn.controller.title.download.controllers.FetchTypeSelect;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemCombox;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemValueTextField;
import com.lucas.ferreira.maburn.controller.title.download.controllers.ItemsTextField;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchInfo;
import com.lucas.ferreira.maburn.controller.title.download.register.FetchTextDetails;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleFetcher;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;
import com.lucas.ferreira.maburn.controller.title.download.register.ScreenshotFullDetails;
import com.lucas.ferreira.maburn.controller.title.download.register.SiteScreenshot;
import com.lucas.ferreira.maburn.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlert;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.ShadeLayer;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

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
	private SiteScreenshot siteScreenshot;
	private ManualSearchAlertController manualSearchAlertController;
	private FetchInSystem fetchInSystem = new FetchInSystem();
	private Category category;
	private List<String> taggedItems;
	private ItemsTextField<String> itemsTextField;
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
		TitleController controller = (TitleController) Navigator.getMapNavigator()
				.get(Interfaces.TITLE);
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
				titleScraped = titleDownloadFetcher.automaticFetch(cbSource.getValue(), new RegisterTitleSearcher(txtFetchMsg));
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
		Map<String, String> namedItemsValues = itemCombox.itemsValuesMap(titleScraped, taggedItems);
		System.out.println(namedItemsValues.get(cbItems.getValue()));

	}

	private void organizeFetchResult() {
		if (titleScraped != null) {
			ItemCombox itemCombox = new ItemCombox();
			String tag = category == Category.ANIME ? "Episode " : "Chapter ";
			taggedItems = itemCombox.valuesToNumberTagItems(titleScraped, tag);
			itemCombox.itemsValuesMap(titleScraped, taggedItems);
			Platform.runLater(() -> cbItems.getItems().setAll(taggedItems));
			initializeTextFields();
			itemsTextField.validates();
			
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
		
		ItemValueTextField itemValueTextFieldFirst = new ItemValueTextField(txtStartItemValue, taggedItems.size(), txtAreaFieldFirstMsg);
		ItemValueTextField itemValueTextFieldLast = new ItemValueTextField(txtEndItemValue, taggedItems.size(), txtAreaFieldLastMsg);

		itemsTextField = new ItemsTextField<String>(itemValueTextFieldFirst, itemValueTextFieldLast, txtAreaChooseMsg);
		cbSelect.valueProperty().addListener(new FetchTypeSelect(cbItems, itemsTextField));

	}

}
