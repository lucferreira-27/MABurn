package com.lucas.ferreira.maburn.model.collections.management;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.dao.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.dao.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.effects.Card;
import com.lucas.ferreira.maburn.model.effects.SearchCard;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.items.AnimeItemCreate;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.items.ItemCreater;
import com.lucas.ferreira.maburn.model.items.MangaItemCreate;
import com.lucas.ferreira.maburn.model.service.Database;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;

public class CollectionSearch {

	private final ProgressIndicator sortCollectionLoad;
	private final ImageView loadImageLoadArea;

	public CollectionSearch(ProgressIndicator sortCollectionLoad, ImageView loadImageLoadArea) {
		// TODO Auto-generated constructor stub
		this.sortCollectionLoad = sortCollectionLoad;
		this.loadImageLoadArea = loadImageLoadArea;
	}

	public void showQuickLoad() {
		sortCollectionLoad.setVisible(true);
	}

	public void hideQuickLoad() {
		sortCollectionLoad.setVisible(false);

	}

	public GridPaneTable search(String querry, Collections collection, Category category) {

		GridPaneTable searchTable = new GridPaneTable();

		showQuickLoad();
		loadImageLoadArea.setVisible(true);
		System.out.println("Search " + querry);
		List<CollectionTitle> items = new ArrayList<CollectionTitle>();

		Database database = new KitsuDatabase();
		database.readAll(querry, category).forEach(data -> {

			switch (category) {

			case ANIME:

				ItemCreater<AnimeDownloaded> animeCreator = new AnimeItemCreate((AnimeCollection) collection);
				items.add((CollectionTitle) animeCreator.createSearchItem(data));
				break;
			case MANGA:
				ItemCreater<MangaDownloaded> mangaCreator = new MangaItemCreate((MangaCollection) collection);
				items.add((CollectionTitle) mangaCreator.createSearchItem(data));
				break;

			default:
				break;
			}
		});
		System.err.println("Database Result: " + items.size());
		for (CollectionTitle item : items) {

			ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);
			try {
				GridPaneCell cell = thumbnailLoader.onlineLoad();

				if (cell != null) {
					Card card = new SearchCard(cell);
					card.overlay();
					cell.getNode().setUserData(cell);
					searchTable.add(cell);

				}
			} catch (ThumbnailLoadException e1) {
				// TODO Auto-generated catch block

				e1.printStackTrace();

				continue;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		
		hideQuickLoad();
//		propertyStatus.set(CollectionStatus.COLLECTION_SEARCH);
//		Platform.runLater(() -> {
//			reloadTable(searchTable);
//
//			txtSearchBar.clear();
//			loadImageLoadArea.setVisible(false);
//			hideSortLoad();
//
//		});
		
		System.err.println("Search Table: " + searchTable.getCells().size());
		
		return searchTable;
		
	}

}
