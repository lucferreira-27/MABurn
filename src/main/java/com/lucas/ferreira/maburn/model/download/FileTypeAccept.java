package com.lucas.ferreira.maburn.model.download;

public enum FileTypeAccept {
	MP4("mp4"),
	JPG("jpg"),
	PNG("png");
	
	private String name;
	
	FileTypeAccept(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
