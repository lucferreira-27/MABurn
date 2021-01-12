package com.lucas.ferreira.maburn.model.databases;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;

public interface Database {
	
	public CollectDatas read(String querry,Category category);
	public List<CollectDatas> readAll(String querry,Category category);
	public CollectDatas read(int id,Category category);
}
