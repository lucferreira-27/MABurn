package com.lucas.ferreira.maburn.model.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.folder.FolderCollectionItemLoader;
import com.lucas.ferreira.maburn.model.loader.folder.FolderCollectionLoader;

public class LoaderOrchestrator {

	private FolderCollectionLoader collectionLoader = new FolderCollectionLoader();
	private FolderCollectionItemLoader collectionItemLoader = new FolderCollectionItemLoader();
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();


	public void loadCollection(String destination, Category category) {

		
		
		collectionLoader.loadCollection(destination, category);

	}

	public void loadCollectionItem(Collections collection) {
		String dest = collection.getActualItem().getForm().getDestination();
		collectionItemLoader.loadCollectionItems(dest, collection.getCategory());

	}

	public void loadCollectionItem(CollectionItem item) {
		String dest = item.getForm().getDestination();
		collectionItemLoader.loadCollectionItems(dest, item.getCategory());

	}
}
