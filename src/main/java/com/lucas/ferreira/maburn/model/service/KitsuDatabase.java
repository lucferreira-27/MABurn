package com.lucas.ferreira.maburn.model.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.service.response.KitsuResponseAPI;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class KitsuDatabase implements Database {

	KitsuResponseAPI kitsuApi;
	
	@Override
	public CollectDatas read(String querry,Category category) {
		CustomLogger.log("Read - KitsuDatabase - " +"\nquerry: " +querry + " category: " + category);

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
		try {
			querry = URLEncoder.encode(querry, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		kitsuApi = new KitsuResponseAPI(url + querry);
		CollectDatas datas =  kitsuApi.fetchFirst();
		
		return datas;

	}
	@Override
	public List<CollectDatas> readAll(String querry,Category category) {
		CustomLogger.log("ReadAll - KitsuDatabase - " +"\nquerry: " +querry + " category: " + category);
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
		try {
			querry = URLEncoder.encode(querry, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		kitsuApi = new KitsuResponseAPI(url + querry);
		List<CollectDatas> datas =  kitsuApi.fetchAll();
		return datas;

	}
	@Override
	public CollectDatas read(int id,Category category) {
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
		
		String url = "https://kitsu.io/api//edge/"+type+"/";
		// TODO Auto-generated method stub
		kitsuApi = new KitsuResponseAPI(url + id);
		CollectDatas datas =  kitsuApi.fetchFirst();
		return datas;

	}



}
