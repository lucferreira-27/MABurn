package com.lucas.ferreira.maburn.controller.title;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.controller.collection.CollectionInterfaceController;
import com.lucas.ferreira.maburn.controller.home.ModelInterface;
import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.TableCollectionItemModel;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.service.Database;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;
import com.lucas.ferreira.maburn.util.CollectionLoaderUtil;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.comparator.ItemFileComparator;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TitleController implements ModelInterface {
	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final long serialVersionUID = 137374207309422526L;
	private static final String ICON_PATH = "icons/";
	private Navigator navigator = new Navigator();
	private Collections collections;
	private CollectionTitle title;
	@FXML
	private ImageView imageViewTitle;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnWatch;
	@FXML
	private Button btnRead;
	@FXML
	private Button btnRemove;
	@FXML
	private Label lblTitle;
	@FXML
	private TextArea txtAreaSynopsis;
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblPublishedDate;
	@FXML
	private Label lblAverageRating;

	@FXML
	private ImageView imgStatus;
	@FXML
	private ImageView imgPublishedDate;
	@FXML
	private ImageView imgAverageRating;

	@FXML
	private ImageView imgDownload;
	@FXML
	private ImageView imgUpdate;
	@FXML
	private ImageView imgHide;
	@FXML
	private ImageView imgRemove;

	@FXML
	private TableView<TableCollectionItemModel> tableItens;
	@FXML
	private TableColumn<TableCollectionItemModel, String> nameCol;
	@FXML
	private TableColumn<TableCollectionItemModel, Button> sizeCol;
	@FXML
	private TableColumn<TableCollectionItemModel, String> pathCol;

	public TitleController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Icon iconStatus = new Icon(imgStatus, new IconConfig(ICON_PATH, Icons.STATUS, "Status"));

		Icon iconPublished = new Icon(imgPublishedDate, new IconConfig(ICON_PATH, Icons.PUBLISHED, "Published"));

		Icon iconRating = new Icon(imgAverageRating, new IconConfig(ICON_PATH, Icons.RATING, "Rating"));
		
		
		
		Icon iconDownload = new Icon(imgDownload, new IconConfig(ICON_PATH,Icons.DOWNLOAD_2, "Download"));
		iconDownload.setProperties((event) -> onClickButtonDownload());

		Icon iconUpdate = new Icon(imgUpdate, new IconConfig(ICON_PATH, Icons.UPDATE_2, "Update"));
		iconUpdate.setProperties((event) -> onClickButtonUpdate());

		Icon iconHide = new Icon(imgHide, new IconConfig(ICON_PATH, Icons.HIDE, "Hide"));
		iconHide.setProperties((event) -> onClickButtonHide());

		Icon iconRemove = new Icon(imgRemove, new IconConfig(ICON_PATH, Icons.REMOVE, "Remove"));
		iconRemove.setProperties((event) -> onClickButtonRemove());

		

		CollectionInterfaceController collectionController = (CollectionInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.COLLECTION);
		collections = collectionController.getCollectionGridPane().getCollection();
		title = collections.getActualItem();
		LOGGER.info("Initialize Title");

		loadTitleDatas();

	}

	@FXML
	public void onClickButtonBack() {
		
		navigator.back();
		// itensView.setCollections(collections);

	}

	private void loadTitleDatas() {

		CollectionTitle item = collections.getActualItem();
		LOGGER.info("Loading data from Local title");

		Image image = null;
		try {
			if (CollectionLoaderUtil.isRequiredFilesAvailable(item))
				image = new Image(new FileInputStream(new File(item.getImageLocal())));
			else {
				CollectionLoaderUtil.getRequiredFiles(item);
				image = new Image(new FileInputStream(new File(item.getImageLocal())));
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		tableItens.setPlaceholder(new Label(LanguageReader.read("LABEL_TABLE_EMPTY")));
		lblTitle.setText(item.getTitleDataBase());
		imageViewTitle.setImage(image);
		imageViewTitle.setOnMouseClicked(event -> {

			onClickImageViewTitle();

		});

		loadTable(item);
		new Thread(() -> {
			loadInformation(item);
		}).start();

	}

	public void loadInformation(CollectionTitle item) {
		LOGGER.info("Loading data from KitsuDatabase");
		Database database = new KitsuDatabase();
		CollectDatas datas = database.read(item.getId(), item.getCategory());
		Platform.runLater(() -> {
			txtAreaSynopsis.setText(datas.getSynopsis());
			txtAreaSynopsis.setVisible(true);
			lblStatus.setText(datas.getStatus().substring(0, 1).toUpperCase() + datas.getStatus().substring(1));
			lblStatus.setVisible(true);
			lblPublishedDate.setText(datas.getPublishedDate().replaceAll("-", "/"));
			lblPublishedDate.setVisible(true);
			lblAverageRating.setText(String.valueOf(datas.getAvaregeRating()));
			lblAverageRating.setVisible(true);
		});
	}

	public void onClickImageViewTitle() {
		LOGGER.config("Click on [ImageViewTitle]");
		CollectionTitle item = collections.getActualItem();
		Desktop desk = Desktop.getDesktop();
		try {
			desk.browse(new URI(item.getDataBaseUrl()));
		} catch (IOException | URISyntaxException e) {
			
			e.printStackTrace();
		}
	}

	public void onClickButtonDownload() {
		try {
			navigator.openFromRegisteredState(Interfaces.TITLE_DOWNLOAD, String.valueOf(collections.getActualItem().getId()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onClickButtonUpdate() {
		CollectionTitle item = collections.getActualItem();
		LOGGER.config("Click on [Update]");

		loadTable(item);
	}

	public void onClickButtonRemove() {
		LOGGER.config("Click on [Remove]");
		XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
		try {
			CollectionForm collectionForm = orchestrator.read();
			orchestrator.removeById(collectionForm, collections.getActualItem().getId());
			AlertWindowView.infoAlert(collections.getActualItem().getTitleFileName() + " REMOVE!", "SUCCEED!",
					"Path: " + collections.getDestination());
		} catch (IOException e) {
			  
			e.printStackTrace();
		}
	}

	public void onClickButtonHide() {
		LOGGER.config("Click on [Hide]");
		XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
		try {

			CollectionForm form = orchestrator.read();

			boolean result = AlertWindowView.confirmationAlert("HIDE TITLE",
					"Do you want HIDE " + collections.getActualItem().getTitleFileName() + "?",
					"If the title is hidden, it does not appear in the collection, but it will still be in the MABurn system");

			if (!result)
				return;

			ListItemForm itemForm = form.getItems().stream()
					.filter(item -> item.getId() == collections.getActualItem().getId()).findFirst().get();

			itemForm.setBlackList(true);
			orchestrator.write(form);

			

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void loadTable(CollectionTitle item) {
		LOGGER.config("Load Title Table");
		List<CollectionItem> listSubItens = item.getListSubItens();

		List<TableCollectionItemModel> tableItens = new ArrayList<>();
		if (tableItens.size() > 0) {
			tableItens.clear();
		}
		try {
			listSubItens.sort(new ItemFileComparator());

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			
		}
		String btnText = "";

		if (item.getCategory() == Category.ANIME) {
			btnText = btnWatch.getText();
		} else if (item.getCategory() == Category.MANGA) {
			btnText = btnRead.getText();
		}

		for (CollectionItem subItem : listSubItens) {
			Button btn = new Button();
			btn.setText(btnText);
			btn.setOnAction(event -> {
				try {
					DirectoryModel.openDirectory(subItem.getDestination());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			});
			tableItens.add(new TableCollectionItemModel(subItem.getName(), btn, 0, subItem.getDestination()));
		}

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("btnFolder"));
		sizeCol.setCellFactory(tc -> new TableCell<TableCollectionItemModel, Button>() {

			@Override
			protected void updateItem(Button btn, boolean empty) {
				super.updateItem(btn, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btn.setOnAction(event -> {
						try {
							DirectoryModel.openDirectory(getTableView().getItems().get(getIndex()).getPath());
							getTableView().getSelectionModel().select(getIndex());
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					});

					setGraphic(btn);
					setText(null);
				}

			}
		});
		this.tableItens.setItems(FXCollections.observableArrayList(tableItens));
		this.tableItens.refresh();
		preventColumnReordering(this.tableItens);
	}

	public static <T> void preventColumnReordering(TableView<T> tableView) {
		Platform.runLater(() -> {
			for (Node header : tableView.lookupAll(".column-header")) {
				header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
			}
		});
	}

	public CollectionTitle getTitle() {
		return title;
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
