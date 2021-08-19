package com.lucas.ferreira.maburn.model.fetch;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;
import com.lucas.ferreira.maburn.exceptions.NotURLFoundInRecover;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class FetchInSystem {
	FetchSave fetchSave = new FetchSave();
	FetchRecover fetchRecover = new FetchRecover();
	FetchReplacer fetchReplacer = new FetchReplacer();

	public void save(SaveData saveData) {
		fetchSave.save(saveData );
	}
	public void replace(SaveData saveData) {
		fetchReplacer.replace(saveData);
	}
	public boolean hasItemWithUrl(SaveData saveData) {
		return fetchRecover.recoverWithUrl(saveData) != null;

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
