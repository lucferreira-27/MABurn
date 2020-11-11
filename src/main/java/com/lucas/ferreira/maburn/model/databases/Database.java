package com.lucas.ferreira.maburn.model.databases;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;

public interface Database {
	
	public CollectDatas read(String url,Category category);
	public CollectDatas read(String url, int size);
}
