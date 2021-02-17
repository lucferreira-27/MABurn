package com.lucas.ferreira.maburn.model.documents.xml.form.config;

public class GeralConfigForm {
	private Integer parallelDownloads = new Integer(3);
	
	public Integer getParallelDownloads() {
		return parallelDownloads;
	}
	public void setParallelDownloads(Integer parallelDownloads) {
		this.parallelDownloads = parallelDownloads;
	}
	
}
