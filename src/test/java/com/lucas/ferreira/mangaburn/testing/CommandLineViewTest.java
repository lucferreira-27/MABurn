//package com.lucas.ferreira.mangaburn.testing;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.lucas.ferreira.maburn.controller.CommandLineController;
//import com.lucas.ferreira.maburn.model.bean.Anime;
//import com.lucas.ferreira.maburn.model.bean.Manga;
//import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
//import com.lucas.ferreira.maburn.model.collections.Collections;
//import com.lucas.ferreira.maburn.model.collections.MangaCollection;
//import com.lucas.ferreira.maburn.model.itens.CollectionItem;
//import com.lucas.ferreira.maburn.model.loader.MainLoader;
//import com.lucas.ferreira.maburn.model.loader.SlowAnimeCollectionLoader;
//import com.lucas.ferreira.maburn.model.loader.SlowMangaCollectionLoader;
//import com.lucas.ferreira.maburn.view.CommandLineView;
//import com.lucas.ferreira.maburn.view.View;
//
//public class CommandLineViewTest {
//	private static final String MANGA_LOCAL = "D:\\MABurnTest\\Mangas";
//	private static final String ANIME_LOCAL = "D:\\MABurnTest\\Animes";
//	private View view;
//	private CommandLineController controller;
//	private SlowMangaCollectionLoader slowMangaLoader;
//	private SlowAnimeCollectionLoader slowAnimeLoader;
//
//	private MangaCollection collection;
//	private Collections collections;
//	private MainLoader loader;
//
//	@Before
//	public void setUp() {
//		slowMangaLoader = new SlowMangaCollectionLoader();
//		slowAnimeLoader = new SlowAnimeCollectionLoader();
//		view = new CommandLineView();
//
//	}
//
//	@Test
//	public void informMangaInColletionTest() {
//		CustomLogger.log("TEST - informItensInColletion");
//		collections = instanceCollectionsManga();
//		view.informItensInColletion(collections);
//		
//	}
//	@Test
//	public void informAnimeInColletionTest() {
//		CustomLogger.log("TEST - informItensInColletion");
//		collections = instanceCollectionsAnime();
//		view.informItensInColletion(collections);
//		
//	}
//	@Test
//	public void informChaptersInMangaTest() {
//		collections = instanceCollectionsManga();
//
//		CustomLogger.log("TEST - informChaptersInMangaTest");
//
//		int mangaIndex = 4;
//		Manga manga = (Manga) loader.loadSelectItem(mangaIndex);
//		view.informChaptersInManga(manga);
//	}
//	@Test
//	public void informEpisodesInAnimeTest() {
//		collections = instanceCollectionsAnime();
//
//		CustomLogger.log("TEST - informEpisodesInAnimeTest");
//
//		int episodeIndex = 1;
//		Anime anime = (Anime) loader.loadSelectItem(episodeIndex);
//		view.informEpisodesInAnime(anime);
//	}
//	@Test
//	public void definePath() {
//		String anime = view.definePath("Anime");
//		String manga = view.definePath("Manga");
//		assertThat(anime, is(equalTo("Test1")));
//		assertThat(manga, is(equalTo("Test2")));
//	}
//	
//	private Collections  instanceCollectionsManga() {
//		Collections collections = new MangaCollection();
//		loader = new MainLoader(slowMangaLoader);
//		collections.setDestination(MANGA_LOCAL);
//		try {
//			collections =  (Collections) loader.loadCollection(collections.getDestination()).get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
//		return collections;
//	}
//	private Collections  instanceCollectionsAnime() {
//		Collections collections = new AnimeCollection();
//		loader = new MainLoader(slowAnimeLoader);
//		collections.setDestination(ANIME_LOCAL);
//		try {
//			collections =  (Collections) loader.loadCollection(collections.getDestination()).get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
//		return collections;
//	}
//
//	
//	
//}
