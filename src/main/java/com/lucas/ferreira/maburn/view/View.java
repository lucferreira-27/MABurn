package com.lucas.ferreira.maburn.view;

import java.util.List;

import com.lucas.ferreira.maburn.model.bean.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.bean.MangaDownloaded;
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
	public int informChaptersInManga(MangaDownloaded manga);
	public int informEpisodesInAnime(AnimeDownloaded anime);
	public int editConfig();
	public int showEditMenu();
	
	
}
