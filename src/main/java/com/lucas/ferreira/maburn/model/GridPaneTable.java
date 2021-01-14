package com.lucas.ferreira.maburn.model;

import java.util.ArrayList;
import java.util.List;

public class GridPaneTable {

	private List<GridPaneCell> cells = new ArrayList<>();;
	private int columnSize;

	public GridPaneTable(List<GridPaneCell> cells, int columnSizeMax) {
		// TODO Auto-generated constructor stub
		this.cells = cells;
		this.columnSize = columnSizeMax;
	}

	public GridPaneTable(int columnSizeMax) {
		// TODO Auto-generated constructor stub
		this.columnSize = columnSizeMax;
	}

	public List<GridPaneCell> getCells() {
		return cells;
	}

	public void setCells(List<GridPaneCell> cells) {
		this.cells = cells;
	}

	public void add(GridPaneCell cell) {
		cells.add(cell);

	}

	public void remove(GridPaneCell cell) {
		cells.remove(cell);
	}



	public static int getImagesGridPaneLastColumn(int total, int columnSizeMax) {

		if (total < columnSizeMax) {
			return total;
		}

		int c = 0;

		c = (total % columnSizeMax);

		return c;
	}

	public static int getImagesGridPaneLastRow(int total, int columnSizeMax) {

		double rest = total / columnSizeMax;
		int r = (int) rest;

		return r;
	}

	public void replaceCell(int target, int index) {
		GridPaneCell cell = cells.get(index);
		GridPaneCell cellTarget = cells.get(target);

		int row = cell.getRow();
		int column = cell.getColumn();

		int rowTarget = cellTarget.getRow();
		int columnTarget = cellTarget.getColumn();

		cellTarget.setRow(row);
		cellTarget.setRow(column);

		cell.setRow(rowTarget);
		cell.setColumn(columnTarget);

	}

	public void replaceCell(GridPaneCell cellTarget, GridPaneCell cell) {
		int row = cell.getRow();
		int column = cell.getColumn();

		int rowTarget = cellTarget.getRow();
		int columnTarget = cellTarget.getColumn();

		cellTarget.setRow(row);
		cellTarget.setColumn(column);

		cell.setRow(rowTarget);
		cell.setColumn(columnTarget);
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	public int getColumnSize() {
		return columnSize;
	}
	

}
