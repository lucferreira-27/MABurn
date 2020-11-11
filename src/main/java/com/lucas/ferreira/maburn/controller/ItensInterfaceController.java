package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.CollectionGridCellComparator;
import com.lucas.ferreira.maburn.model.CollectionMatch;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.util.ViewUtil;
import com.lucas.ferreira.maburn.view.ItensInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ItensInterfaceController implements Initializable {
	@FXML
	private GridPane itensImagesGridPane;
	@FXML
	private AnchorPane collectionAnchorPane;
	@FXML
	private TextField txtSearchBar;
	@FXML
	private Button btnFilter;
	
	private ItensInterfaceView newView;

	private MainInterfaceView mainView;
	private MainInterfaceController mainController = new MainInterfaceController();
	private ItensInterfaceView itensView;
	private ArrayList<ImageView> imageViews;
	private Collections completeCollection;
	private Collections mathCollection;
	private List<CollectionItem> itens;
	private List<CollectionItem> originalItens;
	private List<AnchorPane> removesPanes = new ArrayList<>();
	String querry;

	public ItensInterfaceController(MainInterfaceView mainView, ItensInterfaceView itensView) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;
		this.itensView = itensView;

	}

	public void onClickOnImageGridPane() {

		itensImagesGridPane.setOnMouseClicked(event -> {

			if (event.getPickResult().getIntersectedNode().getParent() instanceof AnchorPane) {
				AnchorPane pane = (AnchorPane) event.getPickResult().getIntersectedNode().getParent();
				System.out.println("GridPane Children: " + itensImagesGridPane.getChildren().size());
				System.out.println("Column Index: " + itensImagesGridPane.getColumnIndex(pane) + " Row Index: "
						+ itensImagesGridPane.getRowIndex(pane));

				ImageView image = (ImageView) pane.getChildren().get(0);
				Label size = (Label) pane.getChildren().get(2);
				if (image.getUserData() instanceof CollectionItem) {

					CollectionItem item = (CollectionItem) image.getUserData();
					//CommandLineView view = new CommandLineView();

					if (item instanceof Anime) {
						/// size.setText("Episodes: "+ size.getText());

						 //view.informEpisodesInAnime((Anime) item);
					}

					else if (item instanceof Manga) {
						// size.setText("Chapters: "+ size.getText());

						 //view.informChaptersInManga((Manga) item);
					}

				}
			}

		});
	}

	public void onSearchBarType() {
		txtSearchBar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// mathCollection.setItens(itens);
				reloadCollection(newValue);
			}
		});

	}

	private void reloadCollection(String value) {
		List<CollectionItem> originalItens = new ArrayList<>();
		List<CollectionItem> mathItens = new ArrayList<>();

		GridPaneTable originalTable = itensView.getGridTable();
		GridPaneTable mathTable = new GridPaneTable(7);

		originalTable.getCells().forEach(cell -> originalItens.add((CollectionItem) cell.getUserData()));
		mathItens = CollectionMatch.locale(originalItens, value);

		for (int i = 0; i < originalTable.getCells().size(); i++) {
			CollectionItem item = (CollectionItem) originalTable.getCells().get(i).getUserData();
			if (mathItens.contains(item)) {
				mathTable.add(originalTable.getCells().get(i));
			}
		}
		replaceGridPaneTable(mathTable);

	}

	private void replaceGridPaneTable(GridPaneTable newTable) {

		List<GridPaneCell> cells = newTable.getCells();
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
	}

	public void reloadCollection() {

		List<AnchorPane> panes = new ArrayList<>();
		Platform.runLater(() -> {
			for (int i = 0; i < itensImagesGridPane.getChildren().size(); i++) {
				AnchorPane pane = (AnchorPane) itensImagesGridPane.getChildren().get(i);
				ImageView image = (ImageView) pane.getChildren().get(0);
				CollectionItem item = (CollectionItem) image.getUserData();
				if (!itens.contains(item)) {
					// pane.setVisible(false);
					removeItemFromPanel(pane, item);

				}
			}

			if (itens.size() > 0)
				for (CollectionItem recoveritem : itens) {

					recoverItemFromPanel(recoveritem);

				}

		});

	}

	public void onClickFilter() {

		if (btnFilter.getText().equals("A-Z"))
			reverseImagesGridPane();
		else
			sortImagesGridPane();
	}

	private void sortImagesGridPane() {
		Platform.runLater(() -> {
			GridPaneTable gridTable = itensView.getGridTable();

			List<GridPaneCell> cells = gridTable.getCells();
			java.util.Collections.sort(cells, new CollectionGridCellComparator());
			itensImagesGridPane.getChildren().clear();
			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);
				System.out.println(((CollectionItem) cell.getUserData()).getTitleDataBase());
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

	private void recoverItemFromPanel(CollectionItem item) {
		// TODO Auto-generated method stub
		for (int i = 0; i < removesPanes.size(); i++) {
			AnchorPane pane = removesPanes.get(i);
			ImageView image = (ImageView) pane.getChildren().get(0);
			CollectionItem recoveritem = (CollectionItem) image.getUserData();
			if (recoveritem.getTitleDataBase().equals(item.getTitleDataBase())) {

				int r = ViewUtil.getImagesGridPaneLastRow(itensImagesGridPane.getChildren().size(), 7);
				int c = ViewUtil.getImagesGridPaneLastColumn(itensImagesGridPane.getChildren().size(), 7);

				itensImagesGridPane.add(pane, c, r); // Position code here
				pane.setVisible(true);
				pane.setManaged(true);
				completeCollection.setItens(itens);
				removesPanes.remove(pane);
			}
		}

	}

	private void removeItemFromPanel(AnchorPane pane, CollectionItem item) {
		pane.setVisible(false);
		pane.setManaged(false);

		itensImagesGridPane.getChildren().remove(pane);
		removesPanes.add(pane);
		completeCollection.setItens(itens);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		onClickOnImageGridPane();
		onSearchBarType();

	}

	public void setCollection(Collections collections) {
		// TODO Auto-generated method stub
		this.completeCollection = collections;

		this.originalItens = (List<CollectionItem>) completeCollection.getItens();

	}

}
