package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class FilterScoreComparator implements Comparator<GridPaneCell> {


	@Override
	public int compare(GridPaneCell cell1, GridPaneCell cell2) {
		
		
		Double score1 =  ((CollectionTitle)cell1.getUserData()).getScore();
		Double score2 =  ((CollectionTitle)cell2.getUserData()).getScore();
	
		int result = score1.compareTo(score2);
		int absResult = Math.abs(result);
		
		if(result != absResult) {
			return absResult;
		}
		result -= result * 2;

		return result;
	}

}
