package com.lucas.ferreira.mangaburn.testing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.CollectionMatch;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class CollectionMatcherTest {

	@Test
	public void locale() {
			

		List<CollectionItem> itens = new ArrayList<>();
		List<String> names = new ArrayList<>();
		names.add("One Piece");
		names.add("Dragon Ball");
		names.add("Naruto");
		names.add("Great Pretender");
		names.add("Akudama Drive");
		names.add("Angel Beats");
		
		for (int i = 0; i < names.size(); i++) {
			CollectionItem item = new AnimeDownloaded();
			item.setTitleFromDataBase(names.get(i));
			itens.add(item);
		}

		itens = CollectionMatch.locale(itens, "");
		itens.forEach(item -> System.out.println(item.getTitleDataBase()));
	}

}
