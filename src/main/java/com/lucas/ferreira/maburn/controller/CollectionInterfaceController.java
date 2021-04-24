package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.CollectionFilter;
import com.lucas.ferreira.maburn.model.CollectionMatch;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.dao.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.dao.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.effects.Card;
import com.lucas.ferreira.maburn.model.effects.NormalCard;
import com.lucas.ferreira.maburn.model.effects.SearchCard;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.CollectionFilterType;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.items.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.ItemCreater;
import com.lucas.ferreira.maburn.model.items.MangaItemCreate;
import com.lucas.ferreira.maburn.model.loader.CollectionCheck;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.model.service.Database;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.MathUtil;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.util.comparator.CollectionGridCellComparator;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CollectionInterfaceController implements Initializable {

	@FXML
	private StackPane collectionStackPane;

	@FXML
	private GridPane itensImagesGridPane;

	@FXML
	private ScrollPane itemsImagesScroll;

	@FXML
	private AnchorPane collectionAnchorPane;

	@FXML
	private AnchorPane vboxLoadArea;
	@FXML
	private GridPane searchItemGridPane;

	@FXML
	private TextField txtSearchBar;


	@FXML
	private Label lblLoadDataBase;

	@FXML
	private Label lblLoadFolderCollectionRead;

	@FXML
	private Label lblLoadFolderItemRead;

	@FXML
	private Label lblPorcentage;

//	@FXML
//	private Label lblLoadDownlaodImage;

	@FXML
	private Button btnSearch;

	@FXML
	private Label lblSearch;

	@FXML
	private Label lblCollectionName;

	@FXML
	private Label lblPath;

	@FXML
	private ImageView loadImageLoadArea;

	@FXML
	private ImageView imgReload;

	@FXML
	private ImageView imgFilter;

	@FXML
	private ProgressBar pbReadProgress;
	@FXML
	private ProgressIndicator sortCollectionLoad;

	private HomeInterfaceController homeController;
	private Navigator navigator = new Navigator();
	private DataFetcher dataFetcher;
	private Collections collection;
	private DataFetcher collectionLoader;
	private CollectionFilter filter = new CollectionFilter();
	private CollectionCheck collectionCheck = new CollectionCheck();
	private Category category;
	private String querry;
	private GridPaneTable searchTable = new GridPaneTable();
	private GridPaneTable collectionTable = new GridPaneTable();
	private BooleanProperty emptyProperty = new SimpleBooleanProperty();
	private BooleanProperty searchModeProperty = new SimpleBooleanProperty(false);
	private BooleanProperty reverseModeProperty = new SimpleBooleanProperty(false);
	private StringProperty querryProperty = new SimpleStringProperty();

	private IntegerProperty propertyItemsTotal = new SimpleIntegerProperty();

	private BooleanProperty propertyFullLoaded = new SimpleBooleanProperty(false);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		homeController = (HomeInterfaceController) Navigator.getMapNavigator().get(Interfaces.HOME);
		if (homeController == null || homeController.getCategory() == null) {
			return;
		}
		propertyFullLoaded.set(false);

//		try {
//			if (fullLoadedProperty.get()) {
//				if (itensImagesGridPane.getChildren().size() == 0) {
//					if (homeController.getCategory() == Category.ANIME && animeCollectionTable.getCells().size() > 0) {
//						System.out.println("HomeController: " + homeController.getCategory());
//						System.out.println("Category: " + category);
//						System.out.println("AnimeCollectionTable: " + animeCollectionTable.getCells().size());
//						sortImagesGridPane(animeCollectionTable);
//					} else if (homeController.getCategory() == Category.MANGA
//							&& mangaCollectionTable.getCells().size() > 0) {
//						System.out.println("HomeController: " + homeController.getCategory());
//						System.out.println("Category: " + category);
//						System.out.println("MangaCollectionTable: " + mangaCollectionTable.getCells().size());
//						sortImagesGridPane(mangaCollectionTable);
//					} else
//						throw new Exception("Error in recover collection!");
//					onClickOnImageGridPane();
//					onSearchBarType();
//				}
//				return;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.err.println("Category: " + category + "\n HomeController Category: " + homeController.getCategory());
//			// e.printStackTrace();
//		}

		final ObservableList<Node> children = itensImagesGridPane.getChildren();

		children.addListener((InvalidationListener) o -> {
			int size = children.size();
			propertyItemsTotal.set(size);
		});

		imagesAndIconsSetter();

		category = homeController.getCategory();

		if (category == Category.ANIME)
			lblCollectionName.setText("Anime Collection");
		else if (category == Category.MANGA) {
			lblCollectionName.setText("Manga Collection");

		}

		itensImagesGridPane.visibleProperty().addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue) {
				showSortLoad();
			} else {
				hideSortLoad();
				propertyFullLoaded.set(true);
			}
		});

		if (collectionCheck.hasNewItemCollecetion(homeController.getCategory())) {
			showLoadArea();
		}
		dataFetcher();

		initItensImagesScrollPane();

		if (dataFetcher != null) {
			collectionLoader = dataFetcher;
			bindCollectionLoaderProperties();
		} else {
			System.out.println("NULL DATAFETCHER!");
		}

		onClickOnImageGridPane();
		onSearchBarType();

	}

	private void imagesAndIconsSetter() {
		loadImageLoadArea.setImage(new Image(Resources.getResourceAsStream("icons/load_collection_icon.png")));
		reloadIcon();
		filterIcon();
	}

	private void reloadIcon() {

		
		Image imgSynchWhite = new Image(Resources.getResourceAsStream("icons/sync_white.png"));
		Image imgSynchRed = new Image(Resources.getResourceAsStream("icons/sync_red.png"));

		
		imgReload.setImage(imgSynchWhite);
		imgReload.setUserData(false);
		imgReload.hoverProperty().addListener((o -> {
			if (!(Boolean) imgReload.getUserData())
				if (imgReload.isHover()) {
					imgReload.setImage(imgSynchRed);
				} else {
					imgReload.setImage(imgSynchWhite);

				}
		}));
		imgReload.setOnMouseClicked(event -> {

			imgReload.setUserData(true);
			imgReload.setImage(imgSynchRed);
			imgReload.setRotate(30);
			reloadCollection();

			sortCollectionLoad.visibleProperty().addListener((obs, oldvalue, newvalue) -> {
				if (!newvalue) {
					imgReload.setRotate(0);
					imgReload.setImage(imgSynchWhite);
					imgReload.setUserData(false);
				}
			});

		});
	}

	private void filterIcon() {
		
	    final ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOnAction(e -> System.out.print(((MenuItem)e.getTarget()).getText()));
	   
	     List<MenuItem> items = Arrays.asList(CollectionFilterType.values()).stream().map(f -> new MenuItem(f.name())).collect(Collectors.toList());
	    
		contextMenu.getItems().addAll(items);
		
		imgFilter.setOnContextMenuRequested(event -> {
			contextMenu.show(imgFilter, event.getScreenX(), event.getScreenY());
		});
		Image imgFilterWhite = new Image(Resources.getResourceAsStream("icons/filter_white.png"));
		Image imgFilterRed = new Image(Resources.getResourceAsStream("icons/filter_red.png"));
		
		
		
		imgFilter.setImage(imgFilterWhite);
		imgFilter.setUserData(false);

		imgFilter.setOnMouseClicked(event -> {
			
			if(event.getButton() == MouseButton.SECONDARY) {
				return;
			}
			
			if (!(Boolean) imgFilter.getUserData()) {
				imgFilter.setUserData(true);
				imgFilter.setImage(imgFilterRed);
				onClickFilter();
			} else {
				imgFilter.setImage(imgFilterWhite);
				imgFilter.setUserData(false);
				onClickFilter();

			}
		});

	}

	private void dataFetcher() {

		itensImagesGridPane.setVisible(false);

		Category category = homeController.getCategory();
		dataFetcher = new DataFetcher(category);

		dataFetcher.stateProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == State.SUCCEEDED) {
				itensImagesGridPane.setVisible(true);
				defaultFilter();
			}
		});
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(dataFetcher);
		executorService.shutdown();
	}

	private void bindCollectionLoaderProperties() {

		if (collectionLoader.getDataFetcherDoneProperty().get()) {
			imagesGridPaneSetup();
		}

		Platform.runLater(() -> {
			txtSearchBar.setEditable(false);
			txtSearchBar.setPromptText("Type here ...");
		});
		lblLoadDataBase.textProperty().bind(collectionLoader.getLblLoadDataBase());
		lblPath.textProperty().bind(collectionLoader.getLblLoadFolderCollectionRead());
		lblLoadFolderItemRead.textProperty().bind(collectionLoader.getLblLoadFolderItemRead());
		pbReadProgress.progressProperty().bind(collectionLoader.getReadProgressProperty());

		pbReadProgress.progressProperty().addListener((obs, oldvalue, newvalue) -> {
			lblPorcentage.setText(String.valueOf(MathUtil.roundDouble(newvalue.doubleValue(), 1) * 100));
		});
		collectionLoader.getReadProgressProperty().addListener((obs, oldvalue, newvalue) -> {
			System.out.println("N: " + newvalue.doubleValue());
		});
		collectionLoader.getDataFetcherDoneProperty().addListener((obs, oldvalue, newvalue) -> {
			imagesGridPaneSetup();
		});

	}

	private void imagesGridPaneSetup() {
		collection = collectionLoader.getCollections();
		tableSetter();
		hideLoadArea();
		defaultFilter();
	}

	private void tableSetter() {
		addAllItemsInTable();
		// firstImagesGridPaneSort(collectionTable);
		sortImagesGridPane(collectionTable);

	}

	private void defaultFilter() {
		Platform.runLater(() -> filter.filter(collectionTable, itensImagesGridPane, CollectionFilterType.ASC));
	}

	private void showLoadArea() {
		vboxLoadArea.setVisible(true);
		lblLoadDataBase.setVisible(true);
		loadImageLoadArea.setVisible(true);
	}

	private void showSortLoad() {
		sortCollectionLoad.setVisible(true);

	}

	private void hideSortLoad() {
		sortCollectionLoad.setVisible(false);

	}

	private void hideLoadArea() {
		Platform.runLater(() -> {
			vboxLoadArea.setVisible(false);
			lblLoadDataBase.setVisible(false);
			// lblLoadFolderCollectionRead.setVisible(false);
			lblLoadFolderItemRead.setVisible(false);
			loadImageLoadArea.setVisible(false);
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
						navigator.open(Interfaces.TITLE);
					} else {
						navigator.open(Interfaces.TITLE_SEARCH);
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
				else {
					if (category == Category.ANIME)
						lblCollectionName.setText("Anime Collection");
					else if (category == Category.MANGA) {
						lblCollectionName.setText("Manga Collection");

					}
					sortImagesGridPane(collectionTable);
				}
			});
		});

		reverseModeProperty.addListener((obs, oldvalue, newvalue) -> {

			CustomLogger.log("Filter: " + newvalue);

		});

		txtSearchBar.textProperty().bindBidirectional(querryProperty);
		txtSearchBar.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				onClickButtonSearch();
			}
		});
	}

	private void showSearchOption() {

		String search = LanguageReader.read("LABEL_SEARCH").replace("${value}", "\"" + querry + "\"");

		lblSearch.setText(search);
		lblSearch.setVisible(true);
		btnSearch.setVisible(true);
		searchItemGridPane.setVisible(true);
		itemsImagesScroll.setContent(searchItemGridPane);
	}

	private void hideSearchOption() {
		Platform.runLater(() -> {
			lblSearch.setVisible(false);
			btnSearch.setVisible(false);
			searchItemGridPane.setVisible(false);
			itemsImagesScroll.setContent(itensImagesGridPane);
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

		collection.getItens().sort((n1, n2) -> {
			return n1.getTitleDataBase().compareTo(n2.getTitleDataBase());
		});
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
			Card card = new NormalCard(cell);
			collectionTable.add(cell);
			Platform.runLater(() -> {
				card.overlay();
			});
		}
	}

	@FXML
	public void onClickFilter() {

		if (filter.propertyActiveFilter().get() == CollectionFilterType.DESC) {
		//	btnFilter.setText("Z-A");
			filter.filter(collectionTable, itensImagesGridPane, CollectionFilterType.ASC);
			reverseModeProperty.set(true);
		} else if (filter.propertyActiveFilter().get() == CollectionFilterType.ASC) {
		//	btnFilter.setText("A-Z");
			filter.filter(collectionTable, itensImagesGridPane, CollectionFilterType.DESC);
			reverseModeProperty.set(false);
		}

	}

	public void reloadCollection() {
		if (dataFetcher.isRunning()) {
			CustomLogger.log("AN OTHER RELOAD IS RUNNING!");
			return;
		}
		searchModeProperty.set(false);
		dataFetcher();
	}

	public void onClickButtonSearch() {
		// TODO Auto-generated method stub
		List<CollectionItem> items = new ArrayList<CollectionItem>();
		new Thread(() -> {
			showSortLoad();
			loadImageLoadArea.setVisible(true);
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
						Card card = new SearchCard(cell);
						card.overlay();
						cell.getNode().setUserData(cell);
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
				loadImageLoadArea.setVisible(false);
				hideSortLoad();

			});

		}).start();

	}

//	private void firstImagesGridPaneSort(GridPaneTable table) {
//		try {
//
//			List<GridPaneCell> cells = table.getCells();
//
//			Platform.runLater(() -> {
//				itensImagesGridPane.getChildren().clear();
//			});
//
//			for (int i = 0; i < cells.size(); i++) {
//				AddCellInGridPane(cells, i, 100);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//		if (filter.getActiveFilter() == null) {
//
//			filter.filter(collectionTable, itensImagesGridPane, CollectionFilterType.ASC);
//
//		}
//
//	}

	private void sortImagesGridPane(GridPaneTable table) {
		Platform.runLater(() -> {

			try {

				List<GridPaneCell> cells = table.getCells();

				itensImagesGridPane.getChildren().clear();

				for (int i = 0; i < cells.size(); i++) {
					AddCellInGridPane(cells, i, -1);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			if (filter.propertyActiveFilter().get() == null) {

				filter.filter(collectionTable, itensImagesGridPane, CollectionFilterType.ASC);

			}
		});
	}

	private void AddCellInGridPane(List<GridPaneCell> cells, int i, long waitTime) {
		GridPaneCell cell = cells.get(i);
		int c = GridPaneTable.getImagesGridPaneLastColumn(i, collectionTable.getColumnSize());
		int r = GridPaneTable.getImagesGridPaneLastRow(i, collectionTable.getColumnSize());
		cell.setColumn(c);
		cell.setRow(r);
		cell.getNode().setUserData(cell);
		if (waitTime == -1)
			itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
		else {
			Platform.runLater(() -> itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow()));
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initItensImagesScrollPane() {
		itemsImagesScroll.setLayoutY(10);
		itemsImagesScroll.setLayoutX(200);
		itemsImagesScroll.setPrefViewportHeight(MainInterfaceView.getInstance().getRoot().getScene().getHeight() - 150);
		itemsImagesScroll.setPannable(false);

		itemsImagesScroll.widthProperty().addListener((obs, oldvalue, newvalue) -> {
			double imageWith = 168.75;
			double width = newvalue.doubleValue();
			double size = width / imageWith;
			if ((int) size <= 0) {
				size = 1;
			} else
				size = size > (int) size ? (int) size : (int) size + 1;

			collectionTable.setColumnSize((int) size);
			if (!searchModeProperty.get())
				sortImagesGridPane(collectionTable);
			else {
				sortImagesGridPane(searchTable);
			}
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

	public GridPane getItensImagesGridPane() {
		// TODO Auto-generated method stub
		return itensImagesGridPane;
	}

	public BooleanProperty propertyFullLoaded() {
		return propertyFullLoaded;
	}

	public IntegerProperty propertyItemTotal() {
		return propertyItemsTotal;
	}

	public ObjectProperty<CollectionFilterType> propertyFilter() {
		return null;
	}

	public Category getCategory() {
		return category;
	}

	public CollectionFilter getFilter() {
		return filter;
	}

//	public ItemsInterfaceView getItensView() {
//		return itensView;
//	}
}
