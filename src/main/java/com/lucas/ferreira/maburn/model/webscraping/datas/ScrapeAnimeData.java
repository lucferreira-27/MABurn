package com.lucas.ferreira.maburn.model.webscraping.datas;

import com.lucas.ferreira.maburn.model.bean.AnimeDownloaded;

public class ScrapeAnimeData implements ScrapeData {
	private AnimeDownloaded anime = new AnimeDownloaded();

	public AnimeDownloaded getAnime() {
		return anime;
	}

}
