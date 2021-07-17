package com.lucas.ferreira.maburn.model.enums;

public enum Category {
	ANIME("Episode "), MANGA("Chapter: "), UNDEFINED("UNDEFINED ");
	private String namedItem;
	private Category(String namedItem) {
		
		this.namedItem  = namedItem;
	}
	
	public String getNamedItem() {
		return namedItem;
	}




}
