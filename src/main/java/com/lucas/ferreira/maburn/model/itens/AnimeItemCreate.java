package com.lucas.ferreira.maburn.model.itens;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
import com.lucas.ferreira.maburn.model.enums.Category;

public class AnimeItemCreate implements ItemCreater<AnimeDownloaded> {
	private KitsuDatabase database = new KitsuDatabase();
	private AnimeCollection collection;

	public AnimeItemCreate(AnimeCollection collection) {
		// TODO Auto-generated constructor stub
		this.collection = collection;

	}

	@Override
	public AnimeDownloaded createItem(String destination) {
		// TODO Auto-generated method stub

		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setName(destination.substring(destination.lastIndexOf("\\") + 1));

		CollectDatas collectDatas = database.read(anime.getName(), Category.ANIME);
		
		anime.setDestination(destination);
		anime.setTitleFromDataBase(collectDatas.getTitle());
		anime.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		anime.setImageUrl(collectDatas.getPosterImageLink("medium"));
		anime.setId(collectDatas.getId());
		anime.setAnimeCollection(collection);

		return anime;
	}

}
