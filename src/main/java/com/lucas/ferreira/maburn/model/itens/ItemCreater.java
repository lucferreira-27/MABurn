package com.lucas.ferreira.maburn.model.itens;

import com.lucas.ferreira.maburn.model.bean.CollectDatas;

public interface ItemCreater<T> {

	public T createItem(String destination);
	
	public T createSearchItem(CollectDatas data);



}
