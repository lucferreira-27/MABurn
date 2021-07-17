package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

import javafx.scene.Node;

public class FilterAscComparator implements Comparator<GridPaneCell> {


	@Override
	public int compare(GridPaneCell cell1, GridPaneCell cell2) {
		
		
		String str1 =  ((CollectionTitle)cell1.getUserData()).getTitleDataBase();
		String str2 =  ((CollectionTitle)cell2.getUserData()).getTitleDataBase();

		return str1.compareTo(str2);
	}

}
