
package com.lucas.ferreira.maburn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.fetch.FetcherOrchestrator;
import com.lucas.ferreira.maburn.model.TableConfig;
import com.lucas.ferreira.maburn.model.dao.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.download.DownloadOrchestrator;
import com.lucas.ferreira.maburn.model.download.queue.DownloadQueue;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.model.search.SearchController;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CollectionLoaderUtil;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TitleDownloadInterfaceController implements Initializable {
	private CollectionItem collectionItemTitle;
	private TitleWebData webDataTitle;
	private TitleDownload titleDownload;
	private WebScraping scraping;
	private FetcherOrchestrator fetcherController;
	private SearchController searchController;
	private Navigator navigator = new Navigator();
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
	private TableConfig tableConfig;

	@FXML
	private Button btnFetch;

	@FXML
	private Button btnPause;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnDownload;

	@FXML
	private Button btnReplaceLink;

	@FXML
	private Button btnNewFetch;

	@FXML
	private ComboBox<String> cbSource;

	@FXML
	private ComboBox<String> cbSelect;

	@FXML
	private ComboBox<String> cbItens;

	@FXML
	private Label lblSource;

	@FXML
	private Label lblPath;

	@FXML
	private Label lblItemsTotal;

	@FXML
	private Label lblItemsDownloaded;

	@FXML
	private Label lblItemsFailed;

	@FXML
	private Label lblTimeRemain;

	@FXML
	private Label lblTecTitle;

	@FXML
	private Label lblSearchResult;

	@FXML
	private Label lblTotalProgress;

	@FXML
	private Label lblLoadDownload;

	@FXML
	private Label lblLoadFetch;

	@FXML
	private ImageView imageViewTitle;

	@FXML
	private ProgressBar pbTotalProgress;

	@FXML
	private ProgressIndicator piLoadDownload;

	@FXML
	private ProgressIndicator piLoadFetch;

	@FXML
	private TableView<Downloader<CollectionSubItem>> tableItens;

	@FXML
	private TableColumn<Downloader<CollectionSubItem>, String> clName;

	@FXML
	private TableColumn<Downloader<CollectionSubItem>, Double> clSize;

	@FXML
	private TableColumn<Downloader<CollectionSubItem>, Double> clSpeed;
	@FXML
	private TableColumn<Downloader<CollectionSubItem>, Double> clCompleted;
	@FXML
	private TableColumn<Downloader<CollectionSubItem>, Double> clProgress;

	@FXML
	private TableColumn<Downloader<CollectionSubItem>, DownloadState> clState;

	@FXML
	private TableColumn<Downloader<CollectionSubItem>, String> clActionPause;
	@FXML
	private TableColumn<Downloader<CollectionSubItem>, String> clActionCancel;

	public TitleDownloadInterfaceController() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tableConfig = new TableConfig(tableItens, clName, clSize, clSpeed, clCompleted, clProgress, clState,
				clActionPause, clActionCancel);

		TitleInterfaceController titleController = (TitleInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.TITLE);

		collectionItemTitle = titleController.getTitle();
		titleDownload = DownloadQueue.getInstance().getDownload(collectionItemTitle.getId());
		if (titleDownload == null) {
			titleDownload = new TitleDownload(collectionItemTitle.getCollections(), collectionItemTitle.getId());
		}

		tableConfig.initTable(titleDownload.getObsDownloads());

		initLabels();

		// preventColumnReordering(tableItens);

		if (titleDownload.getState().getValue() != DownloadState.PREPARING) {

			downloadListening();
			webDataTitle = titleDownload.getTitleWebData();
			fetcherController = new FetcherOrchestrator(webDataTitle, webDataTitle.getSite().getScraping());
			loadCbItems();

		}

		btnNewFetch.setDisable(true);
		loadCbSelect();
		loadCbSource();

		Image image = null;
		try {
			if (CollectionLoaderUtil.isRequiredFilesAvailable(collectionItemTitle))
				image = new Image(new FileInputStream(new File(collectionItemTitle.getImageLocal())));
			else {
				CollectionLoaderUtil.getRequiredFiles(collectionItemTitle);
				image = new Image(new FileInputStream(new File(collectionItemTitle.getImageLocal())));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageViewTitle.setImage(image);
		lblTecTitle.setText(collectionItemTitle.getTitleDataBase());

	}

	@FXML
	public void onClickButtonBack() {

		if (titleDownload.getState().getValue() == DownloadState.FINISH
				|| titleDownload.getState().getValue() == DownloadState.FAILED) {
			deleteTitleDownload();
		}

		back();

	}

	@FXML
	public void onClickButtonFetch() {

		try {
			ListItemForm itemForm = orchestrator.readById(titleDownload.getId());
			SiteForm siteForm = itemForm.getCurretScrapingLink();
			if (siteForm.getItemLink() == null || siteForm.getItemLink().isEmpty()) {
				newFetch();
			} else {
				recoverLastFetch(siteForm);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AlertWindowView.exceptionAlert(e);
		}

	}

	private void recoverLastFetch(SiteForm siteForm) {
		// TODO Auto-generated method stub
		recoverFetch(siteForm);
		startFetch(siteForm.getItemLink());
		scraping = siteForm.getSiteName().getScraping();
	}

	private void newFetch() {
		// TODO Auto-generated method stub
		new Thread(() -> {
			if (isSourceSelect(cbSource)) {
				Platform.runLater(() -> {
					piLoadFetch.setVisible(true);
				});

				fetch();

			} else {
				AlertWindowView.warningAlert("Download Manager", "Missing information",
						"You need to inform a source before fetch");
			}
		}).start();
	}

	// Recover Fetch
	@FXML
	public void onClickButtonNewFetch() {
		newFetch();
	}

	@FXML
	public void onClickButtonDownload() {

		if (getCbSourceValue() != scraping.getSite()) {
			AlertWindowView.infoAlert("SOURCE CHANGE", "The value of source was change", "Please fetch first");
			piLoadDownload.setVisible(false);
			return;
		}

		// newTitleDownload();
		DownloadQueue.getInstance().addDownload(titleDownload);
		titleDownload.setTitleWebData(webDataTitle);
		selectDownload();
		changeTableItems();
		downloadListening();

	}

	@FXML
	public void onClickButtonPause() {

		if (titleDownload.getState().get() != DownloadState.PREPARING) {
			System.out.println("titleDownload.isPausing() " + titleDownload.isPausing());
			if (titleDownload.isPausing()) {
				System.out.println("Wait all items pause first!");
				return;
			}

			if (!titleDownload.getPauseProperty().get()) {
				btnPause.setText("RESUME");
				titleDownload.pause();

			} else {

				btnPause.setText("PAUSE");
				titleDownload.resume();

			}
		}
		System.out.println("The table is empty, any download changed state");
	}

	@FXML
	public void onClickButtonCancel() {

		if (titleDownload.getState().get() != DownloadState.PREPARING) {

			CustomLogger.log("Cancel");
			titleDownload.cancel();

		}
		System.out.println("The table is empty, any download will change the state");

	}

	@FXML
	public void onClickButtonReplaceLink() {
		// TODO Auto-generated method stub
		String result = AlertWindowView.inputAlet("Link", "Inform new the link");
		if (result != null & !result.isEmpty()) {
			lblSearchResult.setText(result);
			startFetch(result);
		}
	}

	private void initLabels() {
		lblItemsDownloaded.setText("Downlaoded: 0");
		lblItemsFailed.setText("Failed: 0");
		lblItemsTotal.setText("Total: 0");
		lblSearchResult.setWrapText(true);
	}

	private void loadCbSource() {
		try {
			CustomLogger.log("Source loadCbSource");
			List<String> sitesOptions = new ArrayList<String>();

			for (Sites site : Sites.values()) {
				if (site.getCategory() == collectionItemTitle.getCategory()) {
					sitesOptions.add(site.toString());

				}
			}

			cbSource.valueProperty().addListener((obs, oldvalue, newvalue) -> {
				Object value = cbSource.getValue();
				try {
					if (!value.toString().equals("Source")) {
						btnNewFetch.setDisable(false);
					}
				} catch (ClassCastException e) {
					// TODO: handle exception
					btnNewFetch.setDisable(false);
				}

			});

			ObservableList<String> options = FXCollections.observableArrayList(sitesOptions);

			cbSource.getItems().addAll(options);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void loadCbSelect() {
		ObservableList<String> options = FXCollections.observableArrayList("Select All", "Select One", "Select Between",
				"Select Update");

		cbSelect.getItems().addAll(options);
		cbSelect.getSelectionModel().selectFirst();
		cbSelect.getSelectionModel().selectedItemProperty().addListener((ops, oldValue, newValue) -> {
			if (newValue.equals("Select One")) {
				cbItens.setDisable(false);
			} else {
				disableAndSetDefaultValue(cbItens, null);

			}
			CustomLogger.log(newValue);
		});
	}

	private void loadCbItems() {
		Platform.runLater(() -> {
			try {
				cbItens.getItems().clear();
				if (!webDataTitle.getWebDatas().isEmpty())
					for (ItemWebData sub : webDataTitle.getWebDatas()) {
						System.out.println(sub.getUrl());
						cbItens.getItems().add(sub.getName());

					}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});

	}

	private void downloadListening() {

		pbTotalProgress.progressProperty().bindBidirectional(titleDownload.getTotalProgressPropery());

		lblTimeRemain.textProperty().bindBidirectional(titleDownload.getRemain());

		titleDownload.getConcludedDownlods().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> lblItemsDownloaded.setText("Downloaded: " + newvalue));
		});

		titleDownload.getTotalDownlods().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> lblItemsTotal.setText("Total: " + newvalue));
		});
		titleDownload.getFailedDownlods().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> lblItemsFailed.setText("Failed: " + newvalue));
		});

	}

	private void changeTableItems() {
		tableConfig.changeTableItems(titleDownload.getObsDownloads());
	}

	private void back() {
		navigator.back();
	}

	private void selectDownload() {
		try {
			DownloadOrchestrator downloadController = new DownloadOrchestrator(titleDownload, fetcherController);

			int type = cbSelect.getSelectionModel().getSelectedIndex();
			titleDownload.setState(DownloadState.DOWNLOADING);
//			if (titleDownload.getDownloadType() != type) {
//				titleDownload.setDownloadType(type);
//			}else {
//				
//			}
			switch (type) {

			case 0:
				downloadController.downloadAll();
				break;
			case 1:
				downloadController.downloadByIndex(cbItens.getSelectionModel().getSelectedIndex());
				break;
			case 2:
				downloadController.downloadBetween(0, 10);
				break;
			case 3:
				downloadController.downloadUpdate(collectionItemTitle);
				break;

			default:
				throw new Exception("Invalid download type");
			}
		} catch (Exception e) {
			// TODO: handle exception
			AlertWindowView.exceptionAlert(e);
			e.printStackTrace();
		}
	}

	private void search() {

		String querry = collectionItemTitle.getTitleDataBase();
		searchController = new SearchController(collectionItemTitle, scraping);

		searchController.searchResultProperty().addListener((obs, oldvalue, newvalue) -> {

			String result = newvalue.getUrl();

			showItemDetails(result);
			startFetch(result);

		});

		searchController.searchFailedProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue)
				Platform.runLater(() -> {

					if (AlertWindowView.confirmationAlert("Fetch error", "MABurn could not find the title link",
							"You want manually put the link?")) {

						String input = AlertWindowView.inputAlet("Manual fetch", "Please insert the title link");
						System.out.println("CHANGE LINK: " + input);
						lblSearchResult.setText(input);
						startFetch(input);
					}
				});

			Platform.runLater(() -> piLoadFetch.setVisible(false));

		});

		searchController.search(querry);

	}

	private void showItemDetails(String result) {
		Platform.runLater(() -> {
			lblSearchResult.setText(result);
			lblSearchResult.setVisible(true);
			btnReplaceLink.setVisible(true);
			btnNewFetch.setVisible(true);
			btnNewFetch.setDisable(false);
		});
		collectionItemTitle.setLink(result);
	}

	private void startFetch(String result) {

		piLoadFetch.setVisible(true);
		webDataTitle.setUrl(result);
		fetcherController = new FetcherOrchestrator(webDataTitle, scraping);

		fetcherController.titlePropery().addListener((obs, oldValue, newValue) -> {
			if (webDataTitle.isFetched()) {
				loadCbItems();
				saveFetchLink(scraping.getSite(), result);
				btnReplaceLink.setDisable(false);
				enableTitleDownloadControlers();

			} else
				AlertWindowView.errorAlert("Fetch error", "No items found", "");
			piLoadFetch.setVisible(false);
			btnDownload.setDisable(false);

		});

		fetcherController.fetchHome();

	}

	private void saveFetchLink(Sites site, String link) {
		CollectionForm form;
		try {
			form = orchestrator.read();

			ListItemForm itemForm = form.getItems().stream().filter(item -> item.getId() == titleDownload.getId())
					.findFirst().get();
			SiteForm siteForm = new SiteForm();

			siteForm.setItemLink(link);

			siteForm.setSiteName(site);

			itemForm.setCurretScrapingLink(siteForm);
			Platform.runLater(() -> cbSource.setValue(site.toString()));

			orchestrator.write(form);
			showItemDetails(link);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fetch() {

		try {

			createFetch();
			search();

		} catch (WebScrapingException e) {
			// TODO: handle exception
			AlertWindowView.errorAlert("Fetch error", e.getMessage(), "Please try another site");
			piLoadFetch.setVisible(false);
		}
	}

	private void createFetch() {
		createTitleDownload();
		createWebScraping();
		createWebDataTitle();
	}

	private void recoverFetch(SiteForm siteForm) {
		recoverWebScraping(siteForm);
		createWebDataTitle();
	}

	private void createWebScraping() {
		scraping = getCbSourceValue().getScraping();

	}

	private Sites getCbSourceValue() {
		for (Sites site : Sites.values()) {
			if (site.toString() == cbSource.getValue()) {
				return site;
			}
		}
		return null;
	}

	private void recoverWebScraping(SiteForm siteForm) {
		scraping = siteForm.getSiteName().getScraping();
	}

//	private void crateWebScraping() {
//		scraping = cbSource.getValue().getScraping();
//	}

	private void createWebDataTitle() {
		if (collectionItemTitle.getCategory() == Category.ANIME)
			webDataTitle = new AnimeWebData(collectionItemTitle.getTitleDataBase());
		else if (collectionItemTitle.getCategory() == Category.MANGA)
			webDataTitle = new MangaWebData(collectionItemTitle.getTitleDataBase());
	}

	public void enableTitleDownloadControlers() {

		Platform.runLater(() -> {
			lblSource.setText("Source: " + scraping.getSite().getUrl());
			lblPath.setText("Path: " + collectionItemTitle.getDestination());
		});
		btnDownload.setDisable(false);
		btnPause.setDisable(false);
		btnCancel.setDisable(false);

		cbSource.setDisable(false);
		cbSelect.setDisable(false);

	}

	public void createTitleDownload() {

		if (titleDownload == null) {
			titleDownload = new TitleDownload(collectionItemTitle.getCollections(), collectionItemTitle.getId());
			DownloadQueue.getInstance().addDownload(titleDownload);
		}
	}

	private void deleteTitleDownload() {
		DownloadQueue.getInstance().removeDownload(titleDownload);
		titleDownload = null;

	}

	public <T> void disableAndSetDefaultValue(ComboBox<T> cb, T value) {
		CustomLogger.log("!" + value);
		cb.setValue(value);
		cb.setDisable(true);

	}

	public <T> boolean isSourceSelect(ComboBox<T> cb) {
		if (cb.getValue().equals("Source")) {
			return false;
		}
		return true;
	}

	public void setTitleDownload(TitleDownload titleDownload) {
		this.titleDownload = titleDownload;
	}

}
