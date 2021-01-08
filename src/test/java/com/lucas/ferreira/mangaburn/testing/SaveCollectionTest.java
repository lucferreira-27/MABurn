package com.lucas.ferreira.mangaburn.testing;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.SaveCollection;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class SaveCollectionTest {
	@Test
	public void testLoadData() {
		SaveCollection save = new SaveCollection(new CollectionDatasReader().getDocumentCollectionDates());
		
		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setDestination("D:\\AnimeBurn\\Great Pretenderr2");
		CustomLogger.log(save.loadDatas(anime).getTitleDataBase());
		
	}
	@Test
	public void testSaveData() {
		SaveCollection save = new SaveCollection(new CollectionDatasReader().getDocumentCollectionDates());
		
		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setDestination("D:\\AnimeBurn\\Great Pretenderr2");
		save.saveDatas(anime);
		CustomLogger.log(save.loadDatas(anime).getTitleDataBase());
		
	}
	@Test
	public void testDeleteData() {
		SaveCollection save = new SaveCollection(new CollectionDatasReader().getDocumentCollectionDates());
		
		AnimeDownloaded anime = new AnimeDownloaded();
		anime.setDestination("D:\\AnimeBurn\\Great Pretenderr2");
		save.deleteData(anime);
		//CustomLogger.log(save.loadDatas(anime).getTitleDataBase());
		
	}
}
