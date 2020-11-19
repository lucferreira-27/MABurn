package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;

public interface WebScraping {
	

	public TitleWebData fecthTitle(TitleWebData titleWebData);
	
	public ItemWebData fecthItem(ItemWebData itemWebData);

	
	
	
	public Object getSite();
}
