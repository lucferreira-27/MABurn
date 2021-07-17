package com.lucas.ferreira.maburn.util.predicates;

import java.util.List;
import java.util.function.Predicate;

import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;

public class ItemWebDataFilter implements Predicate<ItemWebData> {

	private List<String> filesName;

	public ItemWebDataFilter(List<String> filesName) {
		// TODO Auto-generated constructor stub
		this.filesName = filesName;
	}

	@Override
	public boolean test(ItemWebData t) {
		// TODO Auto-generated method stub

		for (String file : filesName) {
			//
			if (file.equals(t.getName())) {	
				return false;
			}
		}
		
		return true;
	}

}
