package com.lucas.ferreira.maburn.controller;

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

import com.lucas.ferreira.maburn.model.DirectoryModel;
import com.lucas.ferreira.maburn.model.TableCollectionItemModel;
import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.model.service.Database;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;
import com.lucas.ferreira.maburn.util.CollectionLoaderUtil;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.comparator.ItemFileComparator;
import com.lucas.ferreira.maburn.view.AlertWindowView;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class TitleInterfaceController implements Initializable {
	private Navigator navigator = new Navigator();
	private Collections collections;
	private CollectionItem title;
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
	private TableView<TableCollectionItemModel> tableItens;
	@FXML
	private TableColumn<TableCollectionItemModel, String> nameCol;
	@FXML
	private TableColumn<TableCollectionItemModel, Button> sizeCol;
	@FXML
	private TableColumn<TableCollectionItemModel, String> pathCol;

	public TitleInterfaceController() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		CollectionInterfaceController collectionController = (CollectionInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.COLLECTION);
		collections = collectionController.getCollection();
		title = collections.getActualItem();
		CustomLogger.log("UPDATED SUB ITENS ....");
		CustomLogger.log("UPDATEDED SUB ITENS!");

		loadTitleDatas();

	}

	@FXML
	public void onClickButtonBack() {
		System.out.println("Back: " + collections);
		navigator.back();
		// itensView.setCollections(collections);

	}

	private void loadTitleDatas() {

		CollectionItem item = collections.getActualItem();

		Image image = null;
		try {
			if (CollectionLoaderUtil.isRequiredFilesAvailable(item))
				image = new Image(new FileInputStream(new File(item.getImageLocal())));
			else {
				CollectionLoaderUtil.getRequiredFiles(item);
				image = new Image(new FileInputStream(new File(item.getImageLocal())));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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

	public void loadInformation(CollectionItem item) {
		Database database = new KitsuDatabase();
		CollectDatas datas = database.read(item.getId(), item.getCategory());
		Platform.runLater(() -> {
			txtAreaSynopsis.setText(datas.getSynopsis());
			txtAreaSynopsis.setVisible(true);
			lblStatus.setText(lblStatus.getText() + ": " + datas.getStatus());
			lblStatus.setVisible(true);
			lblPublishedDate.setText(lblPublishedDate.getText() + ": " + datas.getPublishedDate());
			lblPublishedDate.setVisible(true);
			lblAverageRating.setText(lblAverageRating.getText() + ": " + datas.getAvaregeRating());
			lblAverageRating.setVisible(true);
		});
	}

	public void onClickImageViewTitle() {
		CollectionItem item = collections.getActualItem();
		Desktop desk = Desktop.getDesktop();
		try {
			desk.browse(new URI(item.getDataBaseUrl()));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onClickButtonDownload() {
		navigator.open(Interfaces.TITLE_DOWNLOAD);
//		TitleDownloadInterfaceView titleDownload = new TitleDownloadInterfaceView(titleView);
//		titleDownload.loadMainInterfaceFX();
	}

	public void onClickButtonUpdate() {
		CollectionItem item = collections.getActualItem();
		CustomLogger.log("UPDATED SUB ITENS ....");
		CustomLogger.log("UPDATEDED SUB ITENS!");
		loadTable(item);
	}

	public void onClickButtonRemove() {
		XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
		try {
			CollectionForm collectionForm = orchestrator.read();
			orchestrator.removeById(collectionForm, collections.getActualItem().getId());
			AlertWindowView.infoAlert(collections.getActualItem().getTitleFileName() + " REMOVE!", "SUCCEED!",
					"Path: " + collections.getDestination());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onClickButtonHide() {
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

			// orchestrator.removeById(collectionForm, collections.getActualItem().getId());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadTable(CollectionItem item) {

		List<CollectionSubItem> listSubItens = item.getListSubItens();

		List<TableCollectionItemModel> tableItens = new ArrayList<>();
		if (tableItens.size() > 0) {
			tableItens.clear();
		}
		try {
			listSubItens.sort(new ItemFileComparator());
			for (CollectionSubItem sub : listSubItens) {
				CustomLogger.log(sub.getName());
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			// TODO: handle exception
		}
		String btnText = "";

		if (item.getCategory() == Category.ANIME) {
			btnText = btnWatch.getText();
		} else if (item.getCategory() == Category.MANGA) {
			btnText = btnRead.getText();
		}

		for (CollectionSubItem subItem : listSubItens) {
			Button btn = new Button();
			btn.setText(btnText);
			btn.setOnAction(event -> {
				try {
					DirectoryModel.openDirectory(subItem.getDestination());
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
							// TODO: handle exception
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

	public CollectionItem getTitle() {
		return title;
	}

}
