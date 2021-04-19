package com.lucas.ferreira.maburn.model.loader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.Category;

public class CollectionCheck {

	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
	private XmlConfigurationOrchestrator configurationOrchestrator = new XmlConfigurationOrchestrator();

	private ConfigForm config;
	private CollectionForm form;
	private boolean hasNewItem = false;

	public boolean hasNewItemCollecetion(Category category) {
		try {

			form = orchestrator.read();

			List<ListItemForm> filterResult = form.getItems().stream()
					.filter(checkItem -> checkItem.getCategory() == category).collect(Collectors.toList());

			config = configurationOrchestrator.read();
			String destination = null;
			if (category == Category.ANIME)
				destination = config.getAnimeConfig().getCollectionDestination();
			else
				destination = config.getMangaConfig().getCollectionDestination();

			File folder = new File(destination);

			List<File> listFolders = Arrays
					.asList(folder.listFiles((file, name) -> new File(file, name).isDirectory()));

			int findFoldersSize = listFolders.size();
			int filterResultSize = filterResult.size();

			if (findFoldersSize > filterResultSize) {
				return true;
			}
			return false;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hasNewItem;
	}

	public List<File> getNewItemsCollection() {
		return null;
	}
}
