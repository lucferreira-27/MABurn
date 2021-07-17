package com.lucas.ferreira.maburn.util.predicates;

import java.util.List;
import java.util.function.Predicate;

import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;

public class ItemWebDataFilter implements Predicate<ItemWebData> {

	private List<String> filesName;

	public ItemWebDataFilter(List<String> filesName) {
		
		this.filesName = filesName;
	}

	@Override
	public boolean test(ItemWebData t) {
		

		for (String file : filesName) {
						if (file.equals(t.getName())) {	
				return false;
			}
		}
		
		return true;
	}

}
