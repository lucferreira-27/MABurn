package com.lucas.ferreira.maburn.model.service;

import java.util.List;

import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;

public interface Database {
	
	public CollectDatas read(String querry,Category category);
	public List<CollectDatas> readAll(String querry,Category category);
	public CollectDatas read(int id,Category category);
}
