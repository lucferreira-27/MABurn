package com.lucas.ferreira.maburn.view;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Chapter;
import com.lucas.ferreira.maburn.model.bean.Episode;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;

public interface View {
	public int showMenu();
	public Category selectCollection();
	public String definePath(String category);
	public int selectItemInCollection();
	public int informItensInColletion(Collections collections);
	public int informChaptersInManga(Manga manga);
	public int informEpisodesInAnime(Anime anime);
	public int editConfig();
	public int showEditMenu();
	
	
}
