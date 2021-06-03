package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;

public class OrganizeFetchResult {
	
	
	private final ComboBox<String> cbItems;
	private final ComboBox<Sites> cbSource;
	private FetchInSystem fetchInSystem = new FetchInSystem();
	private CollectionTitle collectionTitle;
	private final static String CHAPTER_CATEGORY_TAG = "Chapter ";  
	private final static String EPISODE_CATEGORY_TAG = "Episode ";  

	public OrganizeFetchResult(ComboBox<String> cbItems, ComboBox<Sites> cbSource, CollectionTitle collectionTitle) {
		this.cbItems = cbItems;
		this.cbSource = cbSource;
		this.collectionTitle = collectionTitle;
	}
	
	public TaggedItems organizeAndSaveFetch(TitleScraped titleScraped, Category category) {
		String tag = category == Category.ANIME ? EPISODE_CATEGORY_TAG : CHAPTER_CATEGORY_TAG;
		TaggedItems taggedItems = new TaggedItems(titleScraped.getItemsScraped(), tag);
		taggedItems.tagItems();

		Platform.runLater(() -> cbItems.getItems().setAll(taggedItems.getKeyItems()));
		
		cbItems.setDisable(false);

		saveFetchInSystem(titleScraped);
		return taggedItems;
		
	}
	public void saveFetchInSystem(TitleScraped titleScraped) {
		fetchInSystem.save(collectionTitle, cbSource.getValue(), titleScraped.getTitleUrl());

	}
	
}
