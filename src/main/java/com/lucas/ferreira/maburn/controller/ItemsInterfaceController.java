package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.CollectionMatch;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.effects.Card;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.items.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.ItemCreater;
import com.lucas.ferreira.maburn.model.items.MangaItemCreate;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.model.service.Database;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
	private VBox vboxLoadArea;
	@FXML
	private GridPane searchItemGridPane;

	@FXML
	private TextField txtSearchBar;

	@FXML
	private Button btnFilter;

	@FXML
	private Label lblLoadDataBase;
	@FXML
	private Label lblLoadFolderCollectionRead;
	@FXML
	private Label lblLoadFolderItemRead;
	@FXML
	private Label lblLoadDownlaodImage;

	@FXML
	private Button btnSearch;

	@FXML
	private Label lblSearch;

	@FXML
	private Label lblCollectionName;

	@FXML
	private ProgressIndicator loadGridPane;
	@FXML
	private ProgressBar pbReadProgress;
	
	private ItemsInterfaceView itensView;
	private Collections collection;
	private DataFetcher collectionLoader;

	private String querry;
	private GridPaneTable searchTable = new GridPaneTable();
	private GridPaneTable collectionTable = new GridPaneTable();

	private BooleanProperty emptyProperty = new SimpleBooleanProperty();
	private BooleanProperty searchModeProperty = new SimpleBooleanProperty(false);
	private BooleanProperty reverseModeProperty = new SimpleBooleanProperty(false);

	private StringProperty querryProperty = new SimpleStringProperty();

	public ItemsInterfaceController(ItemsInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.itensView = itensView;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Ok!");
		initItensImagesScrollPane();

//		if (itensView.getCollectionLoader() != null) {
//			collectionLoader = itensView.getCollectionLoader();
//			bindCollectionLoaderProperties();
//		}
//		if (collectionLoader != null && collectionLoader.isDone()) {
//			loadGridPane.setVisible(false);
//			lblLoad.setVisible(false);
//		}

		if (itensView.getDataFetcher() != null) {
			collectionLoader = itensView.getDataFetcher();
			bindCollectionLoaderProperties();
		}
//		if (collectionLoader != null && collectionLoader.isDone()) {
//			loadGridPane.setVisible(false);
//			lblLoad.setVisible(false);
//		}

		onClickOnImageGridPane();
		onSearchBarType();

	}

	private void bindCollectionLoaderProperties() {

		Platform.runLater(() -> {
			// vboxLoadArea.setDisable(false);
			showLoadArea();
			txtSearchBar.setEditable(false);
			txtSearchBar.setPromptText("Type here ...");
		});
		lblLoadDataBase.textProperty().bind(collectionLoader.getLblLoadDataBase());
		lblLoadFolderCollectionRead.textProperty().bind(collectionLoader.getLblLoadFolderCollectionRead());
		lblLoadFolderItemRead.textProperty().bind(collectionLoader.getLblLoadFolderItemRead());
		collectionLoader.getReadProgressProperty().addListener((obs, oldvalue, newvalue) ->{
			System.out.println("N: " + newvalue.doubleValue());
		});
		pbReadProgress.progressProperty().bind(collectionLoader.getReadProgressProperty());
		try {

			collectionLoader.getDataFetcherDoneProperty().addListener((obs, oldvalue, newvalue) -> {

				collection = collectionLoader.getCollections();

				addAllItemsInTable();
				sortImagesGridPane();
				hideLoadArea();

			});
			if (collectionLoader.getDataFetcherDoneProperty().get()) {
				collection = collectionLoader.getCollections();

				addAllItemsInTable();
				sortImagesGridPane();
				hideLoadArea();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void showLoadArea() {
		vboxLoadArea.setVisible(true);
		lblLoadDataBase.setVisible(true);
		loadGridPane.setVisible(true);
	}

	private void hideLoadArea() {
		Platform.runLater(() -> {
			vboxLoadArea.setVisible(false);
			lblLoadDataBase.setVisible(false);
			lblLoadFolderCollectionRead.setVisible(false);
			lblLoadFolderItemRead.setVisible(false);
			loadGridPane.setVisible(false);
			txtSearchBar.setEditable(true);

		});
	}

	public void onClickOnImageGridPane() {
		itensImagesGridPane.setOnMouseClicked(event -> {

			if (event.getPickResult().getIntersectedNode().getParent() instanceof AnchorPane) {
				AnchorPane pane = (AnchorPane) event.getPickResult().getIntersectedNode().getParent();
				ImageView image = (ImageView) pane.getChildren().get(0);
				if (image.getUserData() instanceof CollectionItem) {

					CollectionItem item = (CollectionItem) image.getUserData();
					collection.setActualItem(item);
					if (!searchModeProperty.get()) {
						TitleInterfaceView titleView = new TitleInterfaceView(this);
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
				reloadTable(collectionTable);
			else
				reloadTable(searchTable);
		});

		searchModeProperty.addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				if (newvalue)
					lblCollectionName.setText("Result");
				else
					lblCollectionName.setText("Collection");
			});
		});
		reverseModeProperty.addListener((obs, oldvalue, newvalue) -> {

			CustomLogger.log("Filter: " + newvalue);

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

	private void reloadTable(GridPaneTable table) {
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
		} else if (mathItens.size() > 0) {
			if (emptyProperty.get()) {
				emptyProperty().setValue(false);
			}
			replaceTable(mathTable);

		}
	}

	private void replaceTable(GridPaneTable newTable) {

		List<GridPaneCell> cells = newTable.getCells();
		java.util.Collections.sort(cells, new CollectionGridCellComparator());
		itensImagesGridPane.getChildren().clear();
		for (int i = 0; i < cells.size(); i++) {
			GridPaneCell cell = cells.get(i);
			int c = GridPaneTable.getImagesGridPaneLastColumn(i, collectionTable.getColumnSize());
			int r = GridPaneTable.getImagesGridPaneLastRow(i, collectionTable.getColumnSize());

			cell.setColumn(c);
			cell.setRow(r);

			itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
		}

	}

	private void addAllItemsInTable() {
		collectionTable.getCells().clear();
		if (collection.getItens().size() > 0)
			for (CollectionItem item : collection.getItens()) {
				try {
					addItemInTable(item);
				} catch (ThumbnailLoadException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

	}

	private void addItemInTable(CollectionItem item) throws ThumbnailLoadException {

		ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);

		GridPaneCell cell = thumbnailLoader.downloadLoad();
		if (cell != null) {
			Card card = new Card(cell);
			collectionTable.add(cell);
			Platform.runLater(() -> card.overlay());
		}
	}

	@FXML
	public void onClickFilter() {

		if (btnFilter.getText().equals("A-Z")) {
			btnFilter.setText("Z-A");
			reverseModeProperty.set(true);
		} else {
			btnFilter.setText("A-Z");
			reverseModeProperty.set(false);
		}
		reverseImagesGridPane();

	}

	@FXML
	public void onClickReload() {
		DataFetcher data = new DataFetcher(collection.getCategory());
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(data);
		bindCollectionLoaderProperties();

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

					if (cell != null) {
						Card card = new Card(cell);
						card.overlay();
						searchTable.add(cell);

					}
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
				reloadTable(searchTable);

				txtSearchBar.clear();
				loadGridPane.setVisible(false);
			});

		}).start();

	}

	private void sortImagesGridPane() {
		searchModeProperty.set(false);
		Platform.runLater(() -> {
			GridPaneTable sortTable = new GridPaneTable(collectionTable.getColumnSize());

			List<GridPaneCell> cells = collectionTable.getCells();
			// java.util.Collections.sort(cells, new CollectionGridCellComparator());
			itensImagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);
				int c = GridPaneTable.getImagesGridPaneLastColumn(i, collectionTable.getColumnSize());
				int r = GridPaneTable.getImagesGridPaneLastRow(i, collectionTable.getColumnSize());

				cell.setColumn(c);
				cell.setRow(r);

				itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
				sortTable.add(cell);
			}

		});
	}

	private void reverseImagesGridPane() {
		if (!reverseModeProperty.get())
			reverseModeProperty.set(true);
		else
			reverseModeProperty.set(false);
		Platform.runLater(() -> {

			List<GridPaneCell> cells = null;
			if (!searchModeProperty.get())

				cells = collectionTable.getCells();
			else {

				cells = searchTable.getCells();

			}
			java.util.Collections.reverse(cells);
			itensImagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);

				int c = GridPaneTable.getImagesGridPaneLastColumn(i, collectionTable.getColumnSize());
				int r = GridPaneTable.getImagesGridPaneLastRow(i, collectionTable.getColumnSize());

				cell.setColumn(c);
				cell.setRow(r);

				itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());

			}

		});
	}

	private void initItensImagesScrollPane() {

		itensImagesScroll.setLayoutY(10);
		itensImagesScroll.setLayoutX(200);
		itensImagesScroll.setPrefViewportHeight(MainInterfaceView.getInstance().getRoot().getScene().getHeight() - 150);
		itensImagesScroll.setPannable(false);

		itensImagesScroll.widthProperty().addListener((obs, oldvalue, newvalue) -> {
			double imageWith = 168.75;
			double width = newvalue.doubleValue();

			double size = width / imageWith;
			if ((int) size <= 0) {
				size = 1;
			} else
				size = size > (int) size ? (int) size : (int) size + 1;

			collectionTable.setColumnSize((int) size);
			sortImagesGridPane();
		});
	}

	public BooleanProperty emptyProperty() {
		return emptyProperty;
	}

	public BooleanProperty reverseModeProperty() {
		return reverseModeProperty;
	}

	public BooleanProperty searchModeProperty() {
		return searchModeProperty;
	}

	public Collections getCollection() {
		return collection;
	}

	public ItemsInterfaceView getItensView() {
		return itensView;
	}
}
