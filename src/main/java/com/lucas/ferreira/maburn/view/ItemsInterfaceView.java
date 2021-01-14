package com.lucas.ferreira.maburn.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.lucas.ferreira.maburn.controller.ItemsInterfaceController;
import com.lucas.ferreira.maburn.exceptions.LoadingException;
import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.LoadingType;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.comparator.CollectionGridCellComparator;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ItemsInterfaceView extends ViewInterface {

	private Pane root;
	private GridPaneTable gridTable = new GridPaneTable(7);
	private GridPane itensImagesGridPane;
	private ItemsInterfaceController controller;
	private CollectionLoader futureCollections;
	private Collections collections;
	private Map<String, Object> namespace;

	public ItemsInterfaceView(CollectionLoader futureCollections) {
		this.futureCollections = futureCollections;

	}

	public ItemsInterfaceView(Collections collections) {
		this.collections = collections;
	}

	@Override
	public void loadMainInterfaceFX() {
		// TODO Auto-generated method stub
		this.namespace = null;
	
		this.root = MainInterfaceView.getInstance().getRoot();
		new Thread(() -> {

			remove(root); // Removes the previous nodes.
			initFX(); // Initializes interface.

		}).start();

	}

	private void showLoading(LoadingType loading) throws LoadingException {

		switch (loading) {
		case COLLECTION:
			collectionLoading();
			CustomLogger.log("Collection Loading DONE!");

			break;
		case FILTER:
			filterLoading();
			CustomLogger.log("Filter Loading DONE!");
			break;
		default:
			break;
		}

	}

	private void collectionLoading() throws LoadingException {
		try {
			CustomLogger.log("Future collection");

			if (collections == null) {

				collections = (Collections) futureCollections.get();
				collections.getItens()
						.sort((item1, item2) -> item1.getTitleDataBase().compareTo(item2.getTitleDataBase()));

			}
			System.out.println("collectionLoading: " + collections);
			controller.setCollection(collections);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LoadingException("No itens found in collection");

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LoadingException("No itens found in collection");
		}
	}

	private void filterLoading() {
		new Thread(() -> {
			while (collections == null || itensImagesGridPane == null
					|| gridTable.getCells().size() < collections.getItens().size()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			Platform.runLater(() -> {
				sortImagesGridPane();
				// root.getChildren().remove(pane);
			});
		}).start();
	}

	private void initFX() {
		CustomLogger.log("> Run ItensInterfaceView");
		Platform.runLater(() -> {


			initFXMLLoader(controller, root, "ItensViewFXML.fxml");

		});

		while (namespace == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			showLoading(LoadingType.COLLECTION);
		} catch (LoadingException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		Platform.runLater(() -> {

			initItensImagesPane();
			addAllNodes();
			CustomLogger.log("> Complete ItensIntGerfaceView");
		});

		showLoading(LoadingType.FILTER);

	}

	@Override
	protected void initFXMLLoader(Initializable initializable, Pane root, String fxml) {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("ItensViewFXML.fxml"));
		controller = new ItemsInterfaceController(this);
		loader.setController(controller);
		try {
			root = loader.<VBox>load();
			namespace = loader.getNamespace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initItensImagesPane() {

		itensImagesGridPane = (GridPane) namespace.get("itensImagesGridPane");

	}

	private void addAllNodes() {

		if (gridTable.getCells().size() == 0)
			addAllImageViewInImageGrid();

	}

	private void addAllImageViewInImageGrid() {

		// Create a thread for each item
		for (CollectionItem item : collections.getItens()) {

			ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);
			try {
				GridPaneCell cell = thumbnailLoader.call();
				if (cell != null)
					gridTable.add(cell);
			} catch (ThumbnailLoadException e1) {
				// TODO Auto-generated catch block

				e1.printStackTrace();

				continue;
			} catch (Exception e) {
				// TODO: handle exception

			}

		}

	}

	public void sortImagesGridPane() {
		try {
			List<GridPaneCell> cells = gridTable.getCells();
			if (cells.size() > 0) {
				controller.emptyProperty().setValue(true);
			}
			java.util.Collections.sort(cells, new CollectionGridCellComparator());

			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);

				int c = GridPaneTable.getImagesGridPaneLastColumn(i, gridTable.getColumnSize());
				int r = GridPaneTable.getImagesGridPaneLastRow(i, gridTable.getColumnSize());

				cell.setColumn(c);
				cell.setRow(r);
				if (itensImagesGridPane.getChildren().contains(cell.getNode()))
					itensImagesGridPane.getChildren().remove(cell.getNode());
				itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}





	public GridPaneTable getGridTable() {
		return gridTable;
	}

	public Collections getCollections() {
		return collections;
	}

	public void setCollections(Collections collections) {
		this.collections = collections;
	}

	public CollectionLoader getCollectionLoader() {
		return futureCollections;
	}

}
