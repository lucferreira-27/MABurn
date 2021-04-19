package com.lucas.ferreira.maburn.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.CollectionFilterType;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.comparator.FilterAscComparator;
import com.lucas.ferreira.maburn.util.comparator.FilterDescComparator;
import com.lucas.ferreira.maburn.util.comparator.FilterScoreComparator;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class CollectionFilter {

	private CollectionFilterType activeFilter;

	public CollectionFilter() {
	}

	public void filter(GridPaneTable collectionTable, GridPane gridPaneTable, CollectionFilterType filter) {

		GridPane table = gridPaneTable;
		activeFilter = filter;
		switch (filter) {
		case ASC:
			filterOrderAsc(table, collectionTable);
			break;
		case DESC:
			filterOrderDesc(table, collectionTable);
			break;
		case DATE:
			filterByDate(table, collectionTable);
			break;
		case SCORE:
			filterByScore(table, collectionTable);
			break;
		}
	}

	private void filterByDate(GridPane table, GridPaneTable collectionTable) {
		CustomLogger.log("FILTER BY DATE");

	}
	private void filterByScore(GridPane table, GridPaneTable collectionTable) {
		CustomLogger.log("FILTER BY SCORE");
		List<GridPaneCell> cells = getCellsFromGridPane(table);
		Collections.sort(cells, new FilterScoreComparator());
		addCellsInGridPane(cells, table, collectionTable);

	}
	private void filterOrderAsc(GridPane table, GridPaneTable collectionTable) {
		CustomLogger.log("FILTER ORDER ASC");
		List<GridPaneCell> cells = getCellsFromGridPane(table);
		Collections.sort(cells, new FilterAscComparator());
		// printCellsValues(cells);

		addCellsInGridPane(cells, table, collectionTable);

	}

	private void printCellsValues(List<GridPaneCell> cells) {
		cells.stream().forEach((cell) -> {
			System.out.println("FILTERED " + ((CollectionItem) cell.getUserData()).getName());
		});
	}

	private List<GridPaneCell> getCellsFromGridPane(GridPane table) {
		List<GridPaneCell> cells = new ArrayList<GridPaneCell>();

		table.getChildren().forEach(child -> {
			GridPaneCell cell = (GridPaneCell) child.getUserData();

			cells.add(cell);
		});
		return cells;
	}

	private void addCellsInGridPane(List<GridPaneCell> cells, GridPane table, GridPaneTable collectionTable) {
		Platform.runLater(() -> {
			table.getChildren().clear();

			for (int i = 0; i < cells.size(); i++) {
				GridPaneCell cell = cells.get(i);
				int c = GridPaneTable.getImagesGridPaneLastColumn(i, collectionTable.getColumnSize());
				int r = GridPaneTable.getImagesGridPaneLastRow(i, collectionTable.getColumnSize());
				cell.setColumn(c);
				cell.setRow(r);
				table.add(cell.getNode(), cell.getColumn(), cell.getRow());

			}
		});
	}

	private void filterOrderDesc(GridPane table, GridPaneTable collectionTable) {
		CustomLogger.log("FILTER ORDER DESC");
		List<GridPaneCell> cells = getCellsFromGridPane(table);
		Collections.sort(cells, new FilterDescComparator());
		// printCellsValues(cells);
		addCellsInGridPane(cells, table, collectionTable);
	}

	public CollectionFilterType getActiveFilter() {
		return activeFilter;
	}
}
