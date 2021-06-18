package com.lucas.ferreira.maburn.model.download;

public class DownloadInfo {
	private String filename;
	private String path;
	private String url;
	private String referer;
	private FileTypeAccept prefFiletype;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public FileTypeAccept getPrefFiletype() {
		return prefFiletype;
	}
	public void setPrefFiletype(FileTypeAccept prefFiletype) {
		this.prefFiletype = prefFiletype;
	}
	
	
	
	
}
