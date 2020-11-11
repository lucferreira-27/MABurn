package com.lucas.ferreira.maburn.model;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

	public GridPaneCell(Node node) throws Exception {
		// TODO Auto-generated constructor stub

		if (!(node instanceof AnchorPane)) {
			throw new Exception();
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

}
