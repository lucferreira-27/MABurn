package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.util.List;

public abstract class TitleWebData {
	protected boolean fetched;

	public abstract void setUrl(String url);
	public abstract List<ItemWebData> getWebDatas();
	
	public boolean isFetched() {
		return fetched;
	}

	public void setFetched(boolean bool) {
		this.fetched = bool;
	}
	
	
}
