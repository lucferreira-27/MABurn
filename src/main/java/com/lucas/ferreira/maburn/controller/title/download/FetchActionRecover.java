package com.lucas.ferreira.maburn.controller.title.download;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.controller.title.download.register.OrganizeFetchResult;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleFetcher;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;
import com.lucas.ferreira.maburn.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class FetchActionRecover implements Callable<TitleScraped>{
	
	
	private RegisterTitleFetcher registerTitleFetcher;
	private FetchInSystem fetchInSystem;
	private FetchableTittle  fetchableTittle;
	
	
	public FetchActionRecover(FetchableTittle  fetchableTittle, RegisterTitleFetcher registerTitleFetcher, FetchInSystem fetchInSystem) {
		this.registerTitleFetcher = registerTitleFetcher;
		this.fetchableTittle  = fetchableTittle;
		this.fetchInSystem  = fetchInSystem;
	}
	
	
	public TitleScraped recoverFetch() throws Exception {
		
		String recoverUrl = fetchInSystem.recover(fetchableTittle.getCollectionTitle(), fetchableTittle.getSourceSelect());
		fetchableTittle.setTitleUrl(recoverUrl);
		
		TitleScraped titleScraped =  registerTitleFetcher.fetch(fetchableTittle);
		return titleScraped;

	}

	@Override
	public TitleScraped call() throws Exception {
		// TODO Auto-generated method stub
		
		
		return recoverFetch();
	}

}
