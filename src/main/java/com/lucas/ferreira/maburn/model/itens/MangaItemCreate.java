package com.lucas.ferreira.maburn.model.itens;

import java.io.File;
import java.io.IOException;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
import com.lucas.ferreira.maburn.model.download.ThumbnailDownload;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadImageServiceModel;
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
		// Update xml files TODO
		manga.setDestination(destination);
		manga.setTitleDataBase(collectDatas.getCanonicalTitle());
		manga.setTitles(collectDatas.getTitles());
		manga.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		manga.setImageUrl(collectDatas.getPosterImageLink("medium"));
		manga.setId(collectDatas.getId());
		manga.setMangaCollection(collection);
	
		ThumbnailDownload thumbnail = new ThumbnailDownload(manga);

		try {
			
			String thumanailPath = thumbnail.download().getAbsolutePath();
			manga.setImageLocal(thumanailPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return manga;
	}

}
