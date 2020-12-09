package com.lucas.ferreira.maburn.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.lucas.ferreira.maburn.controller.ItensInterfaceController;
import com.lucas.ferreira.maburn.exceptions.LoadingException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.ImageLoaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.effects.TransformEffects;
import com.lucas.ferreira.maburn.model.effects.TransformImagesViewEffect;
import com.lucas.ferreira.maburn.model.effects.TransformPanelEffect;
import com.lucas.ferreira.maburn.model.enums.LoadingType;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.util.CollectionGridCellComparator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ItensInterfaceView implements ViewInterface {

	private MainInterfaceView mainView;
	private Pane root;
	private GridPaneTable gridTable = new GridPaneTable(7);
	private GridPane itensImagesGridPane;
	private ProgressIndicator loadProgress;
	private ScrollPane itensImagesScroll;
	private ItensInterfaceController controller;
	private Future<?> futureCollections;
	private Collections collections;
	private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
	private Map<String, Object> namespace;

	public ItensInterfaceView(Future<?> futureCollections) {
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

		loadProgress = (ProgressIndicator) namespace.get("loadGridPane");

		switch (loading) {
		case COLLECTION:
			collectionLoading(loadProgress);
			System.out.println("Collection Loading DONE!");

			break;
		case FILTER:
			filterLoading(loadProgress);
			System.out.println("Filter Loading DONE!");
			break;
		default:
			loadProgress.setVisible(false);
			break;
		}

	}

	private void collectionLoading(ProgressIndicator load) throws LoadingException {
		try {

			if (collections == null) {
				load.setVisible(true);

				System.out.println("Future collection");
				collections = (Collections) futureCollections.get();
				collections.getItens()
						.sort((item1, item2) -> item1.getTitleDataBase().compareTo(item2.getTitleDataBase()));

				controller.setCollection(collections);

				Platform.runLater(() -> load.setProgress(1));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new LoadingException("No itens found in collection");

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block

			throw new LoadingException("No itens found in collection");
		} finally {
			load.setVisible(false);

		}
	}

	private void filterLoading(ProgressIndicator load) {
		new Thread(() -> {
			load.setVisible(true);
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
				load.setVisible(false);
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

			initItensImagesScrollPane();
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

	private void initItensImagesScrollPane() {

		itensImagesScroll = (ScrollPane) namespace.get("itensImagesScroll");
		itensImagesScroll.setLayoutY(10);
		itensImagesScroll.setLayoutX(200);
		itensImagesScroll.setPrefViewportHeight(root.getScene().getHeight() - 200);
		itensImagesScroll.setPannable(false);
	}

	private void addAllNodes() {
		
		if (gridTable.getCells().size() == 0)
			addAllImageViewInImageGrid();

	}

	private void addAllImageViewInImageGrid() {
		ArrayList<Thread> threads = new ArrayList<>();

		ImageLoaderModel imageLoader = new ImageLoaderModel();

		// Create a thread for each item
		for (CollectionItem item : collections.getItens()) {

			Thread fetchImage = new Thread(() -> {
				synchronized (imageViews) { // Add images views to the array list need be synchronized with the
					// addImageViewInImageGrid (array list size)
					imageViews.add(imageLoader.loadImageViewByUrl(item.getImageUrl()));

					Platform.runLater(() -> {
						// To add a ImageView in the GridPane is require a ImageView, current nodes
						// quantity in grid pane and the max column length
						try {
							addImageViewInImageGrid(imageViews.get(imageViews.size() - 1), imageViews.size() - 1, 7,
									item);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(item.getImageUrl());
							System.out.println(imageViews.get(imageViews.size() - 1));
							e.printStackTrace();
						}
					});
				}

			});
			threads.add(fetchImage);
		}

		threads.forEach(thread -> thread.start());

	}

	private void addImageViewInImageGrid(ImageView imageView, int total, int columnMax, CollectionItem item)
			throws Exception {

		imageView.setFitWidth(168.75);
		imageView.setFitHeight(237.0);
		imageView.setUserData(item);
		try {

			Label title = new Label(item.getTitleDataBase());

			imageView = createImageEffect(imageView, TransformEffects.BORDER_IMAGE);

			title = createLabelEffect(title);
			Label subItensSize = new Label();

			if (item instanceof AnimeDownloaded) {
				subItensSize = new Label("Episodes: " + String.valueOf(item.getListSubItens().size()));
			}
			if (item instanceof MangaDownloaded) {
				subItensSize = new Label("Chapters: " + String.valueOf(item.getListSubItens().size()));

			}
			AnchorPane imageAreaPanel = new AnchorPane(imageView, title, subItensSize);
			imageAreaPanel = (AnchorPane) createPaneEffect(imageAreaPanel);

			imageAreaPanel.getStyleClass().add("item-image");

			GridPaneCell cell = new GridPaneCell(imageAreaPanel);
			gridTable.add(cell);

			imageView.setLayoutX(3);
			imageView.setLayoutY(3);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	private void sortImagesGridPane() {

		List<GridPaneCell> cells = gridTable.getCells();
		java.util.Collections.sort(cells, new CollectionGridCellComparator());
		CollectionItem item = (CollectionItem) cells.get(0).getUserData();
		int position = 0;
		// cells.forEach(cell -> cell.set Row(0));

		for (int i = 0; i < cells.size(); i++) {
			GridPaneCell cell = cells.get(i);

			int c = GridPaneTable.getImagesGridPaneLastColumn(i, 7);
			int r = GridPaneTable.getImagesGridPaneLastRow(i, 7);

			cell.setColumn(c);
			cell.setRow(r);

			itensImagesGridPane.add(cell.getNode(), cell.getColumn(), cell.getRow());

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

}
