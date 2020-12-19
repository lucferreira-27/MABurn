package com.lucas.ferreira.maburn.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.ConfigurationReader;
import com.lucas.ferreira.maburn.model.documents.DocumentConfiguration;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.view.View;

public class CommandLineController {
	private View view;

	private FolderReaderModel reader = new FolderReaderModel();
	private MainLoader mainLoader;
	private Collections collection;
	private CollectionLoader collectionLoader;
	private ConfigurationReader configRedaer = new ConfigurationReader();
	private DocumentConfiguration doc;
	private String session = null;
	private Integer action = null;
	private InitializeModel initialize;

	public CommandLineController(View view, CollectionLoader collectionLoader, Collections collection) {
		// TODO Auto-generated constructor stub
		this.view = view;
		this.collectionLoader = collectionLoader;
		this.collection = collection;
		this.initialize = new InitializeModel(view);
		initialize.boot();
		this.doc = new DocumentConfiguration(configRedaer.getDocumentConfiguration());

	}

	private void initizialte() {
		definePaths();

	}

	public void run() {
		int option = 0;
		while (true) {
			option = sessionMenu();
			if (option == 1) {
				sessionSelectCollection();
				sessionSelectItem();
			}
			if (option == 2) {
				int optionEdit = sessionEditMenu();
				if (optionEdit == 1) {
					sessionEditCollectionDates();
				}
				if (optionEdit == 2) {
					sessionEditConfiguration();
				}
			} else if (option == 3) {
				break;
			}
		}
	}

	private void sessionEditConfiguration() {
		definePaths();

	}

	private void sessionEditCollectionDates() {

	}

	public int sessionEditMenu() {
		return view.showEditMenu();
	}

	public int sessionMenu() {
		return view.showMenu();
	}

	public void sessionSelectItem() {
		int index = showItens();
		if (index == -1)
			return;
		selectItem(index);
	}

	public void sessionSelectCollection() {
		selectCollection();
	}

	private void definePaths() {
		doc.setPath(view.definePath("Anime"), Category.ANIME);
		doc.setPath(view.definePath("Manga"), Category.MANGA);
		// view.definePath("Anime");
		// view.definePath("Manga");
	}

	public void selectCollection() {
		Category categoryCollection = view.selectCollection(); // Get select collection category in command line
		mainLoader = new MainLoader(categoryCollection);
		try {
			collection = (Collections) mainLoader.loadCollection(doc.getPath(categoryCollection)).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // loaded collection items

	}

	public int showItens() {
		List<CollectionItem> itens = (List<CollectionItem>) collection.getItens();
		int index = view.informItensInColletion(collection);
		return index;
	}

	public void selectItem(int index) {

		CollectionItem item = mainLoader.loadSelectItem(index);
		if (item instanceof MangaDownloaded) {
			MangaDownloaded manga = (MangaDownloaded) item;
			action = view.informChaptersInManga(manga);
		} else if (item instanceof AnimeDownloaded) {
			AnimeDownloaded anime = (AnimeDownloaded) item;
			action = view.informEpisodesInAnime(anime);
		}

	}

}
