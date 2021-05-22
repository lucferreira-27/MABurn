package com.lucas.ferreira.maburn.model.states;

import com.lucas.ferreira.maburn.fetch.Fetch;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

public class TitleDownloadState implements StateAdapter {
	private Fetch fetch;
	private CollectionItem item;
	
	@Override
	public StateAdapter getState()
	// TODO Auto-generated method stub
	{
		return this;
	}

}
