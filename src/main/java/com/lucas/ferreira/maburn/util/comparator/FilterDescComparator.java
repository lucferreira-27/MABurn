package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

public class FilterDescComparator implements Comparator<GridPaneCell> {

	@Override
	public int compare(GridPaneCell cell1, GridPaneCell cell2) {
		// TODO Auto-generated method stub

		String str1 = ((CollectionItem) cell1.getUserData()).getTitleDataBase();
		String str2 = ((CollectionItem) cell2.getUserData()).getTitleDataBase();

		int result = str1.compareTo(str2);
		int absResult = Math.abs(result);
		
		if(result != absResult) {
			return absResult;
		}
		result -= result * 2;

		return result;

	}
}
