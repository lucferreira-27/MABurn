package com.lucas.ferreira.maburn.model.bean;

public class Page {
	private String name;
	private String destination;
	private Chapter chapter;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	public Chapter getChapter() {
		return chapter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
