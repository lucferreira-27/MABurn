package com.lucas.ferreira.maburn.model.documents.xml.form.config;

public class GeralConfigForm {
	private Integer parallelDownloads = new Integer(3);
	private String browserLocal = "DEFAULT";
	
	public Integer getParallelDownloads() {
		return parallelDownloads;
	}
	public void setParallelDownloads(Integer parallelDownloads) {
		this.parallelDownloads = parallelDownloads;
	}
	public String getBrowserLocal() {
		return browserLocal;
	}
	public void setBrowserLocal(String browserLocal) {
		this.browserLocal = browserLocal;
	}
	
	
}
