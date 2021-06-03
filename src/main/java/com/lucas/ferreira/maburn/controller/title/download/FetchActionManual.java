package com.lucas.ferreira.maburn.controller.title.download;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleFetcher;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

public class FetchActionManual  implements Callable<TitleScraped>{
	
	
	private RegisterTitleFetcher registerTitleFetcher;
	private ManualSearchAlertController manualSearchAlertController;
	private FetchableTittle  fetchableTittle;
	
	
	public FetchActionManual(FetchableTittle  fetchableTittle, RegisterTitleFetcher registerTitleFetcher, ManualSearchAlertController manualSearchAlertController) {
		this.registerTitleFetcher = registerTitleFetcher;
		this.manualSearchAlertController = manualSearchAlertController;
		this.fetchableTittle  = fetchableTittle;
	}
	
	
	public TitleScraped manualFetch() throws Exception {
		
		 String response = manualSearchAlertController.invokeAlert();
			if(response == null) {
				return null;
			}
		
			fetchableTittle.setTitleUrl(response);
		
		TitleScraped titleScraped =  registerTitleFetcher.fetch(fetchableTittle);
		return titleScraped;

	}

	@Override
	public TitleScraped call() throws Exception {
		// TODO Auto-generated method stub
		
		
		return manualFetch();
	}

}
