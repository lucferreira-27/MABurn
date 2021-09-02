package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.model.fetch.SaveData;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrganizeFetchResult {

	private final ComboBox<String> cbItems;
	private final ComboBox<RegisteredSite> cbSource;
	private FetchInSystem fetchInSystem = new FetchInSystem();
	private CollectionTitle collectionTitle;
	private final static String CHAPTER_CATEGORY_TAG = "Chapter ";
	private final static String EPISODE_CATEGORY_TAG = "Episode ";

	public OrganizeFetchResult(ComboBox<String> cbItems, ComboBox<RegisteredSite> cbSource,
			CollectionTitle collectionTitle) {
		this.cbItems = cbItems;
		this.cbSource = cbSource;
		this.collectionTitle = collectionTitle;
	}

	public TaggedItems organizeAndSaveFetch(TitleScraped titleScraped, Category category) {
		String tag = category == Category.ANIME ? EPISODE_CATEGORY_TAG : CHAPTER_CATEGORY_TAG;
		titleScraped.getItemsScraped();
		TaggedItems taggedItems = new TaggedItems(removeUselessListMap(titleScraped.getItemsScraped()));
		cbItems.setCellFactory(param -> addCellFactory());
		Platform.runLater(() -> cbItems.getItems().setAll(taggedItems.getKeyItems()));

		cbItems.setDisable(false);

		saveFetchInSystem(titleScraped);
		return taggedItems;

	}
	
	public ListCell<String> addCellFactory(){
		IntegerProperty count = new SimpleIntegerProperty();
		return new ListCell<String>() {

           
           final Label label = new Label() {{
        	   setMaxWidth(300);
        	   setWrapText(true);
           }};


            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                count.set(count.get() + 1);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
		};
        
	}
	
	private Map<String, String> removeUselessListMap(Map<String, List<String>> map) {
		Map<String, String> newMap = new LinkedHashMap<String, String>();

		map.forEach((key, value) -> {
			newMap.put(key, value.get(0));
		});

		return newMap;

	}

	public void saveFetchInSystem(TitleScraped titleScraped) {
		SaveData saveData = new SaveData(collectionTitle, cbSource.getValue(), titleScraped.getTitleUrl());
		if (fetchInSystem.hasItemWithUrl(saveData)) {
			return;
		} else {
			fetchInSystem.save(saveData);
		}
	}

}
