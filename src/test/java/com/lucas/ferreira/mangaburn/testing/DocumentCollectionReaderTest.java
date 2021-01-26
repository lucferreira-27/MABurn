package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class DocumentCollectionReaderTest {
	private DocumentCollectionReader doc;
	private CollectionDatasReader reader;
	private CollectionItem item;

	@Before
	public void setUp() {
		reader = new CollectionDatasReader();
		doc = new DocumentCollectionReader(reader.getDocumentCollectionDates());
	}

	@Test
	public void getItemsInDocumentTest() {
		List<CollectionItem> itens = doc.getItemsInDocumentByCategory("anime");
		assertThat(itens.size(), is(not(0)));

	}

	@Test
	public void getElementByCollectionItemTest() {
		CollectionItem itemManga = new MangaDownloaded();
		itemManga.setDestination("D:\\MangaBurnTest\\Naruto");

		Element itemMangaElement = doc.getElementByCollectionItem(itemManga);

		assertNotNull(itemMangaElement);
	}

	@Test
	public void xmlContainsTest() {

		boolean result = doc.xmlContains("D:\\AnimeBurn\\Dragon Quest  Dai no Daibouken (2020)");
		assertTrue(result);
	}

	@Test
	public void xmlContainsCategoryTest() {

		boolean result = doc.xmlContains("D:\\AnimeBurn\\Dragon Quest  Dai no Daibouken (2020)", Category.ANIME);
		assertTrue(result);
		result = doc.xmlContains("D:\\MABurnTest\\Animes\\Dragon Ball", Category.ANIME);
		assertTrue(result);
	}

	@Test
	public void editElementByIdTest() {
		AnimeDownloaded anime = new AnimeDownloaded();


		anime.setDestination("D:\\AnimeBurn\\Maou Gakuin no Futekigousha – Todos os Episódios");
		anime = (AnimeDownloaded) doc.getItemInDocument(anime);

		CustomLogger.log(anime.getName());

		doc.editElementById(6, "title", "Maou Gakuin no Futekigousha – Todos os Episódios", "Anime");

		anime = (AnimeDownloaded) doc.getItemInDocument(anime);

		CustomLogger.log(anime.getName());
	}

}
