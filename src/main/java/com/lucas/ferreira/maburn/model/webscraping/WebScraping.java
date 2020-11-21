package com.lucas.ferreira.maburn.model.webscraping;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.Sites;

public interface WebScraping {
	

	public TitleWebData fecthTitle(TitleWebData titleWebData);
	
	public ItemWebData fecthItem(ItemWebData itemWebData);

	public List<SearchTitleWebData> fetchSearchTitle(String querry);

	
	public Sites getSite();
}
