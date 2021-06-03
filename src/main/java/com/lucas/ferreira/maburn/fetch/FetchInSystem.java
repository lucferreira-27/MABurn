package com.lucas.ferreira.maburn.fetch;

import com.lucas.ferreira.maburn.exceptions.NotURLFoundInRecover;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class FetchInSystem {
	FetchSave fetchSave = new FetchSave();
	FetchRecover fetchRecover = new FetchRecover();

	public void save(CollectionTitle collectionItem, Sites site, String url) {
		fetchSave.save(collectionItem, site, url);
	}

	public String recover(CollectionTitle title, Sites site) throws NotURLFoundInRecover {
		
		String recoverFetch = fetchRecover.recover(title, site);
		if(recoverFetch == null) {
			throw new NotURLFoundInRecover();
		}
		return recoverFetch;
	}

}
