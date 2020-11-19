package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.model.ConnectionModel;
import com.lucas.ferreira.maburn.model.bean.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.MangaWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.MangasSites;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingResult;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

public class MangaHostScraping implements WebScraping {
	private Scraper scraper = new Scraper();
	private Response response;

	public MangaHostScraping() {

	}

	@Override
	public MangasSites getSite() {
		// TODO Auto-generated method stub
		return MangasSites.MANGA_HOST;
	}

	public List<ChapterWebData> fetchChaptersUrl() {
		try {
			List<ChapterWebData> chapterWebDatas = new ArrayList<>();
			Elements elements = scraper.scrapeSnippet(response.parse(), ".btn-green.w-button.pull-left");
			elements.forEach(element -> {

				ChapterWebData chapterWebData = new ChapterWebData();
				chapterWebData.setUrl(element.attr("href"));
				chapterWebDatas.add(chapterWebData);

			});
			return chapterWebDatas;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public ChapterWebData fetchPagesUrl(ChapterWebData chapterWebData) {

		try {
			Elements elements = scraper.scrapeSnippet(response.parse(), "picture > img");
			elements.forEach(element -> {

				chapterWebData.addPagesUrl((element.attr("src")));
			});
			return chapterWebData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		// TODO Auto-generated method stub
		MangaWebData mangaWebData = (MangaWebData) titleWebData;

		response = ConnectionModel.connect(mangaWebData.getUrl());

		mangaWebData.setWebDatas(fetchChaptersUrl());

		return mangaWebData;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {
		// TODO Auto-generated method stub

		response = ConnectionModel.connect(itemWebData.getUrl());
		
		ChapterWebData chapterWebData = (ChapterWebData) itemWebData;

		chapterWebData = fetchPagesUrl(chapterWebData);

		return chapterWebData;
	}

}
