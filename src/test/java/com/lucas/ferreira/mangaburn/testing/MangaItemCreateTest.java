//package com.lucas.ferreira.mangaburn.testing;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.lucas.ferreira.maburn.model.bean.Manga;
//import com.lucas.ferreira.maburn.model.collections.MangaCollection;
//import com.lucas.ferreira.maburn.model.itens.MangaItemCreate;
//
//public class MangaItemCreateTest {
//	private MangaItemCreate mangaItemCreate;
//	private MangaCollection mangaCollection;
//
//	@Before
//	public void setUp() {
//		mangaCollection = new MangaCollection();
//		mangaCollection.setDestination("test");
//		mangaItemCreate = new MangaItemCreate(mangaCollection);
//	}
//
//	@Test
//	public void createItem() {
//		List<Manga> mangas = new ArrayList<>();
//		Manga manga1 = mangaItemCreate.createItem("//Test");
//		for (int i = 0; i < 10; i++) {
//			Manga m = mangaItemCreate.createItem("//Test");
//			mangas.add(m);
//			mangaCollection.getListMangas().add(m);
//		}
//		Manga lastM = mangaCollection.getListMangas().get(mangaCollection.getListMangas().size() - 1);
//		assertThat(lastM.getId(), is(equalTo(mangaCollection.getListMangas().size()))); //Test creation of IDs
//
//	}
//}
