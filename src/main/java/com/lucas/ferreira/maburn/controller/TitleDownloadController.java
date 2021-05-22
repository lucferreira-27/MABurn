package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.fetch.FetchTitle;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.model.enums.MessageError;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;
import com.lucas.ferreira.maburn.model.messages.WarningMessage;
import com.lucas.ferreira.maburn.model.search.SearchScraped;
import com.lucas.ferreira.maburn.model.search.SearchScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.AnimeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.TitleScraped;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class TitleDownloadController implements Initializable {

	@FXML
	private ComboBox<String> cbSelect;

	@FXML
	private ComboBox<String> cbItems;

	@FXML
	private ComboBox<Sites> cbSource;

	@FXML
	private ImageView imgThumbnail;
	@FXML
	private ImageView imgSource;

	@FXML
	private ImageView imgFetch;
	@FXML
	private ImageView imgRecover;
	@FXML
	private ImageView imgChoose;

	@FXML
	private ImageView imgDownloadStart;

	@FXML
	private TextArea txtFetchMsg;

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
	private Label lblStart;

	@FXML
	private Label lblSource;

	@FXML
	private Label lblSiteTitle;

	@FXML
	private Label lblItems;

	private String ICON_PATH = "icons/";
	private FetchTitle fetch = new FetchTitle();
	private CollectionItem item;
	private Category category;
	private Sites sourceSelect;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Navigator navigator = new Navigator();
		try {
			initializeTitle();
			initializeImages();
			initializeLabels();
			initializeComboBoxs();
			initializeState();
		} catch (IllegalAccessError e) {
			// TODO: handle exception
			if (Navigator.getInterfacesList().size() > 1)
				navigator.back();
			else
				navigator.open(Interfaces.HOME);
		}
	}

	private void initializeTitle() {
		TitleInterfaceController controller = (TitleInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.TITLE);
		if (controller == null || controller.getTitle() == null) {
			throw new IllegalAccessError("Title can't be null");
		}
		item = controller.getTitle();
		category = Category.ANIME;

		System.out.println(item.getTitleDataBase());
	}

	private void initializeState() {

	}

	public void initializeImages() {

		Icon iconFetch = new Icon(imgFetch, new IconConfig(ICON_PATH, Icons.FETCH));
		iconFetch.setProperties((event) -> onClickFetch());
		Icon iconRecover = new Icon(imgRecover, new IconConfig(ICON_PATH, Icons.RECOVER));
		iconRecover.setProperties((event) -> onClickRecover());
		Icon iconDownloadStart = new Icon(imgDownloadStart, new IconConfig(ICON_PATH, Icons.DOWNLOAD_START));
		iconDownloadStart.setProperties((event) -> onClickDownloadStart());
		Icon iconSource = new Icon(imgSource, new IconConfig(ICON_PATH, Icons.SOURCE));
		iconSource.setProperties();
		Icon iconChoose = new Icon(imgChoose, new IconConfig(ICON_PATH, Icons.CHOOSE));
		iconChoose.setProperties();

	}

	public void onClickFetch() {

		Message errorMessage = new ErrorMessage(txtFetchMsg);
		Message warningMessage = new WarningMessage(txtFetchMsg);
		Message succededMessage = new SucceedMessage(txtFetchMsg);

		final String errorSourceMsg = "You need select a source before fetch.";
		final String errorScrapingMsg = "Sorry something went wrong. Try again or other source";
		final String errorSearchMsg = "No results found.";
		final String warningMsgSearch = "Searching ...";
		final String warningMsgFetch = "Fetching please wait ...";
		final String succededMsg = item.getTitleDataBase() + " fetched!";
		final String namedItem = category == Category.ANIME ? "Episode " : "Chapter ";

		new Thread(() -> {
			try {
				
				if (sourceSelect == null) {
					throw new NullPointerException();
				}
				if (errorMessage.isShowing()) {
					errorMessage.hideMessage();
				}

				warningMessage.showMessage(warningMsgSearch);

				if (cbItems.getItems().size() > 0) {
					cbItems.getItems().clear();
				}

				SearchScraping searchScraping = new SearchScraping(item.getTitleDataBase(), SearchEngine.GOOGLE,
						cbSource.getValue());
				SearchScraped searchScraped = searchScraping.search();
				if (searchScraped == null || searchScraped.getResults().size() == 0) {
					throw new IndexOutOfBoundsException();
				}

				String bestResult = searchScraped.getResults().get(0);

				warningMessage.showMessage(warningMsgFetch);
				TitleScraped titleScraped = fetch.fetch(new AnimeScraping(cbSource.getValue()), bestResult);
				if(titleScraped == null) {
					throw new IllegalArgumentException();
				}
				List<String> items = titleScraped.getItemsScraped();
				System.out.println(titleScraped.getPageInfo().getImage().getException());
				imgThumbnail.setImage(titleScraped.getPageInfo().getImage());
				imgThumbnail.setVisible(true);

				if (items == null) {
					throw new IllegalArgumentException();
				}

				List<String> namedItems = items.stream().mapToInt(item -> items.indexOf(item) + 1).boxed()
						.map(n -> namedItem + n).collect(Collectors.toList());

				Platform.runLater(() -> cbItems.getItems().setAll(namedItems));
				succededMessage.showMessage(succededMsg);

			} catch (NullPointerException e) {
				errorMessage.showMessage(errorSourceMsg);
			} catch (IllegalArgumentException e) {
				errorMessage.showMessage(errorScrapingMsg);

			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
				errorMessage.showMessage(errorSearchMsg);
			}
		}).start();

	}

	public void onClickRecover() {
		System.out.println("Recover");
	}

	public void onClickDownloadStart() {
		System.out.println("Download Start");
	}

	public void initializeLabels() {

	}

	public void initializeComboBoxs() {

		// TEMP
		List<Sites> sites = List.of(Sites.values()).stream().filter((f) -> f.getCategory() == category)
				.collect(Collectors.toList());
		cbSource.getItems().addAll(sites);
		cbSource.valueProperty().addListener((obs, oldValue, newValue) -> {
			System.out.println(newValue);
			sourceSelect = newValue;
		});

		cbSelect.getItems().addAll(List.of("Select One", "Select All", "Update", "Between"));
	}

}
