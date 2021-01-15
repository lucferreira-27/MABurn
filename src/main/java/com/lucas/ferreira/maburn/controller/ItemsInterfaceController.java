package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.CollectionMatch;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.databases.Database;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.ItemCreater;
import com.lucas.ferreira.maburn.model.itens.MangaItemCreate;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.comparator.CollectionGridCellComparator;
import com.lucas.ferreira.maburn.view.ItemsInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.TitleInterfaceView;
import com.lucas.ferreira.maburn.view.TitleSearchInterfaceView;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ItemsInterfaceController implements Initializable {

	@FXML
	private StackPane collectionStackPane;

	@FXML
	private GridPane itensImagesGridPane;

	@FXML
	private ScrollPane itensImagesScroll;

	@FXML
	private AnchorPane collectionAnchorPane;

	@FXML
	private GridPane searchItemGridPane;

	@FXML
	private TextField txtSearchBar;

	@FXML
	private Button btnFilter;

	@FXML
	private Label lblLoad;

	@FXML
	private Button btnSearch;

	@FXML
	private Label lblSearch;

	@FXML
	private Label lblCollectionName;

	@FXML
	private ProgressIndicator loadGridPane;

	private ItemsInterfaceView itensView;
	private Collections collection;

	private String querry;
	private GridPaneTable searchTable = new GridPaneTable(7);

	private BooleanProperty emptyProperty = new SimpleBooleanProperty();
	private BooleanProperty searchModeProperty = new SimpleBooleanProperty(false);
	private StringProperty querryProperty = new SimpleStringProperty();

	public ItemsInterfaceController(ItemsInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.itensView = itensView;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initItensImagesScrollPane();

		if (itensView.getCollectionLoader() == null || itensView.getCollectionLoader().isDone()) {
			CustomLogger.log(loadGridPane.getStyleClass());
			loadGridPane.setVisible(false);
			lblLoad.setVisible(false);
		}
		if (itensView.getCollectionLoader() != null) {
			lblLoad.textProperty().bind(itensView.getCollectionLoader().messageProperty());

			itensView.getCollectionLoader().getConnectinoItemLength().addListener((obser, oldvalue, newvalue) -> {
				lblLoad.textProperty().unbind();
				Platform.runLater(() -> lblLoad.setText("[Fetch items: " + newvalue.toString() + " ]"));
				CustomLogger.log("[Fetch items: " + newvalue.toString() + " ]");

			});

			itensView.getCollectionLoader().getWriteItemLength().addListener((obser, oldvalue, newvalue) -> {
				Platform.runLater(() -> lblLoad.setText("[Write items: " + newvalue.toString() + " ]"));
				CustomLogger.log("[Write items: " + newvalue.toString() + " ]");

			});

			itensView.getCollectionLoader().setOnSucceeded((event) -> {

				Platform.runLater(() -> {
					lblLoad.setVisible(false);
					loadGridPane.setVisible(false);
				});
			});
		}
		onClickOnImageGridPane();
		onSearchBarType();

	}

	public void onClickOnImageGridPane() {

		itensImagesGridPane.setOnMouseClicked(event -> {

			if (event.getPickResult().getIntersectedNode().getParent() instanceof AnchorPane) {
				AnchorPane pane = (AnchorPane) event.getPickResult().getIntersectedNode().getParent();
				ImageView image = (ImageView) pane.getChildren().get(0);
				if (image.getUserData() instanceof CollectionItem) {

					CollectionItem item = (CollectionItem) image.getUserData();
					itensView.getCollections().setActualItem(item);
					if (!searchModeProperty.get()) {
						TitleInterfaceView titleView = new TitleInterfaceView(itensView);
						titleView.loadMainInterfaceFX();
					} else {
						TitleSearchInterfaceView titleView = new TitleSearchInterfaceView(itensView);
						titleView.loadMainInterfaceFX();
					}
				}
			}

		});
	}

	public void onSearchBarType() {

		emptyProperty.addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue) {
				hideSearchOption();
			}
		});

		querryProperty.addListener((obs, oldvalue, newvalue) -> {
			querry = newvalue;
			if (!searchModeProperty.get())
				reloadCollection(itensView.getGridTable());
			else
				reloadCollection(searchTable);
		});

		searchModeProperty.addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				if (newvalue)
					lblCollectionName.setText("Result");
				else
					lblCollectionName.setText(collection.getCategory() + " Collection");
			});
		});

		txtSearchBar.textProperty().bindBidirectional(querryProperty);

	}

	private void showSearchOption() {

		String search = LanguageReader.read("LABEL_SEARCH").replace("${value}", "\"" + querry + "\"");

		lblSearch.setText(search);
		lblSearch.setVisible(true);
		btnSearch.setVisible(true);
		searchItemGridPane.setVisible(true);
		itensImagesScroll.setContent(searchItemGridPane);
	}

	private void hideSearchOption() {
		Platform.runLater(() -> {
			lblSearch.setVisible(false);
			btnSearch.setVisible(false);
			searchItemGridPane.setVisible(false);

			itensImagesScroll.setContent(itensImagesGridPane);
		});
	}

	private void reloadCollection(GridPaneTable table) {
		List<CollectionItem> originalItens = new ArrayList<>();
		List<CollectionItem> mathItens = new ArrayList<>();

		GridPaneTable mathTable = new GridPaneTable(table.getColumnSize());

		table.getCells().forEach(cell -> originalItens.add((CollectionItem) cell.getUserData()));
		mathItens = CollectionMatch.locale(originalItens, querry);

		for (int i = 0; i < table.getCells().size(); i++) {
			CollectionItem item = (CollectionItem) table.getCells().get(i).getUserData();
			if (mathItens.contains(item)) {
				mathTable.add(table.getCells().get(i));
			}
		}
		if (mathItens.size() == 0) {

			emptyProperty().setValue(true);
			showSearchOption();
			replaceGridPaneTable(mathTable);
		} else if (mathItens.size() > 0) {
			if (emptyProperty.get()) {
				emptyProperty().setValue(false);
			}
			replaceGridPaneTable(mathTable);

		}
	}

	private void replaceGridPaneTable(GridPaneTable newTable) {

		List<GridPaneCell> cells = newTable.getCells();
		java.util.Collections.sort(cells, new CollectionGridCellComparator());
		itensImagesGridPane.getChildren().clear();
		for (int i = 0; i < cells.size(); i++) {
			GridPaneCell cell = cells.get(i);
			int c = GridPaneTable.getImagesGridPaneLastColumn(i, itensView.getGridTable().getColumnSize());
			int r = GridPaneTable.getImagesGridPaneLastRow(i, itensView.getGridTable().getColumnSize());

			cell.setColumn(c);
			cell.setRow(r);

			itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
		}

	}

	public void onClickFilter() {

		if (btnFilter.getText().equals("A-Z"))
			reverseImagesGridPane();
		else
			sortImagesGridPane();
	}

	public void onClickButtonSearch() {
		// TODO Auto-generated method stub
		List<CollectionItem> items = new ArrayList<CollectionItem>();
		new Thread(() -> {
			loadGridPane.setVisible(true);
			System.out.println("Search " + querry);
			Database database = new KitsuDatabase();
			database.readAll(querry, collection.getCategory()).forEach(data -> {

				switch (collection.getCategory()) {

				case ANIME:

					ItemCreater<AnimeDownloaded> animeCreator = new AnimeItemCreate((AnimeCollection) collection);
					items.add((CollectionItem) animeCreator.createSearchItem(data));
					break;
				case MANGA:
					ItemCreater<MangaDownloaded> mangaCreator = new MangaItemCreate((MangaCollection) collection);
					items.add((CollectionItem) mangaCreator.createSearchItem(data));
					break;

				default:
					break;
				}
			});

			if (!searchTable.getCells().isEmpty()) {
				searchTable.getCells().clear();
			}
			for (CollectionItem item : items) {

				ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);
				try {
					GridPaneCell cell = thumbnailLoader.onlineLoad();

					if (cell != null)
						searchTable.add(cell);
				} catch (ThumbnailLoadException e1) {
					// TODO Auto-generated catch block

					e1.printStackTrace();

					continue;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			searchModeProperty.set(true);
			Platform.runLater(() -> {
				reloadCollection(searchTable);

				txtSearchBar.clear();
				loadGridPane.setVisible(false);
			});

		}).start();

	}

	private void sortImagesGridPane() {
		Platform.runLater(() -> {
			GridPaneTable gridTable = itensView.getGridTable();

			List<GridPaneCell> cells = gridTable.getCells();
			java.util.Collections.sort(cells, new CollectionGridCellComparator());
			itensImagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);
				int c = GridPaneTable.getImagesGridPaneLastColumn(i, 7);
				int r = GridPaneTable.getImagesGridPaneLastRow(i, 7);

				cell.setColumn(c);
				cell.setRow(r);

				itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());

			}

			btnFilter.setText("A-Z");

		});
	}

	private void reverseImagesGridPane() {
		Platform.runLater(() -> {
			GridPaneTable gridTable = itensView.getGridTable();

			List<GridPaneCell> cells = gridTable.getCells();
			java.util.Collections.reverse(cells);
			itensImagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);

				int c = GridPaneTable.getImagesGridPaneLastColumn(i, 7);
				int r = GridPaneTable.getImagesGridPaneLastRow(i, 7);

				cell.setColumn(c);
				cell.setRow(r);

				itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());

			}

			btnFilter.setText("Z-A");

		});
	}

	private void initItensImagesScrollPane() {

		itensImagesScroll.setLayoutY(10);
		itensImagesScroll.setLayoutX(200);
		itensImagesScroll.setPrefViewportHeight(MainInterfaceView.getInstance().getRoot().getScene().getHeight() - 200);
		itensImagesScroll.setPannable(false);

		itensImagesScroll.widthProperty().addListener((obs, oldvalue, newvalue) -> {
			double imageWith = 168.75;
			double width = newvalue.doubleValue();

			double size = width / imageWith;
			if ((int) size <= 0) {
				size = 1;
			} else
				size = size > (int) size ? (int) size : (int) size + 1;

			itensView.getGridTable().setColumnSize((int) size);
			itensView.sortImagesGridPane();
		});
	}

	public void setCollection(Collections collections) {
		// TODO Auto-generated method stub

		this.collection = collections;

	}

	public BooleanProperty emptyProperty() {
		return emptyProperty;
	}

	public BooleanProperty searchModeProperty() {
		return searchModeProperty;
	}

}
