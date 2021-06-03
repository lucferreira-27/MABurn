package com.lucas.ferreira.maburn.controller.title.download;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleFetcher;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class FetchActionAutomatic implements Callable<TitleScraped>{
	
	
	private RegisterTitleFetcher registerTitleFetcher;
	private RegisterTitleSearcher registerTitleSearcher;
	private FetchableTittle  fetchableTittle;
	
	
	public FetchActionAutomatic(FetchableTittle  fetchableTittle, RegisterTitleFetcher registerTitleFetcher, RegisterTitleSearcher registerTitleSearcher) {
		this.registerTitleFetcher = registerTitleFetcher;
		this.registerTitleSearcher = registerTitleSearcher;
		this.fetchableTittle  = fetchableTittle;
	}
	
	
	public TitleScraped automaticFetch() throws Exception {
		
		registerTitleSearcher.searchNow(fetchableTittle);
		
		TitleScraped titleScraped =  registerTitleFetcher.fetch(fetchableTittle);
		return titleScraped;

	}

	@Override
	public TitleScraped call() throws Exception {
		// TODO Auto-generated method stub
		
		
		return automaticFetch();
	}

}
