
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
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.download.ItemDownload;
import com.lucas.ferreira.maburn.model.download.queue.DownloadController;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.queue.FetcherController;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.download.service.DownloadService;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.model.search.SearchController;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CollectionLoaderUtil;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleDownloadInterfaceView;
import com.lucas.ferreira.maburn.view.TitleInterfaceView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TitleDownloadInterfaceController implements Initializable {
	private TitleDownloadInterfaceView titleDownloadView;
	private CollectionItem collectionItemTitle;
	private TitleWebData webDataTitle;
	private TitleDownload titleDownload;
	private WebScraping scraping;
	private FetcherController fetcherController;
	private SearchController searchController;
	private ItemDownload itemDownload = null;

	private Button btnHome;

	private Button btnConfig;

	private Button btnExtra;

	private List<ItemWebData> items;
	@FXML
	private Button btnFetch;

	@FXML
	private Button btnPause;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnDownload;

	@FXML
	private ComboBox<Sites> cbSource;

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
	private TableColumn<Downloader<CollectionSubItem>, String> clState;

	@FXML
	private TableColumn<Downloader<CollectionSubItem>, String> clActionPause;
	@FXML
	private TableColumn<Downloader<CollectionSubItem>, String> clActionCancel;

	public TitleDownloadInterfaceController(TitleDownloadInterfaceView titleView) {
		this.titleDownloadView = titleView;
		collectionItemTitle = titleView.getTitleInterfaceView().getTitle();
		titleDownload = new TitleDownload(collectionItemTitle.getCollections());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		downloadController();
		initTable();

		try {

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		preventColumnReordering(tableItens);

		loadCbSelect();
		loadCbSource();

		cbSource.valueProperty().addListener((obs, oldvalue, newvalue) -> {
			if (oldvalue != newvalue) {
				itemDownload = null;
			}
		});
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

		back();

	}

	@FXML
	public void onClickButtonFetch() {
		CustomLogger.log("Fetch");
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

	@FXML
	public void onClickButtonDownload() {

		if (cbSource.getValue() != collectionItemTitle.getWebScraping().getSite()) {
			AlertWindowView.infoAlert("SOURCE CHANGE", "The value of source was change", "Please fetch first");
			piLoadDownload.setVisible(false);
			return;
		}
		selectDownload();

	}

	@FXML
	public void onClickButtonPause() {

		if (!titleDownload.getPauseProperty().get())
			titleDownload.pause();
		else
			titleDownload.resume();
	}

	@FXML
	public void onClickButtonCancel() {
		CustomLogger.log("Cancel");
		titleDownload.cancel();
		pbTotalProgress.progressProperty().unbind();
		pbTotalProgress.setProgress(0);
	}

	private void loadCbSource() {
		try {
			CustomLogger.log("Source loadCbSource");
			List<Sites> sitesOptions = new ArrayList<Sites>();

			for (Sites site : Sites.values()) {
				if (site.getCategory() == collectionItemTitle.getCategory()) {
					sitesOptions.add(site);

				}
			}

			ObservableList<Sites> options = FXCollections.observableArrayList(sitesOptions);

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

		cbItens.getItems().clear();
		if (!webDataTitle.getWebDatas().isEmpty())
			for (ItemWebData sub : webDataTitle.getWebDatas()) {

				Platform.runLater(() -> {
					cbItens.getItems().add(sub.getName());
					System.out.println(sub.getUrl());
				});

			}

	}

	private void downloadController() {

//		btnConfig = MainInterfaceView.getInstance().getMenuController().getBtnConfig();
//		btnExtra = MainInterfaceView.getInstance().getMenuController().getBtnExtra();
//		btnHome = MainInterfaceView.getInstance().getMenuController().getBtnHome();
//
//		EventHandler<ActionEvent> onClickBtnConfig = MainInterfaceView.getInstance().getMenuController().getBtnConfig()
//				.getOnAction();
//		EventHandler<ActionEvent> onClickBtnHome = MainInterfaceView.getInstance().getMenuController().getBtnExtra()
//				.getOnAction();
//		EventHandler<ActionEvent> onClickBtnExtra = MainInterfaceView.getInstance().getMenuController().getBtnHome()
//				.getOnAction();

	}

	public void initTable() {
		clName.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("name"));
		clName.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(item);

				}

			};
		});
		clSize.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("size"));
		clSize.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(DataStorageUtil.converterUnit(item));

				}

			};
		});
		clSpeed.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("speed"));
		clSpeed.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(DataStorageUtil.converterSpeedUnit(item));

				}

			};
		});
		clCompleted.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("completed"));
		clCompleted.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(DataStorageUtil.converterUnit(item));

				}

			};
		});
		clProgress.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("progress"));
		clProgress.setCellFactory(ProgressBarTableCell.<Downloader<CollectionSubItem>>forTableColumn());
		clState.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("downloadState"));
		clState.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(item);

				}

			};
		});
		clActionPause
				.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("actionPause"));
		clActionPause.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {
			final Button btn = new Button("PAUSE");

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					downloader.downloadStateProperty().addListener((obs, oldvalue, newvalue) -> {
						if (newvalue.equals(String.valueOf(DownloadState.FINISH))) {
							Platform.runLater(() -> btn.setText("OPEN"));

						}
					});

					btn.setOnAction(event -> {
						if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.FINISH))) {
							if (!downloader.getPauseProperty().get()) {
								CustomLogger.log("btnAction : pause");
								btn.setText("RESUME");
								if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
									downloader.setDownloadState(DownloadState.PAUSING);

									downloader.pause();
								} else
									CustomLogger.log("PAUSING, PLEASE WAIT");
							} else if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
								CustomLogger.log("btnAction : resume");
								btn.setText("PAUSE");
								downloader.resume();
							}
						} else {
							btn.setText("OPEN");

							CollectionSubItem subItem = downloader.getSubItem();
							try {
								DirectoryModel.openDirectory(subItem.getDestination());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
					setGraphic(btn);
					setText(null);
				}
				;

			}
		});
		clActionCancel
				.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("actionCancel"));
		clActionCancel.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {

			@Override
			protected void updateItem(String item, boolean empty) {
				final Button btn = new Button("CANCEL");

				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					downloader.downloadStateProperty().addListener((obs, oldvalue, newvalue) -> {
						if (newvalue.equals(String.valueOf(DownloadState.FINISH))) {
							Platform.runLater(() -> btn.setDisable(true));

						}
					});

					downloader.getCancelProperty().addListener((ops, oldValue, newValue) -> {
						CustomLogger.log("CANCEL ACTION FOR: " + downloader.getItemWebData().getName());
						CustomLogger.log("CancelProperty: " + newValue);
						CustomLogger.log("DownlaodState: " + downloader.getDownloadState());
						if (!newValue) {
							btn.setText("CANCEL");
						} else {

							btn.setText("REMOVE");
						}
					});

					btn.setOnAction(event -> {
						if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.FINISH))) {
							if (!downloader.getCancelProperty().get()) {
								CustomLogger.log("btnAction : cancel");
								downloader.setDownloadState(DownloadState.CANCELING);
								downloader.kill();

								CustomLogger.log(itemDownload.getDownloadService().getItems()
										.remove(downloader.getItemWebData()));

							} else {
								CustomLogger.log("btnAction : remove");

								tableItens.getItems().remove(downloader);

							}

						}

					});
					setGraphic(btn);
					setText(null);
				}
				;

			}
		});

		preventColumnReordering(tableItens);
		tableItens.setItems(titleDownload.getObsDownloads());
		titleDownload.getObsDownloads().addListener(new ListChangeListener<Downloader<CollectionSubItem>>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Downloader<CollectionSubItem>> c) {
				// System.out.println("Changed on " + c);
				if (c.next()) {
					System.out.println("[Downloader] " + c.getList().get(c.getFrom()));

				}

			}

		});
	}

	private void back() {
		TitleInterfaceView titleInterfaceView = this.titleDownloadView.getTitleInterfaceView();
		titleInterfaceView.loadMainInterfaceFX();
	}

	private void selectDownload() {
		try {
			DownloadController downloadController = new DownloadController(titleDownload, fetcherController);

			int type = cbSelect.getSelectionModel().getSelectedIndex();

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

	public static <T> void preventColumnReordering(TableView<T> tableView) {
		System.out.println((LanguageReader.read("LABEL_TABLE_EMPTY")));
		tableView.setPlaceholder(new Label(LanguageReader.read("LABEL_TABLE_EMPTY")));
		Platform.runLater(() -> {
			for (Node header : tableView.lookupAll(".column-header")) {
				header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
			}
		});
	}

	private void search() {

		String querry = collectionItemTitle.getTitleDataBase();
		searchController = new SearchController(collectionItemTitle);

		searchController.searchResultProperty().addListener((obs, oldvalue, newvalue) -> {

			String result = newvalue.getUrl();

			Platform.runLater(() -> lblSearchResult.setText(result));
			collectionItemTitle.setLink(result);
			startFetch(result);

		});

		searchController.searchFailedtProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue)
				Platform.runLater(() -> {

					if (AlertWindowView.confirmationAlert("Fetch error", "MABurn could not find the title link",
							"You want manually put the link?")) {

						String input = AlertWindowView.inputAlet("Manual fetch", "Please put the title link");
						startFetch(input);
					}
				});

			Platform.runLater(() -> piLoadFetch.setVisible(false));

		});

		searchController.search(querry);

	}

	private void startFetch(String result) {

		piLoadFetch.setVisible(true);
		webDataTitle.setUrl(result);
		fetcherController = new FetcherController(webDataTitle, scraping);

		fetcherController.titlePropery().addListener((obs, oldValue, newValue) -> {
			System.out.println("TitlePropery: " + newValue);
			if (webDataTitle.isFetched()) {
				System.out.println("LoadCbItems");
				loadCbItems();
				showHideDatas();
			} else
				AlertWindowView.errorAlert("Fetch error", "No items found", "");
			piLoadFetch.setVisible(false);

		});

		fetcherController.fetchHome();

	}

	private void fetch() {

		try {

			crateWebScraping();
			createWebDataTitle();
			search();

		} catch (WebScrapingException e) {
			// TODO: handle exception
			AlertWindowView.errorAlert("Fetch error", e.getMessage(), "Please try another site");
			piLoadFetch.setVisible(false);
		}
	}

	private void crateWebScraping() {
		collectionItemTitle.setWebScraping(cbSource.getValue().getScraping());
		scraping = collectionItemTitle.getWebScraping();
	}

	private void createWebDataTitle() {
		if (collectionItemTitle.getCategory() == Category.ANIME)
			webDataTitle = new AnimeWebData(collectionItemTitle.getTitleDataBase());
		else if (collectionItemTitle.getCategory() == Category.MANGA)
			webDataTitle = new MangaWebData(collectionItemTitle.getTitleDataBase());
	}

	public void showHideDatas() {

		piLoadFetch.setVisible(false);
		lblSource.setVisible(true);
		lblPath.setVisible(true);
		lblItemsTotal.setVisible(true);
		lblTimeRemain.setVisible(true);
		lblItemsDownloaded.setVisible(true);
		pbTotalProgress.setVisible(true);
		lblTotalProgress.setVisible(true);
		btnPause.setVisible(true);
		btnCancel.setVisible(true);
		btnDownload.setVisible(true);

		cbSource.setDisable(false);
		cbSelect.setDisable(false);

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

}
