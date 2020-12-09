package com.lucas.ferreira.maburn.model.itens;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
import com.lucas.ferreira.maburn.model.enums.Category;

public class MangaItemCreate implements ItemCreater<MangaDownloaded> {
	private KitsuDatabase database = new KitsuDatabase();
	private MangaCollection collection;


	public MangaItemCreate(MangaCollection collection) {
		// TODO Auto-generated constructor stub
		this.collection = collection;


	}

	@Override
	public MangaDownloaded createItem(String destination) {

		MangaDownloaded manga = new MangaDownloaded();
		manga.setName(destination.substring(destination.lastIndexOf("\\") + 1));

		CollectDatas collectDatas = database.read(manga.getName(), Category.MANGA);

		manga.setDestination(destination);
		manga.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		manga.setTitleFromDataBase(collectDatas.getTitle());
		manga.setImageUrl(collectDatas.getPosterImageLink("medium"));
		manga.setId(collectDatas.getId());
		manga.setMangaCollection(collection);

		return manga;
	}

}
