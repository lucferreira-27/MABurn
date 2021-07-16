package com.lucas.ferreira.maburn.controller.title.download.title;

import com.lucas.ferreira.maburn.controller.title.TitleController;
import com.lucas.ferreira.maburn.controller.title.download.register.OrganizeFetchResult;
import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

public class Title implements Initialize{
	
	private TitleController titleController;
	private String textTitle;
	private Category category;
	private TaggedItems taggedItems;
	private CollectionTitle collectionTitle;
	private TitleDownload titleDownload;
	
	public Title(TitleDownload titleDownload) {
		this.titleDownload = titleDownload;
	}
	
	public void initialize() {
		titleController = (TitleController) Navigator.getMapNavigator().get(Interfaces.TITLE);
		if (titleController == null || titleController.getTitle() == null) {
			throw new IllegalAccessError("Title can't be null");
		}
		category = titleController.getTitle().getCategory();
		collectionTitle = titleController.getTitle();
		textTitle = collectionTitle.getTitleDataBase();

		titleDownload.getLblMainTitle().setText(textTitle);
	}
	
	public Category getCategory() {
		return category;
	}
	public CollectionTitle getCollectionTitle() {
		return collectionTitle;
	}
	public String getTextTitle() {
		return textTitle;
	}
	public TaggedItems getTaggedItems() {
		return taggedItems;
	}
	public TitleDownload getTitleDownload() {
		return titleDownload;
	}
	public void setTaggedItems(TaggedItems taggedItems) {
		this.taggedItems = taggedItems;
	}
}
