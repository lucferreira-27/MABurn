package com.lucas.ferreira.maburn.model.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.images.ThumbnailController;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.folder.FolderCollectionLoader;
import com.lucas.ferreira.maburn.model.service.KitsuDatabase;
import com.lucas.ferreira.maburn.util.FutureList;
import com.lucas.ferreira.maburn.util.LanguageReader;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public class DataFetcher extends Task<Collections> {
	private Category category;
	private XmlConfigurationOrchestrator orchestratorConfiguration = new XmlConfigurationOrchestrator();
	private XmlCollectionOrchestrator orchestratorCollection = new XmlCollectionOrchestrator();
	private FolderCollectionLoader folderCollectionLoader = new FolderCollectionLoader();
	private Collections collections = null;
	private ExecutorService executorService;
	private final BooleanProperty dataFetcherDoneProperty = new SimpleBooleanProperty(false);
	private final BooleanProperty readDoneProperty = new SimpleBooleanProperty(false);
	private final BooleanProperty writeDoneProperty = new SimpleBooleanProperty(false);
	private final IntegerProperty readCountProperty = new SimpleIntegerProperty();
	private final DoubleProperty readProgressProperty = new SimpleDoubleProperty();
	private final IntegerProperty goalProgressProperty = new SimpleIntegerProperty();
	private final IntegerProperty downloadImageProgressProperty = new SimpleIntegerProperty();
	private final StringProperty lblLoadFolderCollectionRead = new SimpleStringProperty();
	private final StringProperty lblLoadFolderItemRead = new SimpleStringProperty();
	private final StringProperty lblLoadDataBase = new SimpleStringProperty();

	private final StringProperty lblLoadDownlaodImage = new SimpleStringProperty();
	private ConfigForm config;
	private CollectionForm form;

	public DataFetcher(Category category) {
		this.category = category;
	}

	public void fetch() throws JsonParseException, JsonMappingException, IOException {

		Platform.runLater(() -> {
			stateProperty().addListener((obs, oldvalue, newvalue) -> {
				System.out.println("STATE: " + newvalue);
			});
		});
		config = orchestratorConfiguration.read();
		form = orchestratorCollection.read();

		if (category == Category.ANIME) {
			collections = new AnimeCollection();
			collections.setDestination(config.getAnimeConfig().getCollectionDestination());

			updateLblLoadFolderCollectionRead(config.getAnimeConfig().getCollectionDestination());

			collections = folderCollectionLoader.loadCollection(config.getAnimeConfig().getCollectionDestination(),
					category);

		} else if (category == Category.MANGA) {
			collections = new MangaCollection();
			collections.setDestination(config.getMangaConfig().getCollectionDestination());

			updateLblLoadFolderCollectionRead(config.getMangaConfig().getCollectionDestination());

			collections = folderCollectionLoader.loadCollection(config.getMangaConfig().getCollectionDestination(),
					category);
		}
		List<CollectionItem> newCollectionItems = null;

		if (form.getItems() != null)
			newCollectionItems = collections.getItens().stream().filter((item) -> {

				String itemDestination = item.getDestination();

				for (ListItemForm itemForm : form.getItems()) {
					if (itemDestination.equalsIgnoreCase(itemForm.getDestination())) {
						return false;
					}
				}
				return true;

			}).collect(Collectors.toList());
		else
			newCollectionItems = collections.getItens();
		if (newCollectionItems.size() <= 0) {
			goalProgressProperty.set(1);
			return;
		}
		if (newCollectionItems.size() <= 10) {
			executorService = Executors.newFixedThreadPool(newCollectionItems.size());
		} else {
			executorService = Executors.newFixedThreadPool(10);

		}
		goalProgressProperty.set(newCollectionItems.size());

		synchronizedDates(category, form, newCollectionItems);

	}

	private void synchronizedDates(Category category, CollectionForm form, List<CollectionItem> newCollectionItems) {
		List<Future<?>> fuList = new ArrayList<Future<?>>();

		KitsuDatabase database = new KitsuDatabase();
		Vector<ListItemForm> newItemForms = new Vector<ListItemForm>();

		readCountProperty.addListener((obs, oldvalue, newvalue) -> {
			updateMessage(newvalue + "/" + goalProgressProperty.get());
			updateLblLoadFolderItemRead(newvalue + "/" + goalProgressProperty.get());
			readProgressProperty.set((double) newvalue.intValue() / goalProgressProperty.get());
		});

		for (CollectionItem newCollectionItem : newCollectionItems) {
			System.out.println(newCollectionItem.getName());
			Thread fetch = new Thread(() -> {

				updateLblLoadDataBase(newCollectionItem.getName());

				CollectDatas datas = database.read(newCollectionItem.getName(), category);
				ListItemForm itemForm = collectDatasToItemForm(category, datas);
				itemForm.setDestination(newCollectionItem.getDestination());

				ThumbnailController controller = new ThumbnailController(itemForm);
				try {
					String local = controller.download().getAbsolutePath();
					itemForm.setImageLocal(local);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				readCountProperty.set(readCountProperty.add(1).get());
				newItemForms.add(itemForm);
			});

			Future<?> future = executorService.submit(fetch);
			fuList.add(future);
		}

		FutureList<?> futureList = new FutureList<List<ListItemForm>>(fuList);

		new Thread(() -> {
			while (!futureList.isDone()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			readDoneProperty.set(true);
			if (form.getItems() != null)
				form.getItems().addAll(newItemForms);
			else
				form.setItem(newItemForms);
			try {
				orchestratorCollection.write(form);
				writeDoneProperty.set(true);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

		while (!writeDoneProperty.get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private ListItemForm collectDatasToItemForm(Category category, CollectDatas datas) {
		ListItemForm itemForm = new ListItemForm();
		itemForm.setCategory(category);
		itemForm.setId(datas.getId());
		itemForm.setTitleDatabase(datas.getCanonicalTitle());
		itemForm.setTitleEn(datas.getTitles().get("en"));
		itemForm.setTitleJaJp(datas.getTitles().get("ja_jp"));
		itemForm.setImageUrl(datas.getPosterImageLink("medium"));
		itemForm.setDataUrl(datas.getItemDataBaseUrl());
		return itemForm;
	}

//	private List<ItemForm> collectionItemsToItemForms(Collections collections) {
//		System.out.println("Collection Size: " + collections.getItens().size());
//		return collections.getItens().stream().map(CollectionItem::toForm).collect(Collectors.toList());
//	}

	public IntegerProperty getDownloadImageProgressProperty() {
		return downloadImageProgressProperty;
	}

	public IntegerProperty getReadCountProperty() {
		return readCountProperty;
	}

	public DoubleProperty getReadProgressProperty() {
		return readProgressProperty;
	}

	public IntegerProperty getGoalProgressProperty() {
		return goalProgressProperty;
	}

	private void updateLblLoadFolderCollectionRead(String value) {
		Platform.runLater(() -> {
			String lbl = LanguageReader.read("LABEL_PATH");
			lbl = lbl.replace("${value}", value);
			lblLoadFolderCollectionRead.set(lbl);
		});
	}

	private void updateLblLoadDataBase(String value) {
		Platform.runLater(() -> {
			String lbl = LanguageReader.read("LABEL_FETCH");
			lbl = lbl.replace("${value}", value);
			lblLoadDataBase.set(lbl);
		});
	}

	private void updateLblLoadFolderItemRead(String value) {
		Platform.runLater(() -> {
			String lbl = LanguageReader.read("LABEL_READ_ITEM");
			lbl = lbl.replace("${value}", value);
			lblLoadFolderItemRead.set(lbl);
		});
	}

	private void updateLblLoadDownlaodImage(String value) {
		Platform.runLater(() -> {
			String lbl = LanguageReader.read("LABEL_PATH");
			lbl = lbl.replaceAll("${value}", value);
			lblLoadFolderCollectionRead.set(lbl);
			lblLoadDownlaodImage.set(value);
		});
	}

	public StringProperty getLblLoadFolderCollectionRead() {
		return lblLoadFolderCollectionRead;
	}

	public StringProperty getLblLoadFolderItemRead() {
		return lblLoadFolderItemRead;
	}

	public StringProperty getLblLoadDataBase() {
		return lblLoadDataBase;
	}

	public StringProperty getLblLoadDownlaodImage() {
		return lblLoadDownlaodImage;
	}

	public BooleanProperty getReadDoneProperty() {
		return readDoneProperty;
	}

	public BooleanProperty getDataFetcherDoneProperty() {
		return dataFetcherDoneProperty;
	}

	public BooleanProperty getWriteDoneProperty() {
		return writeDoneProperty;
	}

	@Override
	protected Collections call() throws Exception {
		// TODO Auto-generated method stub
		try {
			fetch();

			CollectionForm form = orchestratorCollection.read();

			List<CollectionItem> list;

			list = filterCategoryItems(category, form);
			list = filterCollectionPath(collections.getDestination(), form);
			collections.setItens(list);

			dataFetcherDoneProperty.set(true);
			return collections;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(e);
		}

	}

	private List<CollectionItem> filterCategoryItems(Category category, CollectionForm form) {
		return form.getItems().stream().map(ListItemForm::toCollectionItem).filter(i -> i.getCategory() == category)
				.collect(Collectors.toList());
	}

	private List<CollectionItem> filterCollectionPath(String path, CollectionForm form) {
		return form.getItems().stream().map(ListItemForm::toCollectionItem).filter(i -> i.getDestination()
				.substring(0, i.getDestination().lastIndexOf("\\")).equalsIgnoreCase(collections.getDestination()))
				.collect(Collectors.toList());
	}

	public Collections getCollections() {
		return collections;
	}

}