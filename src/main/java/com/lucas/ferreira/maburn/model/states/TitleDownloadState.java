package com.lucas.ferreira.maburn.model.states;

import com.lucas.ferreira.maburn.model.fetch.Fetch;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;

public class TitleDownloadState implements StateAdapter {
	private Fetch fetch;
	private CollectionTitle item;
	
	@Override
	public StateAdapter getState()
	// TODO Auto-generated method stub
	{
		return this;
	}

}
