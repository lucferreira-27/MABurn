package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
import com.lucas.ferreira.maburn.model.enums.Category;

public class DocumentConfigurationTest {
	private ConfigurationReader configReader;
	private DocumentConfiguration docConfig;

	@Before
	public void setUp() {
		configReader = new ConfigurationReader();
		docConfig = new DocumentConfiguration(configReader.getDocumentConfiguration());
	}

	@Test
	public void getPath() {
		String mangaPath = docConfig.getPath(Category.MANGA);
		String animePath = docConfig.getPath(Category.ANIME);

		assertThat(mangaPath, is(equalTo("D:\\MABurnTest\\Mangas")));
		assertThat(animePath, is(equalTo("D:\\MABurnTest\\Animes")));

		mangaPath = docConfig.getItemPath(new MangaDownloaded());
		animePath = docConfig.getItemPath(new AnimeDownloaded());

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
