package com.lucas.ferreira.maburn.model.download;

public class DownloadFilename {
	
	
	private String name;
	private String path;
	
	public DownloadFilename(String path,String name) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
