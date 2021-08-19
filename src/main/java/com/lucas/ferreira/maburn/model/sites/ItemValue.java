package com.lucas.ferreira.maburn.model.sites;

import java.util.ArrayList;
import java.util.List;

public class ItemValue {
	private String name;
	private List<String> urls = new ArrayList<>();

	public ItemValue(String name, String url) {
		this.name = name;
		this.urls.add(url);
	}

	public ItemValue(String name) {
		this.name = name;
	}

	public ItemValue() {

	}

	public ItemValue(String name, List<String> urls) {
		this.name = name;
		this.urls = urls;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void addUrl(String url) {
		this.urls.add(url);
	}

	@Override
	public String toString() {
		return "ItemValue [name=" + name + ", urls=" + urls.size() + "]";
	}
}
