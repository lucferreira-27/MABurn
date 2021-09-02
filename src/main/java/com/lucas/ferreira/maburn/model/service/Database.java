package com.lucas.ferreira.maburn.model.service;

import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;

import java.util.List;

public interface Database {
	
	CollectDatas read(String query, Category category);
	List<CollectDatas> readAll(String query, Category category);
	CollectDatas read(int id, Category category);
}
