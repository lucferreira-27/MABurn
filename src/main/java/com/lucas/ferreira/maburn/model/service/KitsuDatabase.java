package com.lucas.ferreira.maburn.model.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.service.response.KitsuResponseAPI;

public class KitsuDatabase implements Database {

	private KitsuResponseAPI kitsuApi;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public CollectDatas read(String querry,Category category) {
		LOGGER.info(("Read - KitsuDatabase - " +"\nquerry: " +querry + " category: " + category));

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
			 
			e.printStackTrace();
		}
		

		kitsuApi = new KitsuResponseAPI(url + querry);
		CollectDatas datas =  kitsuApi.fetchFirst();
		
		return datas;

	}
	@Override
	public List<CollectDatas> readAll(String querry,Category category) {
		LOGGER.info("ReadAll - KitsuDatabase - " +"\nquerry: " +querry + " category: " + category);
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
			 
			e.printStackTrace();
		}
		
		
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
		
		kitsuApi = new KitsuResponseAPI(url + id);
		CollectDatas datas =  kitsuApi.fetchFirst();
		return datas;

	}



}
