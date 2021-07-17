package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.controller.title.download.register.RegisterTitleSearcher;

public class FetchActionAutomatic implements FetchAction{
	
	
	private RegisterTitleSearcher registerTitleSearcher;
	
	
	public FetchActionAutomatic(RegisterTitleSearcher registerTitleSearcher) {
		this.registerTitleSearcher = registerTitleSearcher;
	}
	
	
	public FetchableTittle automaticFetch(FetchableTittle  fetchableTittle) throws Exception {
	
		
		registerTitleSearcher.searchNow(fetchableTittle);
		
		return fetchableTittle;


	}



	@Override
	public FetchableTittle action(FetchableTittle  fetchableTittle) throws Exception {
		
		return automaticFetch(fetchableTittle);
	}

}
