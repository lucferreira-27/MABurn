package com.lucas.ferreira.maburn.model.webscraping.datas;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.MangaDownloaded;

public class ScrapeMangaData implements ScrapeData {
	private MangaDownloaded manga = new MangaDownloaded();
	
	public List<String> getChaptersLinks(){
		ChapterDownloaded chapter = new ChapterDownloaded();
	
		return null;
	}
	
	
	
	
	public MangaDownloaded getManga() {
		return manga;
	}

}
