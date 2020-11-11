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

import com.lucas.ferreira.maburn.model.CollectionDatasReaderModel;
import com.lucas.ferreira.maburn.model.DocumentCollectionReaderModel;
import com.lucas.ferreira.maburn.model.bean.Anime;
import com.lucas.ferreira.maburn.model.bean.Manga;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class DocumentCollectionReaderTest {
	private DocumentCollectionReaderModel doc;
	private CollectionDatasReaderModel reader;
	private CollectionItem item;

	@Before
	public void setUp() {
		reader = new CollectionDatasReaderModel();
		doc = new DocumentCollectionReaderModel(reader.getDocumentCollectionDates());
	}

	@Test
	public void getItemsInDocumentTest() {
		List<CollectionItem> itens = doc.getItemsInDocumentByCategory("anime");
		assertThat(itens.size(), is(not(0)));

	}

	@Test
	public void getElementByCollectionItemTest() {
		CollectionItem itemManga = new Manga();
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
		Anime anime = new Anime();


		anime.setDestination("D:\\AnimeBurn\\Maou Gakuin no Futekigousha – Todos os Episódios");
		anime = (Anime) doc.getItemInDocument(anime);

		System.out.println(anime.getName());

		doc.editElementById(6, "title", "Maou Gakuin no Futekigousha – Todos os Episódios", "Anime");

		anime = (Anime) doc.getItemInDocument(anime);

		System.out.println(anime.getName());
	}

}
