package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.model.fetch.FetchInSystem;

public class FetchActionRecover implements FetchAction{
	
	
	private FetchInSystem fetchInSystem = new FetchInSystem();
	
	
	
	
	public FetchableTittle recoverFetch(FetchableTittle fetchableTittle) throws Exception {
		
		return fetchInSystem.recover(fetchableTittle);


	}


	@Override
	public FetchableTittle action(FetchableTittle fetchableTittle) throws Exception {
		// TODO Auto-generated method stub
		return recoverFetch(fetchableTittle);
	}

}
