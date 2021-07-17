package com.lucas.ferreira.maburn.model.collections.management;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.effects.Card;
import com.lucas.ferreira.maburn.model.effects.NormalCard;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.CollectionFilterType;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.loader.CollectionCheck;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.util.CustomLogger;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CollectionGridPane {

	private Navigator navigator = new Navigator();
	private GridPaneTable table = new GridPaneTable();
	private GridPaneTable tableSearch = new GridPaneTable();
	private GridPaneTable mathTable;
	private GridPane imagesGridPane = new GridPane();
	// private LoadArea loadArea = new LoadArea();
	private ScrollPane itemsImagesScroll;
	private ObjectProperty<DataFetcher> propertyDataFetcher = new SimpleObjectProperty<DataFetcher>();
	private CollectionCheck collectionCheck = new CollectionCheck();
	private CollectionFilter filter = new CollectionFilter();

	private Collections collection;
	private Category category;
	private StringProperty queryProperty;
	private ObjectProperty<CollectionStatus> propertyStatus = new SimpleObjectProperty<CollectionStatus>();
	private BooleanProperty propertyEmptyCollection = new SimpleBooleanProperty();
	private BooleanProperty propertyFullLoaded = new SimpleBooleanProperty(false);

	private CollectionLoadArea collectionLoadArea;
	private IntegerProperty propertyItemsTotal = new SimpleIntegerProperty();

	public CollectionGridPane() {
		

	}

	public void build(Category category, GridPane imagesGridPane, ScrollPane itemsImagesScroll,
			CollectionLoadArea collectionLoadArea, StringProperty query) {
		this.itemsImagesScroll = itemsImagesScroll;
		this.imagesGridPane = imagesGridPane;
		this.collectionLoadArea = collectionLoadArea;
		this.category = category;
		this.queryProperty = query;

		build();

	}

	public void build() {
		propertyDataFetcher.set(new DataFetcher());

		propertyStatus.set(CollectionStatus.COLLECTION_LOCAL);

		propertyFullLoaded.set(false);
		imagesGridPane.visibleProperty().addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue) {
				collectionLoadArea.showQuickLoad();

			} else {
				collectionLoadArea.hideQuickLoad();
			}
		});

		queryProperty.addListener((obs, oldvalue, newvalue) -> {

			String querry = newvalue;
			if (propertyStatus.get() == CollectionStatus.COLLECTION_LOCAL) {
				swichTableLocal(table, querry);
				return;
			}
			if (propertyStatus.get() == CollectionStatus.COLLECTION_SEARCH) {
				swichTableLocal(tableSearch, querry);
				return;
			}

		});

		final ObservableList<Node> children = imagesGridPane.getChildren();

		children.addListener((InvalidationListener) o -> {
			int size = children.size();
			propertyItemsTotal.set(size);
		});

		if (collectionCheck.hasNewItemCollecetion(category)) {
			collectionLoadArea.showArea();
		}

		dataFetcher();

		initItemsImagesScrollPane();
		onClickOnImageGridPane();
		DataFetcher dataFetcher = propertyDataFetcher.get();
		if (dataFetcher.getDataFetcherDoneProperty().get()) {
			imagesGridPaneSetup();
		}

		dataFetcher.getDataFetcherDoneProperty().addListener((obs, oldvalue, newvalue) -> {
			imagesGridPaneSetup();
		});
	}

	public void rebuild() {

		imagesGridPaneSetup();

	}

	private void imagesGridPaneSetup() {
		collection = propertyDataFetcher.get().getCollections();
		tableSetter();
		collectionLoadArea.hideArea();
		propertyFullLoaded.set(true);

		// defaultFilter();
	}

	private void tableSetter() {
		addAllItemsInTable();
		sortGridPane();

	}

	private void defaultFilter() {
		Platform.runLater(() -> filter.filter(table, imagesGridPane, CollectionFilterType.ASC));
	}

	public void reload() {
		reloadCollection();
	}

	private void reloadCollection() {
		if (propertyDataFetcher.get().isRunning()) {
			CustomLogger.log("AN OTHER RELOAD IS RUNNING!");
			return;
		}
		build();
		// propertyStatus.set(CollectionStatus.COLLECTION_LOCAL);
//		dataFetcher();
	}

	public void filter() {
		if (filter.propertyActiveFilter().get() == CollectionFilterType.DESC) {
			// btnFilter.setText("Z-A");
			filter.filter(table, imagesGridPane, CollectionFilterType.ASC);
		} else if (filter.propertyActiveFilter().get() == CollectionFilterType.ASC) {
			// btnFilter.setText("A-Z");
			filter.filter(table, imagesGridPane, CollectionFilterType.DESC);
		}
	}

	private void dataFetcher() {
		propertyDataFetcher.set(new DataFetcher());
		DataFetcher dataFetcher = propertyDataFetcher.get();
		imagesGridPane.setVisible(false);
		dataFetcher.stateProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == State.SUCCEEDED) {
				imagesGridPane.setVisible(true);
				propertyFullLoaded.set(true);
				defaultFilter();
			}
		});
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(dataFetcher);
		executorService.shutdown();
	}

	private void onClickOnImageGridPane() {
		imagesGridPane.setOnMouseClicked(event -> {

			if (event.getPickResult().getIntersectedNode().getParent() instanceof AnchorPane) {
				AnchorPane pane = (AnchorPane) event.getPickResult().getIntersectedNode().getParent();
				ImageView image = (ImageView) pane.getChildren().get(0);
				if (image.getUserData() instanceof CollectionTitle) {

					CollectionTitle item = (CollectionTitle) image.getUserData();
					collection.setActualItem(item);
					if (propertyStatus.get() == CollectionStatus.COLLECTION_LOCAL) {
						navigator.open(Interfaces.TITLE);
						return;
					}
					if (propertyStatus.get() == CollectionStatus.COLLECTION_SEARCH) {
						navigator.open(Interfaces.TITLE_SEARCH);
						return;

					}
				}
			}

		});
	}

	private void addAllItemsInTable() {
		collection.getItens().sort((n1, n2) -> {
			return n1.getTitleDataBase().compareTo(n2.getTitleDataBase());
		});
		table.getCells().clear();
		if (collection.getItens().size() > 0)
			for (CollectionTitle item : collection.getItens()) {
				try {
					addItemInTable(item);
				} catch (ThumbnailLoadException e) {
					
					e.printStackTrace();
				}
			}

	}

	private void addItemInTable(CollectionTitle item) throws ThumbnailLoadException {

		ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);

		GridPaneCell cell = thumbnailLoader.downloadLoad();
		if (cell != null) {
			Card card = new NormalCard(cell);
			table.add(cell);
			Platform.runLater(() -> card.overlay());
		}
	}

	private void initItemsImagesScrollPane() {

		itemsImagesScroll.setLayoutY(10);
		itemsImagesScroll.setLayoutX(200);
		itemsImagesScroll.setPrefViewportHeight(MainInterfaceView.getInstance().getRoot().getScene().getHeight() - 150);
		itemsImagesScroll.setPannable(false);

		listenItemsImagesScrollWidth();
	}

	private void listenItemsImagesScrollWidth() {
		itemsImagesScroll.widthProperty().addListener((obs, oldvalue, newvalue) -> {
			changeColumnSize(newvalue);
		});
	}

	private void changeColumnSize(Number newvalue) {

		double imageWith = 168.75;
		double width = newvalue.doubleValue();

		double size = width / imageWith;
		if ((int) size <= 0) {
			size = 1;
		} else
			size = size > (int) size ? (int) size : (int) size + 1;

		table.setColumnSize((int) size);

		sortGridPane();

	}

	public void sortGridPane() {

		Platform.runLater(() -> {
			GridPaneTable sortTable = new GridPaneTable(table.getColumnSize());
			List<GridPaneCell> cells = null;

			if (propertyStatus.get() == CollectionStatus.COLLECTION_LOCAL)
				cells = table.getCells();
			else if (propertyStatus.get() == CollectionStatus.COLLECTION_SEARCH) {
				cells = tableSearch.getCells();
				java.util.Collections.sort(cells, new CollectionGridCellComparator());
			}

			imagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);
				int c = GridPaneTable.getImagesGridPaneLastColumn(i, table.getColumnSize());
				int r = GridPaneTable.getImagesGridPaneLastRow(i, table.getColumnSize());

				cell.setColumn(c);
				cell.setRow(r);
				cell.getNode().setUserData(cell);

				imagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
				sortTable.add(cell);
			}

		});

	}

	public void swichTableSearch(GridPaneTable table, String querry) {
		propertyStatus.set(CollectionStatus.COLLECTION_SEARCH);
		List<CollectionTitle> mathItens = new ArrayList<>();

		mathTable = new GridPaneTable(table.getColumnSize());

		table.getCells().stream().forEach(cell -> mathItens.add((CollectionTitle) cell.getUserData()));

		for (int i = 0; i < table.getCells().size(); i++) {
			CollectionTitle item = (CollectionTitle) table.getCells().get(i).getUserData();
			if (mathItens.contains(item)) {
				mathTable.add(table.getCells().get(i));
			}
		}

		

		if (mathItens.size() == 0) {
			propertyEmptyCollection.setValue(true);
		} else if (mathItens.size() > 0) {
			propertyEmptyCollection.set(false);
			tableSearch = mathTable;

			replaceTable(mathTable);

		}
	}

	public void swichTableLocal(GridPaneTable table, String querry) {
		List<CollectionTitle> originalItens = new ArrayList<>();
		List<CollectionTitle> mathItens = new ArrayList<>();

		mathTable = new GridPaneTable(table.getColumnSize());

		table.getCells().forEach(cell -> originalItens.add((CollectionTitle) cell.getUserData()));
		mathItens = CollectionMatch.locale(originalItens, querry);

		for (int i = 0; i < table.getCells().size(); i++) {
			CollectionTitle item = (CollectionTitle) table.getCells().get(i).getUserData();
			if (mathItens.contains(item)) {
				mathTable.add(table.getCells().get(i));
			}
		}

		if (mathItens.size() == 0) {
			propertyEmptyCollection.setValue(true);
		} else if (mathItens.size() > 0) {
			propertyEmptyCollection.set(false);
			replaceTable(mathTable);

		}
	}

	private void replaceTable(GridPaneTable newTable) {

		List<GridPaneCell> cells = newTable.getCells();
		java.util.Collections.sort(cells, new CollectionGridCellComparator());
		imagesGridPane.getChildren().clear();
		for (int i = 0; i < cells.size(); i++) {
			GridPaneCell cell = cells.get(i);
			int c = GridPaneTable.getImagesGridPaneLastColumn(i, table.getColumnSize());
			int r = GridPaneTable.getImagesGridPaneLastRow(i, table.getColumnSize());

			cell.setColumn(c);
			cell.setRow(r);

			imagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
		}

	}

	public BooleanProperty getPropertyCollectionEmpty() {
		return propertyEmptyCollection;
	}

	public BooleanProperty propertyFullLoaded() {
		return propertyFullLoaded;
	}

	public IntegerProperty propertyItemTotal() {
		return propertyItemsTotal;
	}

	public ObjectProperty<CollectionStatus> getPropertyStatus() {
		return propertyStatus;
	}

	public ObjectProperty<DataFetcher> propertyDataFetcher() {
		return propertyDataFetcher;
	};

	public CollectionFilter getFilter() {
		return filter;
	}

	public Collections getCollection() {
		return collection;
	}

	public GridPane getImagesGridPane() {
		return imagesGridPane;
	}

}
