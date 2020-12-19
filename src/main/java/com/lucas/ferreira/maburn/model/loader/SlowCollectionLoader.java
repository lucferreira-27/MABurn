package com.lucas.ferreira.maburn.model.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import com.lucas.ferreira.maburn.exceptions.CollectionLoaderException;
import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.FolderReaderModel;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.documents.CollectionDatasReader;
import com.lucas.ferreira.maburn.model.documents.DocumentCollectionReader;
import com.lucas.ferreira.maburn.model.documents.ParseXMLDocument;
import com.lucas.ferreira.maburn.model.documents.SaveCollection;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.model.itens.ItemCreater;
import com.lucas.ferreira.maburn.model.itens.MangaItemCreate;

public class SlowCollectionLoader implements CollectionLoader {
	private FolderReaderModel reader;
	private Collections collection;
	private CollectionDatasReader dataReader;
	private ParseXMLDocument parse;
	private DocumentCollectionReader docCollectionReader;
	private SaveCollection save;
	private String destination;
	private ExecutorService exec;
	private Category category;

	public SlowCollectionLoader(Collections collections) {
		// TODO Auto-generated constructor stub
		this.collection = collections;
		init();
	}

	public SlowCollectionLoader(Category category) {
		// TODO Auto-generated constructor stub
		switch (category) {
		case ANIME:
			this.collection = new AnimeCollection();

			break;
		case MANGA:
			this.collection = new MangaCollection();

			break;
		default:
			break;
		}
		init();
	}

	public void initExecutorService() {
		exec = Executors.newFixedThreadPool(50, r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
	}

	private void init() {
		reader = new FolderReaderModel();
		dataReader = new CollectionDatasReader();
		docCollectionReader = new DocumentCollectionReader(dataReader.getDocumentCollectionDates());
		save = new SaveCollection(dataReader.getDocumentCollectionDates());
		initExecutorService();
	}

	@Override
	public Collections call() throws Exception {
		// TODO Auto-generated method stub

		loadCollection(destination, collection.getCategory());
		return collection;
	}

	@Override
	public Collections loadCollection(String destination, Category category) {
		// TODO Auto-generated method stub
		List<File> filesInFolder = new ArrayList<>();
		this.category = category;
		switch (category) {
		case ANIME:
			collection = new AnimeCollection();
			collection.setDestination(destination);
			filesInFolder = reader.findAnimeFoldersInAnimeCollectionFolder((AnimeCollection) collection);
			break;
		case MANGA:
			collection = new MangaCollection();
			collection.setDestination(destination);
			filesInFolder = reader.findMangaFoldersInMangaCollectionFolder((MangaCollection) collection);
			break;
		default:
			break;
		}

		try {
			addAllItensInCollection(collection, filesInFolder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CollectionLoaderException(e.getMessage());
		}
		return collection;

	}

	private void addAllItensInCollection(Collections collection, List<File> filesInFolder) {
		// TODO Auto-generated method stub

		List<Future<CollectionItem>> futureItens = new ArrayList<>();

		for (File itemFolder : filesInFolder) {
			// CollectionItem item = loadItem(itemFolder.getAbsolutePath(), collection);
			futureItens.add(exec.submit(new LoadItemRunnable(itemFolder.getAbsolutePath(), category)));
		}
		int itensDone = 0;

		while (itensDone < filesInFolder.size()) {
			itensDone = futureItens.stream().filter(futureItem -> futureItem.isDone()).collect(Collectors.toList())
					.size();

		}

		futureItens.forEach(futureItem -> {
			try {
				addItemInCollection(futureItem.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		saveNewCollectionItens();
	}

	private void saveNewCollectionItens() {

		List<CollectionItem> itens = (List<CollectionItem>) collection.getItens();
		List<CollectionItem> newItens = new ArrayList<>();

		newItens = itens.stream().filter(item -> !isCreateItemInDocument(item.getDestination(), item.getCategory()))
				.collect(Collectors.toList());
		for (CollectionItem item : newItens) {
			save.saveDatas(item);

		}

	}

	private void addItemInCollection(CollectionItem item) {
		List<CollectionItem> itens = (List<CollectionItem>) collection.getItens();
		itens.add(item);
		loadSubItensInItem(item);

	}

	private void loadSubItensInItem(CollectionItem item) {
		ItemContentLoader itemContentLoader = new ItemContentLoader(item);
		List<CollectionSubItem> subItens = itemContentLoader.getSubItens();
		item.setListSubItens(subItens);

	}




	private boolean isCreateItemInDocument(String itemPath, Category category) {
		synchronized (this) {

			if (!docCollectionReader.xmlContains(itemPath, category)) {
				return false;
			}
			return true;
		}
	}



	@Override
	public void loadAllItems() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadAllSubItems() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadItem(CollectionItem item) {
		// TODO Auto-generated method stub
		loadSubItensInItem(item);

	}

	@Override
	public void setDestination(String destination) {
		// TODO Auto-generated method stub
		this.destination = destination;

	}

}
