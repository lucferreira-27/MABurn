package com.lucas.ferreira.maburn.model.itens;

import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Chapter;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.bean.Page;

public interface ItemCreater<T> {

	public T createItem(String destination);



}
