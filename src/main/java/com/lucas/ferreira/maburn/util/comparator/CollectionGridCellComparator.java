package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.GridPaneCell;

public class CollectionGridCellComparator implements Comparator<GridPaneCell> {

	@Override
	public int compare(GridPaneCell o1, GridPaneCell o2) {
		
		CollectionNodeComparator comparator = new CollectionNodeComparator();
		int result = comparator.compare(o1.getNode(), o2.getNode());

		return result;
	}

}
