
package com.lucas.ferreira.maburn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.fetch.FetcherOrchestrator;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
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
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
	private CollectionItem collectionItemTitle;
	private TitleWebData webDataTitle;
	private TitleDownload titleDownload;
	private WebScraping scraping;
	private FetcherOrchestrator fetcherController;
	private SearchController searchController;
	private Navigator navigator = new Navigator();
	@FXML
	private Button btnFetch;

	@FXML
	private Button btnPause;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnDownload;

	@FXML
	private Button btnChangeLink;

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

		TitleInterfaceController titleController = (TitleInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.TITLE);

		collectionItemTitle = titleController.getTitle();
		titleDownload = DownloadQueue.getInstance().getDownload(collectionItemTitle.getId());
		if (titleDownload == null) {
			titleDownload = new TitleDownload(collectionItemTitle.getCollections(), collectionItemTitle.getId());
		}

		initTable();

		initLabels();

		preventColumnReordering(tableItens);

		if (titleDownload.getState().getValue() != DownloadState.PREPARING) {

			downloadListening();
			webDataTitle = titleDownload.getTitleWebData();
			fetcherController = new FetcherOrchestrator(webDataTitle, webDataTitle.getSite().getScraping());
			loadCbItems();

		}

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

		if (cbSource.getValue() != scraping.getSite()) {
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
		System.out.println("The table is empty, any download changed state");

	}

	@FXML
	public void onClickButtonChangeLink() {
		// TODO Auto-generated method stub
		String result = AlertWindowView.inputAlet("Link", "Inform new the link");
		startFetch(result);
	}

	private void initLabels() {
		lblItemsDownloaded.setText("Downlaoded: 0");
		lblItemsFailed.setText("Failed: 0");
		lblItemsTotal.setText("Total: 0");
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

	private void loadCbItems(List<ItemWebData> items) {
		Platform.runLater(() -> {
			try {
				cbItens.getItems().clear();
				if (!items.isEmpty())
					for (ItemWebData sub : items) {

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
		tableItens.setItems(titleDownload.getObsDownloads());
	}

	public void initTable() {
		tableItens.setItems(titleDownload.getObsDownloads());
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
		clState.setCellValueFactory(
				new PropertyValueFactory<Downloader<CollectionSubItem>, DownloadState>("downloadState"));
		clState.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, DownloadState>() {
			@Override
			protected void updateItem(DownloadState item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item));

				}

			};
		});
		clActionPause
				.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("actionPause"));
		clActionPause.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {

					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					final Button btn = downloader.getBtnPause();

//					if(downloader.getDownloadState() == DownloadState.FINISH) {
//						btn.setText("OPEN");
//			
//					}else {
//						btn.setText("PAUSE");
//						
//					}
					setGraphic(btn);
//					downloader.downloadStateProperty().addListener((obs, oldvalue, newvalue) -> {
//						// System.out.println("[" + downloader.getName() + "] STATE: " +
//						// downloader.getDownloadState());
//						Platform.runLater(() -> {
//							if (newvalue.equals(String.valueOf(DownloadState.FINISH))
//									&& !btn.getText().equalsIgnoreCase("OPEN")) {
//								System.out.println(downloader.getName() + " -> OPEN");
//								btn.setText("OPEN");
//
//							}
//						});
//					});
				}
//					btn.setOnAction(event -> {
//						if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.FINISH))) {
//							if (!downloader.getPauseProperty().get()) {
//								CustomLogger.log("btnAction : pause");
//								btn.setText("RESUME");
//								if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
//									downloader.setDownloadState(DownloadState.PAUSING);
//
//									downloader.pause();
//								} else
//									CustomLogger.log("PAUSING, PLEASE WAIT");
//							} else if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
//								CustomLogger.log("btnAction : resume");
//								btn.setText("PAUSE");
//								downloader.resume();
//							}
//						} else {
//							btn.setText("OPEN");
//
//							CollectionSubItem subItem = downloader.getSubItem();
//							try {
//								DirectoryModel.openDirectory(subItem.getDestination());
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//
//					});
//					setGraphic(btn);
//					setText(null);
//				}
//				;

			}
		});
		clActionCancel
				.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("actionCancel"));
		clActionCancel.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {

					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					final Button btn = downloader.getBtnCancel();

					setGraphic(btn);
					return;
				}

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
		searchController = new SearchController(collectionItemTitle, scraping);

		searchController.searchResultProperty().addListener((obs, oldvalue, newvalue) -> {

			String result = newvalue.getUrl();

			Platform.runLater(() -> {
				lblSearchResult.setText(result);
				lblSearchResult.setVisible(true);
				btnChangeLink.setVisible(true);
			});
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
		fetcherController = new FetcherOrchestrator(webDataTitle, scraping);

		fetcherController.titlePropery().addListener((obs, oldValue, newValue) -> {
			if (webDataTitle.isFetched()) {
				loadCbItems();
				enableTitleDownloadControlers();
			} else
				AlertWindowView.errorAlert("Fetch error", "No items found", "");
			piLoadFetch.setVisible(false);
			btnDownload.setDisable(false);

		});

		fetcherController.fetchHome();

	}

	private void fetch() {

		try {
			createTitleDownload();
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
		scraping = cbSource.getValue().getScraping();
	}

	private void createWebDataTitle() {
		if (collectionItemTitle.getCategory() == Category.ANIME)
			webDataTitle = new AnimeWebData(collectionItemTitle.getTitleDataBase());
		else if (collectionItemTitle.getCategory() == Category.MANGA)
			webDataTitle = new MangaWebData(collectionItemTitle.getTitleDataBase());
	}

	public void enableTitleDownloadControlers() {

//		piLoadFetch.setVisible(false);
//		lblSource.setVisible(true);
//		lblPath.setVisible(true);
//		lblItemsTotal.setVisible(true);
//		lblTimeRemain.setVisible(true);
//		lblItemsDownloaded.setVisible(true);
//		pbTotalProgress.setVisible(true);
//		lblTotalProgress.setVisible(true);
//		btnPause.setVisible(true);
//		btnCancel.setVisible(true);
//		btnDownload.setVisible(true);

		Platform.runLater(() -> {
			lblSource.setText("Source: " + cbSource.getValue().getUrl());
			lblPath.setText("Path: " + collectionItemTitle.getDestination());
		});
		btnDownload.setDisable(false);
		btnPause.setDisable(false);
		btnCancel.setDisable(false);

		cbSource.setDisable(false);
		cbSelect.setDisable(false);

	}

	private void newTitleDownload() {
		if (titleDownload != null) {
			deleteTitleDownload();
		}

		createTitleDownload();

		titleDownload.setTitleWebData(webDataTitle);
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

}
