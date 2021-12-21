package com.lucas.ferreira.maburn.model.download;

public enum FileTypeAccept {
	MP4("mp4"),
	TS("ts"),
	JPG("jpg"), 
	JPEG("jpeg"), 
	PNG("png"),
	ZIP("zip");

	
	private String name;
	
	FileTypeAccept(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
