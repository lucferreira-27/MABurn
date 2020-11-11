package com.lucas.ferreira.maburn.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class CollectionMatch {

	public static List<CollectionItem> locale(List<CollectionItem> itens, String math) {

		if (itens == null)
			return null;

		List<CollectionItem> mathItens = itens.stream().filter(item -> item.getTitleDataBase().contains(math))
				.collect(Collectors.toList());

		return mathItens;
	}

	private static boolean itemPreciseMath(CollectionItem item, String math) {

		return false;
	}

	public static boolean itemGenericMath(CollectionItem item, String math) {
		System.out.println(item.getTitleDataBase() + " " + math);
		if (item.getTitleDataBase().contains(math))
			return true;
		return false;
	}
}
