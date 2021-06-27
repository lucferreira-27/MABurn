package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

public enum FetchCardState {
	
	IN_QUEUE("IN QUEUE"),
	WORKING("WORKING"),
	READY("READY"),
	ERROR("ERROR");
	
	private String name;
	
	FetchCardState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
}
