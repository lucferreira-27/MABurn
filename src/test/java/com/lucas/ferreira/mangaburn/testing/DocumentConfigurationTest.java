package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.ConfigurationReaderModel;
import com.lucas.ferreira.maburn.model.DocumentConfigurationModel;
import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.enums.Category;

public class DocumentConfigurationTest {
	private ConfigurationReaderModel configReader;
	private DocumentConfigurationModel docConfig;

	@Before
	public void setUp() {
		configReader = new ConfigurationReaderModel();
		docConfig = new DocumentConfigurationModel(configReader.getDocumentConfiguration());
	}

	@Test
	public void getPath() {
		String mangaPath = docConfig.getPath(Category.MANGA);
		String animePath = docConfig.getPath(Category.ANIME);

		assertThat(mangaPath, is(equalTo("D:\\MABurnTest\\Mangas")));
		assertThat(animePath, is(equalTo("D:\\MABurnTest\\Animes")));

		mangaPath = docConfig.getItemPath(new Manga());
		animePath = docConfig.getItemPath(new Anime());

		assertThat(mangaPath, is(equalTo("D:\\MABurnTest\\Mangas")));
		assertThat(animePath, is(equalTo("D:\\MABurnTest\\Animes")));

	}

	@Test
	public void setPath() {
		docConfig.setPath("D:\\MABurnTest\\Mangas\\Test", Category.MANGA);
		docConfig.setPath("D:\\MABurnTest\\Animes", Category.ANIME);

		String mangaPath = docConfig.getPath(Category.MANGA);
		String animePath = docConfig.getPath(Category.ANIME);

		assertThat(mangaPath, is(equalTo("D:\\MABurnTest\\Mangas\\Test")));
		assertThat(animePath, is(equalTo("D:\\MABurnTest\\Animes")));

	}
}
