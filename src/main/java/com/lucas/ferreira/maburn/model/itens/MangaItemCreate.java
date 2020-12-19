package com.lucas.ferreira.maburn.model.itens;

import java.io.File;
import java.io.IOException;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.databases.KitsuDatabase;
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
		manga.setTitleDataBase(collectDatas.getTitle());
		manga.setDataBaseUrl(collectDatas.getItemDataBaseUrl());
		manga.setImageUrl(collectDatas.getPosterImageLink("medium"));
		manga.setId(collectDatas.getId());
		manga.setMangaCollection(collection);
	
		DownloadImageServiceModel service = new DownloadImageServiceModel(collectDatas.getPosterImageLink("medium"),
				new File("C:\\Users\\lucfe\\Documents\\MangaBurn\\Files\\Thumbnails\\Mangas\\" + manga.getTitleFileName()));
		try {
			
			String thumanailPath = service.download().getAbsolutePath();
			
			manga.setImageLocal(thumanailPath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return manga;
	}

}
