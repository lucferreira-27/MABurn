package com.lucas.ferreira.maburn.model.items;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.dao.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ThumbnailController;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;

public class AnimeItemCreate implements ItemCreater<AnimeDownloaded> {
	private KitsuDatabase database = new KitsuDatabase();
	private AnimeCollection collection;

	public AnimeItemCreate(AnimeCollection collection) {
		
		this.collection = collection;

	}

	@Override
	public AnimeDownloaded createItem(String destination) {
		

		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setName(destination.substring(destination.lastIndexOf("\\") + 1));

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
			 
			e.printStackTrace();
		}

		return anime;
	}

	@Override
	public AnimeDownloaded createSearchItem(CollectDatas collectDatas) {
		
		AnimeDownloaded anime = new AnimeDownloaded();
		
		anime.setName(collectDatas.getCanonicalTitle());
		anime.setTitleDataBase(collectDatas.getCanonicalTitle());
		anime.setTitles(collectDatas.getTitles());
		anime.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		anime.setImageUrl(collectDatas.getPosterImageLink("medium"));
		anime.setId(collectDatas.getId());
		anime.setAnimeCollection(collection);
		anime.setScore(collectDatas.getAvaregeRating());
		anime.setDate(collectDatas.getPublishedDate());
		
		return anime;
	}

}
