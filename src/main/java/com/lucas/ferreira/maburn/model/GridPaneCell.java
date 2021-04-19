package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GridPaneCell {
	private Node node;
	private int row;
	private int column;
	public GridPaneCell(Node node, int column, int row) {
		// TODO Auto-generated constructor stub
		GridPane.setConstraints(node, column, row);
		this.node = node;
		this.column = column;
		this.row = row;
	}

	public GridPaneCell(Node node) throws IllegalAccessException {
		// TODO Auto-generated constructor stub

		if (!(node instanceof AnchorPane)) {
			throw new IllegalAccessException();
		}
		this.node = node;
		

	}

	public Object getUserData() {
		AnchorPane pane = (AnchorPane) node;
		ImageView image = (ImageView) pane.getChildren().get(0);
		return image.getUserData();
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public Node getNode() {
		return node;
	}

	public void setRow(int row) {

		GridPane.setConstraints(node, column, row);

		this.row = row;
	}

	public void setColumn(int column) {
		GridPane.setConstraints(node, column, row);

		this.column = column;
	}

	@Override
	public String toString() {
		return "GridPaneCell [node=" + node + ", row=" + row + ", column=" + column + "]";
	}

	public int compareTo(GridPaneCell a) {
		// TODO Auto-generated method stub
		String str1 =  ((CollectionItem)this.getUserData()).getTitleDataBase();
		String str2 =  ((CollectionItem)a.getUserData()).getTitleDataBase();
		return str1.compareTo(str2);
	}

}
