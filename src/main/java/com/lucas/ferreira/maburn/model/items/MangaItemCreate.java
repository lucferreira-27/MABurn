package com.lucas.ferreira.maburn.model.items;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.dao.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ThumbnailController;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;

public class MangaItemCreate implements ItemCreater<MangaDownloaded> {
	private KitsuDatabase database = new KitsuDatabase();
	private MangaCollection collection;


	public MangaItemCreate(MangaCollection collection) {
		
		this.collection = collection;


	}

	@Override
	public MangaDownloaded createItem(String destination) {

		MangaDownloaded manga = new MangaDownloaded();
		manga.setName(destination.substring(destination.lastIndexOf("\\") + 1));

		CollectDatas collectDatas = database.read(manga.getName(), Category.MANGA);

		manga.setDestination(destination);
		manga.setTitleDataBase(collectDatas.getCanonicalTitle());
		manga.setTitles(collectDatas.getTitles());
		manga.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		manga.setImageUrl(collectDatas.getPosterImageLink("medium"));
		manga.setId(collectDatas.getId());
		manga.setMangaCollection(collection);
	
		ThumbnailController thumbnail = new ThumbnailController(manga);

		try {
			
			String thumanailPath = thumbnail.download().getAbsolutePath();
			manga.setImageLocal(thumanailPath);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return manga;
	}

	@Override
	public MangaDownloaded createSearchItem(CollectDatas collectDatas) {
		
		MangaDownloaded manga = new MangaDownloaded();
		
		manga.setName(collectDatas.getCanonicalTitle());
		manga.setTitleDataBase(collectDatas.getCanonicalTitle());
		manga.setTitles(collectDatas.getTitles());
		manga.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		manga.setImageUrl(collectDatas.getPosterImageLink("medium"));
		manga.setId(collectDatas.getId());
		manga.setMangaCollection(collection);
		manga.setScore(collectDatas.getAvaregeRating());
		manga.setDate(collectDatas.getPublishedDate());
		return manga;
	}

}
