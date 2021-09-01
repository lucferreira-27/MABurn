package com.lucas.ferreira.maburn.model.collections.management;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final ProgressIndicator sortCollectionLoad;
    private final ImageView loadImageLoadArea;

    public CollectionSearch(ProgressIndicator sortCollectionLoad, ImageView loadImageLoadArea) {

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
        LOGGER.info("Database Result Size: " + items.size());
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

                e1.printStackTrace();

                continue;
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        hideQuickLoad();

        return searchTable;

    }

}
