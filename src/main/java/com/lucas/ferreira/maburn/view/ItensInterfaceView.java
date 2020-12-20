package com.lucas.ferreira.maburn.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.lucas.ferreira.maburn.controller.ItensInterfaceController;
import com.lucas.ferreira.maburn.exceptions.LoadingException;
import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.effects.TransformEffects;
import com.lucas.ferreira.maburn.model.effects.TransformImagesViewEffect;
import com.lucas.ferreira.maburn.model.effects.TransformPanelEffect;
import com.lucas.ferreira.maburn.model.enums.LoadingType;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
import com.lucas.ferreira.maburn.util.CollectionGridCellComparator;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ItensInterfaceView implements ViewInterface {

	private MainInterfaceView mainView;
	private Pane root;
	private GridPaneTable gridTable = new GridPaneTable(7);
	private GridPane itensImagesGridPane;
	private ItensInterfaceController controller;
	private CollectionLoader futureCollections;
	private Collections collections;
	private Map<String, Object> namespace;

	public ItensInterfaceView(CollectionLoader futureCollections) {
		this.futureCollections = futureCollections;
	}

	public ItensInterfaceView(Collections collections) {
		this.collections = collections;
	}

	@Override
	public void loadMainInterfaceFX(MainInterfaceView mainView) {
		// TODO Auto-generated method stub
		this.mainView = mainView;
		this.root = mainView.getRoot();

		new Thread(() -> {

			remove(); // Removes the previous nodes.
			initFX(); // Initializes interface.

		}).start();

	}

	private void showLoading(LoadingType loading) throws LoadingException {

		
		switch (loading) {
		case COLLECTION:
			collectionLoading();
			System.out.println("Collection Loading DONE!");

			break;
		case FILTER:
			filterLoading();
			System.out.println("Filter Loading DONE!");
			break;
		default:
			break;
		}

	}

	private void collectionLoading() throws LoadingException {
		try {

			if (collections == null) {

				System.out.println("Future collection");
				
				collections = (Collections) futureCollections.get(); 
				collections.getItens()
						.sort((item1, item2) -> item1.getTitleDataBase().compareTo(item2.getTitleDataBase()));

				controller.setCollection(collections);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new LoadingException("No itens found in collection");

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block

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

	private void remove() {
		Platform.runLater(() -> {
			// index start 1 to keep the menu node and removes the rest
			root.getChildren().remove(1, root.getChildren().size());

		});
	}

	private void initFX() {
		System.out.println("> Run ItensInterfaceView");
		Platform.runLater(() -> {

			initFXMLLoader();
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
			System.out.println("> Complete ItensIntGerfaceView");
		});

		showLoading(LoadingType.FILTER);

	}

	private void initFXMLLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource("ItensViewFXML.fxml"));
		controller = new ItensInterfaceController(mainView, this);
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
		ArrayList<Thread> threads = new ArrayList<>();

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
			}catch (Exception e) {
				// TODO: handle exception
			
			}


		}

		threads.forEach(thread -> thread.start());

	}

	private void sortImagesGridPane() {
		try {
			List<GridPaneCell> cells = gridTable.getCells();
			java.util.Collections.sort(cells, new CollectionGridCellComparator());

			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);

				int c = GridPaneTable.getImagesGridPaneLastColumn(i, 7);
				int r = GridPaneTable.getImagesGridPaneLastRow(i, 7);

				cell.setColumn(c);
				cell.setRow(r);

				itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void reverseImagesGridPane() {
		java.util.Collections.reverse(itensImagesGridPane.getChildren());
	}

	private ImageView createImageEffect(ImageView imageView, TransformEffects effect) {

		TransformImagesViewEffect transformEffect = new TransformImagesViewEffect();

		imageView = transformEffect.addEffect(imageView, effect);

		return imageView;
	}

	private Label createLabelEffect(Label label) {
		label.setMaxWidth(120);
		label.getStyleClass().add("image-panel-title");
		return label;
	}

	private Pane createPaneEffect(Pane pane) {
		TransformPanelEffect transform = new TransformPanelEffect();
		pane = transform.addEffect(pane, TransformEffects.BORDER_IMAGE);
		return pane;
	}

	private ArrayList<Image> getImagesFromCollectionItens() {
		ArrayList<Image> images = new ArrayList<>();

		collections.getItens().forEach(item -> images.add(new Image(item.getImageUrl())));
		return images;
	}

	public GridPaneTable getGridTable() {
		return gridTable;
	}

	public Collections getCollections() {
		return collections;
	}
	
	public CollectionLoader getCollectionLoader() {
		return futureCollections;
	}

}
