package com.lucas.ferreira.maburn.model.collections.management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.enums.CollectionFilterType;
import com.lucas.ferreira.maburn.util.comparator.FilterAscComparator;
import com.lucas.ferreira.maburn.util.comparator.FilterDescComparator;
import com.lucas.ferreira.maburn.util.comparator.FilterScoreComparator;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.GridPane;

public class CollectionFilter {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private ObjectProperty<CollectionFilterType> propertyActiveFilter = new SimpleObjectProperty<CollectionFilterType>();

	public CollectionFilter() {
	}

	public void filter(GridPaneTable collectionTable, GridPane gridPaneTable, CollectionFilterType filter) {

		GridPane table = gridPaneTable;
		propertyActiveFilter.set(filter);
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
		LOGGER.config("Active filter order DATE");

	}
	private void filterByScore(GridPane table, GridPaneTable collectionTable) {
		LOGGER.config("Active filter order SCORE");
		List<GridPaneCell> cells = getCellsFromGridPane(table);
		Collections.sort(cells, new FilterScoreComparator());
		addCellsInGridPane(cells, table, collectionTable);

	}
	private void filterOrderAsc(GridPane table, GridPaneTable collectionTable) {
		LOGGER.config("Active filter order ASC");
		List<GridPaneCell> cells = getCellsFromGridPane(table);
		Collections.sort(cells, new FilterAscComparator());
		// printCellsValues(cells);

		addCellsInGridPane(cells, table, collectionTable);

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
		LOGGER.config("Active filter order DESC");
		List<GridPaneCell> cells = getCellsFromGridPane(table);
		Collections.sort(cells, new FilterDescComparator());
		// printCellsValues(cells);
		addCellsInGridPane(cells, table, collectionTable);
	}

	public ObjectProperty<CollectionFilterType> propertyActiveFilter() {
		return propertyActiveFilter;
	}
}
