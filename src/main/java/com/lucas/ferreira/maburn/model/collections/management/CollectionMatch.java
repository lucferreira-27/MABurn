package com.lucas.ferreira.maburn.model.collections.management;

import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class CollectionMatch {

	public static List<CollectionTitle> locale(List<CollectionTitle> itens, String math) {

		if (itens == null)
			return null;

		List<CollectionTitle> mathItens = itens.stream().filter(item -> item.getTitleDataBase().contains(math))
				.collect(Collectors.toList());

		return mathItens;
	}



	public static boolean itemGenericMath(CollectionTitle item, String math) {
		
		if (item.getTitleDataBase().contains(math))
			return true;
		return false;
	}
}
