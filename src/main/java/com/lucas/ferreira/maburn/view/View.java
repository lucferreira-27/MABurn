package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;

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
