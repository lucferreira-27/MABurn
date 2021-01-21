package com.lucas.ferreira.maburn.util.predicates;

import java.util.List;
import java.util.function.Predicate;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;

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
			//System.out.println("File: " + file + " | ItemWebData: " + t.getName() );
			if (file.equals(t.getName())) {	
				return false;
			}
		}
		System.out.println("Don't found: " + t.getName());
		return true;
	}

}
