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
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionCheck;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.comparator.CollectionGridCellComparator;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class CollectionGridPane {

	private GridPaneTable table = new GridPaneTable();
	private GridPaneTable tableSearch = new GridPaneTable();
	private GridPane imagesGridPane = new GridPane();
	// private LoadArea loadArea = new LoadArea();
	private ScrollPane itemsImagesScroll;
	private DataFetcher dataFetcher = new DataFetcher();
	private CollectionCheck collectionCheck = new CollectionCheck();
	private CollectionFilter filter = new CollectionFilter();

	private Collections collection;
	private Category category;
	private StringProperty queryProperty;
	private ObjectProperty<CollectionStatus> propertyStatus = new SimpleObjectProperty<CollectionStatus>();
	private BooleanProperty propertyEmptyCollection = new SimpleBooleanProperty();
	private BooleanProperty propertyFullLoaded = new SimpleBooleanProperty(false);

	private CollectionLoadArea collectionLoadArea;

	public CollectionGridPane() {
		// TODO Auto-generated constructor stub

	}

	public void build(Category category, GridPane imagesGridPane, ScrollPane itemsImagesScroll,
			CollectionLoadArea collectionLoadArea, StringProperty query) {
		this.itemsImagesScroll = itemsImagesScroll;
		this.imagesGridPane = imagesGridPane;
		this.collectionLoadArea = collectionLoadArea;
		this.category = category;
		this.queryProperty = query;

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
				swichTableLocal(table,querry);
				return;
			}
			if (propertyStatus.get() == CollectionStatus.COLLECTION_SEARCH) {
				swichTableLocal(tableSearch,querry);
				return;
			}
			

		});
		
		

		if (collectionCheck.hasNewItemCollecetion(category)) {
			collectionLoadArea.showArea();
		}

		dataFetcher();

		initItemsImagesScrollPane();

		if (dataFetcher.getDataFetcherDoneProperty().get()) {
			imagesGridPaneSetup();
		}

		dataFetcher.getDataFetcherDoneProperty().addListener((obs, oldvalue, newvalue) -> {
			imagesGridPaneSetup();
		});

	}

	public void rebuild(Collections collection) {

		dataFetcher();

		if (dataFetcher.getDataFetcherDoneProperty().get()) {
			imagesGridPaneSetup();
		}
		dataFetcher.getDataFetcherDoneProperty().addListener((o) -> {
			imagesGridPaneSetup();
		});

		initItemsImagesScrollPane();

	}

	private void imagesGridPaneSetup() {
		collection = dataFetcher.getCollections();
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
		if (dataFetcher.isRunning()) {
			CustomLogger.log("AN OTHER RELOAD IS RUNNING!");
			return;
		}
		propertyStatus.set(CollectionStatus.COLLECTION_LOCAL);
		dataFetcher();
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
		imagesGridPane.setVisible(false);
		

		dataFetcher.stateProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue == State.SUCCEEDED) {
				imagesGridPane.setVisible(true);
				defaultFilter();
			}
		});
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(dataFetcher);
		executorService.shutdown();
	}

	private void addAllItemsInTable() {
		System.out.println("addAllItemsInTable");
		collection.getItens().sort((n1, n2) -> {
			return n1.getTitleDataBase().compareTo(n2.getTitleDataBase());
		});
		table.getCells().clear();
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
			if (propertyStatus.get() == CollectionStatus.COLLECTION_SEARCH) {
				cells = tableSearch.getCells();

			}
			// java.util.Collections.sort(cells, new CollectionGridCellComparator());
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
		List<CollectionItem> mathItens = new ArrayList<>();

		GridPaneTable mathTable = new GridPaneTable(table.getColumnSize());

		table.getCells().stream().forEach(cell -> mathItens.add((CollectionItem) cell.getUserData()));

		for (int i = 0; i < table.getCells().size(); i++) {
			CollectionItem item = (CollectionItem) table.getCells().get(i).getUserData();
			if (mathItens.contains(item)) {
				mathTable.add(table.getCells().get(i));
			}
		}

		System.out.println("Search Math Itens: " + mathItens.size());

		if (mathItens.size() == 0) {
			propertyEmptyCollection.setValue(true);
		} else if (mathItens.size() > 0) {
			propertyEmptyCollection.set(false);
			tableSearch = mathTable;

			replaceTable(mathTable);

		}
	}

	public void swichTableLocal(GridPaneTable table, String querry) {
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

		System.out.println("Querry Math Itens: " + mathItens.size());

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

	public ObjectProperty<CollectionStatus> getPropertyStatus() {
		return propertyStatus;
	}

	public DataFetcher getDataFetcher() {
		return dataFetcher;
	}

	public CollectionFilter getFilter() {
		return filter;
	}

	public Collections getCollection() {
		return collection;
	}


}
