package com.lucas.ferreira.maburn.model.download;

import java.util.ArrayList;
import java.util.List;

public class DownloadInfo {
	private String filename;
	private String root;
	private String url;
	private List<String> listUrls = new ArrayList<String>();
	private String referer;
	private FileTypeAccept prefFiletype;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getUrl() {
		return url;
	}
	public List<String> getListUrls() {
		return listUrls;
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
	@Override
	public String toString() {
		return String.format("DownloadInfo [filename=%s, path=%s, url=%s, listUrls=%s, referer=%s, prefFiletype=%s]",
				filename, root, url, listUrls, referer, prefFiletype);
	}
	
	
	
	
}
