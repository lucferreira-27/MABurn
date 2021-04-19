package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.scene.Node;

public class FilterAscComparator implements Comparator<GridPaneCell> {


	@Override
	public int compare(GridPaneCell cell1, GridPaneCell cell2) {
		// TODO Auto-generated method stub
		
		String str1 =  ((CollectionItem)cell1.getUserData()).getTitleDataBase();
		String str2 =  ((CollectionItem)cell2.getUserData()).getTitleDataBase();

		return str1.compareTo(str2);
	}

}
