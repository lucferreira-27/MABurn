package com.lucas.ferreira.maburn.model.items;

import com.lucas.ferreira.maburn.model.dao.CollectDatas;

public interface ItemCreater<T> {

	public T createItem(String destination);
	
	public T createSearchItem(CollectDatas data);



}
