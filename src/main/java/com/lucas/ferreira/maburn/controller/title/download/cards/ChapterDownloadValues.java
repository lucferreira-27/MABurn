package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;

public class ChapterDownloadValues  implements GroupDownloadValues{
	
	
	private String name;
	private DoubleProperty downloadProgress;
	private List<ItemDownloadValues> pagesDownloadValues = new ArrayList<ItemDownloadValues>();
	
	
	public ChapterDownloadValues(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public DoubleProperty getDownloadProgress() {
		return downloadProgress;
	}

	@Override
	public List<ItemDownloadValues> listItemsDownloadValues() {
		// TODO Auto-generated method stub
		return pagesDownloadValues;
	}




		
	

}
