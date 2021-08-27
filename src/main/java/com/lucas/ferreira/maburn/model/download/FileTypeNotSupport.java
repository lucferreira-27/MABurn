package com.lucas.ferreira.maburn.model.download;

public enum FileTypeNotSupport {
	MPEG_URL("vnd.apple.mpegurl");

	private String name;

	FileTypeNotSupport(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
