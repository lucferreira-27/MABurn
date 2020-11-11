package com.lucas.ferreira.mangaburn.testing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.loader.SlowCollectionLoader;

public class SlowCollectionLoaderTest {
	SlowCollectionLoader collectionLoader;

	@Before
	public void setUp() {

		collectionLoader = new SlowCollectionLoader(Category.ANIME);
	}

//	@Test
	public void loadAnimeCollectionTest() {
		Collections collections = collectionLoader.loadCollection("D:\\AnimeBurn", Category.ANIME);
		assertNotNull(collections);
		
		
	}
	@Test
	public void loadMangaCollectionTest() {
		Collections collections = collectionLoader.loadCollection("D:\\UnionDownload\\host", Category.MANGA);
		assertNotNull(collections);
		
		
	}
}
