package com.lucas.ferreira.maburn.model.itens;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
import com.lucas.ferreira.maburn.model.download.ThumbnailController;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.CustomLogger;

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
		CustomLogger.log("Creating " + anime.getName());

		CollectDatas collectDatas = database.read(anime.getName(), Category.ANIME);

		anime.setDestination(destination);
		anime.setTitleDataBase(collectDatas.getCanonicalTitle());
		anime.setTitles(collectDatas.getTitles());
		anime.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		anime.setImageUrl(collectDatas.getPosterImageLink("medium"));
		anime.setId(collectDatas.getId());
		anime.setAnimeCollection(collection);

		ThumbnailController thumbnail = new ThumbnailController(anime);

		try {

			String thumanailPath = thumbnail.download().getAbsolutePath();
			anime.setImageLocal(thumanailPath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return anime;
	}

	@Override
	public AnimeDownloaded createSearchItem(CollectDatas collectDatas) {
		// TODO Auto-generated method stub
		AnimeDownloaded anime = new AnimeDownloaded();
		
		anime.setName(collectDatas.getCanonicalTitle());
		anime.setTitleDataBase(collectDatas.getCanonicalTitle());
		anime.setTitles(collectDatas.getTitles());
		anime.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		anime.setImageUrl(collectDatas.getPosterImageLink("medium"));
		anime.setId(collectDatas.getId());
		anime.setAnimeCollection(collection);

		return anime;
	}

}
