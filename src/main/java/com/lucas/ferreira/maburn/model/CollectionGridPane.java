package com.lucas.ferreira.maburn.model;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.effects.Card;
import com.lucas.ferreira.maburn.model.effects.NormalCard;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class CollectionGridPane {

	private GridPaneTable table = new GridPaneTable();
	private GridPane imagesGridPane = new GridPane();
	//private LoadArea loadArea = new LoadArea();
	private ScrollPane itemsImagesScroll;
	private DataFetcher dataFetcher;
	private Collections collections;
	private Category category;

	public CollectionGridPane(Category category, ScrollPane itemsImagesScroll) {
		// TODO Auto-generated constructor stub
		this.itemsImagesScroll = itemsImagesScroll;
	}

	public void build(Collections collection) {

		initItemsImagesScrollPane();

		addAllItemsInTable();
		sortGridPane();
	}

	public void reload() {

	}

	public void filter() {

	}

	private void dataFetcher() {
		dataFetcher = new DataFetcher(category);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(dataFetcher);
		executorService.shutdown();
	}

	private void addAllItemsInTable() {
		table.getCells().clear();
		for (CollectionItem item : collections.getItens()) {
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
	}

	private void sortGridPane() {

		Platform.runLater(() -> {
			GridPaneTable sortTable = new GridPaneTable(table.getColumnSize());

			List<GridPaneCell> cells = table.getCells();
			// java.util.Collections.sort(cells, new CollectionGridCellComparator());
			imagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);
				int c = GridPaneTable.getImagesGridPaneLastColumn(i, table.getColumnSize());
				int r = GridPaneTable.getImagesGridPaneLastRow(i, table.getColumnSize());

				cell.setColumn(c);
				cell.setRow(r);

				imagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());
				sortTable.add(cell);
			}

		});

	}
}
