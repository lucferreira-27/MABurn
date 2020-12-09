package com.lucas.ferreira.maburn.util;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.collections.Collections;

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

			if (file.equals(t.getName())) {
				//System.out.println("Name: " + t.getName() + " - "  + "File"+ file);
				return false;
			}
		}
		System.out.println("Don't found: " + t.getName());
		return true;
	}

}
