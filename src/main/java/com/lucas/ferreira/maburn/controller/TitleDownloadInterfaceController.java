package com.lucas.ferreira.maburn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.download.ItemDownload;
import com.lucas.ferreira.maburn.model.download.service.DownloadService;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.enums.DownloadType;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.CollectionLoaderUtil;
import com.lucas.ferreira.maburn.util.DataStorageUtil;
import com.lucas.ferreira.maburn.util.ItemFileComparator;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleDownloadInterfaceView;
import com.lucas.ferreira.maburn.view.TitleInterfaceView;

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

	private MainInterfaceView mainView;
	private TitleDownloadInterfaceView titleDownloadView;
	private CollectionItem collectionItemTitle;
	private TitleWebData webDataTitle;
	private WebScraping scraping;
	private DownloadService service;
	private ItemDownload itemDownload = null;

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

	public TitleDownloadInterfaceController(MainInterfaceView mainView, TitleDownloadInterfaceView titleView) {
		this.mainView = mainView;
		this.titleDownloadView = titleView;
		collectionItemTitle = titleView.getTitleInterfaceView().getTitle();
	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTable();

		try {

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		preventColumnReordering(tableItens);

		loadCbSelect();
		loadCbSource();
		
		cbSource.valueProperty().addListener((obs, oldvalue, newvalue) ->{
			if(oldvalue != newvalue) {
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

	private void loadCbSource() {

		List<Sites> sitesOptions = new ArrayList<Sites>();

		for (Sites site : Sites.values()) {
			if (site.getCategory() == collectionItemTitle.getCategory()) {
				sitesOptions.add(site);

			}
		}

		ObservableList<Sites> options = FXCollections.observableArrayList(sitesOptions);

		cbSource.getItems().addAll(options);

	}

	private void loadCbSelect() {
		ObservableList<String> options = FXCollections.observableArrayList("Select All", "Select One");

		cbSelect.getItems().addAll(options);
		cbSelect.getSelectionModel().selectFirst();
		cbSelect.getSelectionModel().selectedItemProperty().addListener((ops, oldValue, newValue) -> {
			if (newValue.equals("Select One")) {
				cbItens.setDisable(false);
			} else {
				disableAndSetDefaultValue(cbItens, null);

			}
			System.out.println(newValue);
		});
	}

	private void loadCbItens() {

		cbItens.getItems().clear();

		items.forEach(sub -> {
			System.out.println(sub.getName());
			Platform.runLater(() -> cbItens.getItems().add(sub.getName()));
		});

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
					System.out.println(item);
					setGraphic(null);
					setText(null);
				} else {
					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

//					downloader.getPauseProperty().addListener((ops, oldValue, newValue) -> {
//						System.out.println("PauseProperty Listener: " + newValue);
//						System.out.println("DownloadState: " + downloader.getDownloadState());
//						if (!newValue) {
//							btn.setText("PAUSE");
//						} else if(downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
//							btn.setText("RESUME");
//						}
//					});

					downloader.downloadStateProperty().addListener((obs, oldvalue, newvalue) -> {
						if (newvalue.equals(String.valueOf(DownloadState.FINISH))) {
							Platform.runLater(() -> btn.setText("OPEN"));

						}
					});

					btn.setOnAction(event -> {
						if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.FINISH))) {
							if (!downloader.getPauseProperty().get()) {
								System.out.println("btnAction : pause");
								btn.setText("RESUME");
								if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
									downloader.setDownloadState(DownloadState.PAUSING);

									downloader.pause();
								} else
									System.out.println("PAUSING, PLEASE WAIT");
							} else if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.PAUSING))) {
								System.out.println("btnAction : resume");
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
					System.out.println(item);
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
						System.out.println("CANCEL ACTION FOR: " + downloader.getItemWebData().getName());
						System.out.println("CancelProperty: " + newValue);
						System.out.println("DownlaodState: " + downloader.getDownloadState());
						if (!newValue) {
							btn.setText("CANCEL");
						} else {

							btn.setText("REMOVE");
						}
					});

					btn.setOnAction(event -> {
						if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.FINISH))) {
							if (!downloader.getCancelProperty().get()) {
								System.out.println("btnAction : cancel");
								downloader.setDownloadState(DownloadState.CANCELING);
								downloader.kill();
							} else {
								System.out.println("btnAction : remove");

								tableItens.getItems().remove(downloader);
								service.getItems().remove(downloader.getItemWebData());

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

	@FXML
	public void onClickButtonBack() {
		TitleInterfaceView titleInterfaceView = this.titleDownloadView.getTitleInterfaceView();
		titleInterfaceView.loadMainInterfaceFX(mainView);
	}

	@FXML
	public void onClickButtonFetch() {
		System.out.println("Fetch");
		new Thread(() -> {
			if (isSourceSelect(cbSource)) {
				Platform.runLater(() -> {
					piLoadFetch.setVisible(true);
				});
				fetch();
				showHideDatas();

			} else {
				AlertWindowView alert = new AlertWindowView();
				alert.warningAlert("Download Manager", "Missing information",
						"You need to inform a source before fetch");
			}
		}).start();
		;
	}

	@FXML
	public void onClickButtonDownload() {

		System.out.println("Download");
		System.out.println("Fetch Item ...");
		piLoadDownload.setVisible(true);

		if (cbSource.getValue() != collectionItemTitle.getWebScraping().getSite()) {
			AlertWindowView alert = new AlertWindowView();
			alert.infoAlert("SOURCE CHANGE", "The value of source was change", "Please fetch first");
			piLoadDownload.setVisible(false);
			
			return;
		}

		if (cbSelect.getValue().equals("Select All")) {
			new Thread(() -> {
				downloadAll();

			}).start();
		} else {

			if (itemDownload == null) {
				new Thread(() -> {
					downloadOne();

				}).start();

			} else {
				new Thread(() -> {
					downloadInQueue();

				}).start();

			}
		}

		System.out.println("Fetch!");

	}

	@FXML
	public void onClickButtonPause() {

		if (!service.getPauseProperty().get()) {
			System.out.println("btnAction : pause");
			btnPause.setText("RESUME");
			if (!(service.getDownloadState() == DownloadState.PAUSING)) {
				service.setDownloadState(DownloadState.PAUSING);

				service.pause();
			} else
				System.out.println("PAUSING, PLEASE WAIT");
		} else if (!(service.getDownloadState() == DownloadState.PAUSING)) {
			System.out.println("btnAction : resume");
			btnPause.setText("PAUSE");
			service.resume();
		}

	}

	@FXML
	public void onClickButtonCancel() {
		System.out.println("Cancel");
		service.cancelDownload();
		pbTotalProgress.progressProperty().unbind();
		pbTotalProgress.setProgress(0);
	}

	public static <T> void preventColumnReordering(TableView<T> tableView) {
		Platform.runLater(() -> {
			for (Node header : tableView.lookupAll(".column-header")) {
				header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
			}
		});
	}

	private void fetch() {
		CollectionItem item = collectionItemTitle;
		System.err.println("Scraping: " + cbSource.getValue().getScraping());
		collectionItemTitle.setWebScraping(cbSource.getValue().getScraping());
		scraping = collectionItemTitle.getWebScraping();
		System.out.println(scraping);
		String resultUrl = scraping.fetchSearchTitle(item.getTitleDataBase()).get(0).getUrl();
		item.setLink(resultUrl);

		if (item.getCategory() == Category.ANIME)
			webDataTitle = new AnimeWebData(item.getTitleDataBase());
		else if (item.getCategory() == Category.MANGA)
			webDataTitle = new MangaWebData(item.getTitleDataBase());

		webDataTitle.setUrl(item.getLink());
		items = scraping.fecthTitle(webDataTitle).getWebDatas();

		loadCbItens();

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

	private void downloadAll() {
		itemDownload = new ItemDownload(collectionItemTitle, DownloadType.DOWNLOAD_ALL);
		itemDownload.getFetchItemProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue) {
				Platform.runLater(() -> piLoadDownload.setVisible(false));
			}
		});
		itemDownload.download();
		service = itemDownload.getDownloadService();

		Platform.runLater(() -> {
			for (ItemWebData it : service.getItems()) {
				tableItens.getItems().add(it.getDownloader());
			}
			pbTotalProgress.progressProperty().bind(service.progressProperty());

		});
	}

	private void downloadOne() {

		itemDownload = new ItemDownload(collectionItemTitle, DownloadType.DOWNLOAD_SELECT,
				cbItens.getSelectionModel().getSelectedIndex());
		itemDownload.getFetchItemProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue) {
				Platform.runLater(() -> piLoadDownload.setVisible(false));
			}
		});
		itemDownload.download();
		service = itemDownload.getDownloadService();

		Platform.runLater(() -> {
			tableItens.getItems().add(service.getItems().get(service.getItems().size() - 1).getDownloader());
			pbTotalProgress.progressProperty().bind(service.progressProperty());

		});

	}

	private void downloadInQueue() {
		System.out.println();
		itemDownload.addItem(cbItens.getSelectionModel().getSelectedIndex());
		
		service = itemDownload.getDownloadService();

		Platform.runLater(() -> {
			tableItens.getItems().add(service.getItems().get(service.getItems().size() - 1).getDownloader());
			pbTotalProgress.progressProperty().bind(service.progressProperty());

		});

	}

	public <T> void disableAndSetDefaultValue(ComboBox<T> cb, T value) {
		System.out.println("!" + value);
		cb.setValue(value);
		cb.setDisable(true);

	}

	public <T> boolean isSourceSelect(ComboBox<T> cb) {
		if (cb.getValue().equals("Source")) {
			return false;
		}
		return true;
	}

	public void showLoadInFetchArea() {

	}

}
