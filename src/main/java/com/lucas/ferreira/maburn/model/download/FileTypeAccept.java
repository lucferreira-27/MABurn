package com.lucas.ferreira.maburn.model.download;

public enum FileTypeAccept {
	MP4("mp4"),
	JPG("jpg"), 
	JPEG("jpeg"), 
	PNG("png");
	
	private String name;
	
	FileTypeAccept(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
