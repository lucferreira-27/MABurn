package com.lucas.ferreira.maburn.model.databases;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.webscraping.databases.myanimelist.MyAnimeListScraping;

public class MyAnimeListDatabase implements Database {
	
	MyAnimeListScraping malScraping;
	
	@Override
	public CollectDatas read(String url, Category anime) {
		// TODO Auto-generated method stub
		malScraping = new MyAnimeListScraping(url);
		return null;
	}

	@Override
	public CollectDatas read(String url, int size) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public CollectDatas read(int id, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

}
