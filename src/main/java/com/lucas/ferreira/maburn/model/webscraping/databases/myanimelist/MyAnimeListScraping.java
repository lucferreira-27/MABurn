package com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.ConnectionModel;
import com.lucas.ferreira.maburn.model.webscraping.databases.WebScraping;
import com.lucas.ferreira.maburn.model.webscraping.databases.WebScrapingDatas;

public class MyAnimeListScraping implements WebScraping {
	private Document malDocument;
	private Scraper scraper = new Scraper();
	private WebScrapingDatas datas;
	private PagesTypes pageType;
	
	public MyAnimeListScraping(String url, PagesTypes pageType) {
		// TODO Auto-generated constructor stub
		try {
			this.pageType = pageType;
			System.out.println(url);
			this.malDocument = ConnectionModel.connect(url).parse();
		} catch (ConnectionException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new WebScrapingException(e.getMessage());
		} finally {

			if (pageType == PagesTypes.SEARCH_PAGE) {
				datas = (WebScrapingDatas) new MyAnimeListSearchDatas();
			} else if (pageType == PagesTypes.TITLE_PAGE) {
				datas = (WebScrapingDatas) new MyAnimeListTitleDatas();

			}
		}

	}

	public MyAnimeListScraping(String url) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public WebScrapingDatas fetchAll() throws WebScrapingException {
		// TODO Auto-generated method stub
		if (pageType.equals(PagesTypes.SEARCH_PAGE)) {
			fetchTextContentSearhPage();
			fetchHyperLinksSearhPage();
			return datas;
		}
		if (pageType.equals(PagesTypes.TITLE_PAGE)) {
			fetchTextContent();
			fetchImages();
			fetchHyperLinks();
			return datas;
		}
		return null;

	}

	public WebScrapingDatas fetchTextContentSearhPage() throws WebScrapingException {
		// TODO Auto-generated method stub
		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) fetchTitleSearchPage();

		return searchDatas;
	}

	public WebScrapingDatas fetchHyperLinksSearhPage() throws WebScrapingException {
		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) datas;
		Elements scrapeElements = scraper.scrapeSnippet(malDocument, "tr > td.borderClass > a.hoverinfo_trigger.fw-b");
		scrapeElements.forEach(a -> searchDatas.getTitlesHyperLinks().add(a.attr("href")));
		return searchDatas;
	}

	@Override
	public WebScrapingDatas fetchImages() throws WebScrapingException {
		// TODO Auto-generated method stub
		fetchImageCover();
		return datas;

	}

	public WebScrapingDatas fetchTitleSearchPage() {
		MyAnimeListSearchDatas searchDatas = (MyAnimeListSearchDatas) datas;
		Elements scrapeElements = scraper.scrapeSnippet(malDocument, "tr > td.borderClass > a.hoverinfo_trigger.fw-b");
		scrapeElements.forEach(text -> searchDatas.getTitles().add(text.text())); // add element text to the list of
																					// search data titles
		return searchDatas;
	}


	@Override
	public WebScrapingDatas fetchTextContent() throws WebScrapingException {
		// TODO Auto-generated method stub
		fetchTitle();
		try {
		fetchSynopsis();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return datas;

	}

	@Override
	public WebScrapingDatas fetchHyperLinks() throws WebScrapingException {
		// TODO Auto-generated method stub
		String webScrapingUrl = malDocument.baseUri();
		((MyAnimeListTitleDatas) datas).setHyperLink(webScrapingUrl);
		return datas;

	}

	public void fetchTitle() throws WebScrapingException {
		Elements elements;
		try {
			elements = scraper.scrapeSnippet(malDocument, "h1.title-name.h1_bold_none"); //If it is an anime it will work, otherwise  an exception will be thrown
		} catch (WebScrapingException e) {
			// TODO: handle exception
			elements = scraper.scrapeSnippet(malDocument, "[itemprop=name]"); //In this case it's a manga

		}
		elements.forEach(text -> ((MyAnimeListTitleDatas) datas).setTitle(text.text())); // add text from elements in
																							// title of data

	}

	public void fetchSynopsis() throws WebScrapingException {
		
		Elements elements = scraper.scrapeSnippet(malDocument, "[itemprop=description]");
		Element firstElement = elements.first();
		((MyAnimeListTitleDatas) datas).setSynopsis(firstElement.text());

	}

	public void fetchImageCover() throws WebScrapingException {

		Elements elements = scraper.scrapeSnippet(malDocument, "td.borderClass > div > div > a > img ");
		Element firstElement = elements.first();
		((MyAnimeListTitleDatas) datas).setImageSrc(firstElement.attr("data-src"));

	}

	@Override
	public WebScrapingDatas getDatas() {
		// TODO Auto-generated method stub
		return datas;
	}

}
