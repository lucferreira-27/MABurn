package com.lucas.ferreira.maburn.model.dao.webdatas;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;

public abstract class TitleWebData {
	protected boolean fetched;

	public abstract void setUrl(String url);
	public abstract Sites getSite();
	
	public boolean isFetched() {
		return fetched;
	}

	public void setFetched(boolean bool) {
		this.fetched = bool;
	}
	
	
	
	
}
