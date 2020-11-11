package com.lucas.ferreira.maburn.model;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class CollectionGridCellComparator implements Comparator<GridPaneCell> {

	@Override
	public int compare(GridPaneCell o1, GridPaneCell o2) {
		// TODO Auto-generated method stub
		GridPaneTable gridTable = new GridPaneTable(7);
		CollectionNodeComparator comparator = new CollectionNodeComparator();
		int result = comparator.compare(o1.getNode(), o2.getNode());

//		if (result < 0) {
//			gridTable.replaceCell(o1, o2);
//		} else if (result > 0) {
//			gridTable.replaceCell(o2, o1);
//
//		}

		return result;
	}

}
