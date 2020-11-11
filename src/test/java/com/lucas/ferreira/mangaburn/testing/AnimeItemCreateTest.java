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
//import com.lucas.ferreira.maburn.model.bean.Anime;
//import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
//import com.lucas.ferreira.maburn.model.itens.AnimeItemCreate;
//
//public class AnimeItemCreateTest {
//	private AnimeItemCreate animeItemCreate;
//	private AnimeCollection animeCollection;
//
//	@Before
//	public void setUp() {
//		animeCollection = new AnimeCollection();
//		animeCollection.setDestination("test");
//		animeItemCreate = new AnimeItemCreate(animeCollection);
//	}
//
//	@Test
//	public void createItem() {
//		List<Anime> animes = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			Anime a = animeItemCreate.createItem("//Test");
//			animes.add(a);
//			animeCollection.getListAnimes().add(a);
//		}
//		Anime lastA = animeCollection.getListAnimes().get(animeCollection.getListAnimes().size() - 1);
//		assertThat(lastA.getId(), is(equalTo(animeCollection.getListAnimes().size()))); //Test creation of IDs
//
//	}
//}
