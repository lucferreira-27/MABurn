package com.lucas.ferreira.maburn.model.databases;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.databases.response.KitsuResponseAPI;
import com.lucas.ferreira.maburn.model.enums.Category;

public class KitsuDatabase implements Database {

	KitsuResponseAPI kitsuApi;
	
	@Override
	public CollectDatas read(String querry,Category category) {
		String type = null;
		switch (category) {
		case ANIME:
			type = "anime";
			break;
		case MANGA:
			type = "manga";
		default:
			break;
		}  
		
		String url = "https://kitsu.io/api//edge/"+type+"?filter[text]=";
		// TODO Auto-generated method stub
		kitsuApi = new KitsuResponseAPI(url + querry);
		CollectDatas datas =  kitsuApi.fetchAll();
		return datas;

	}

	@Override
	public CollectDatas read(String url, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
