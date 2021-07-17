package com.lucas.ferreira.maburn.model.fetch;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.exceptions.NotURLFoundInRecover;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class FetchInSystem {
	FetchSave fetchSave = new FetchSave();
	FetchRecover fetchRecover = new FetchRecover();

	public void save(CollectionTitle collectionItem, Sites site, String url) {
		fetchSave.save(collectionItem, site, url);
	}

	public FetchableTittle recover(FetchableTittle fetchableTittle) throws NotURLFoundInRecover {
		
		String recoverFetch = fetchRecover.recover(fetchableTittle.getCollectionTitle(), fetchableTittle.getSourceSelect());
		fetchableTittle.setTitleUrl(recoverFetch);
		if(recoverFetch == null) {
			throw new NotURLFoundInRecover();
		}
		return fetchableTittle;
	}

}
