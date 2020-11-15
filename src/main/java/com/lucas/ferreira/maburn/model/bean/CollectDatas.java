package com.lucas.ferreira.maburn.model.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.Category;

public class CollectDatas {
	private String title;
	private Integer id;
	private Category category;
	private String synopsis;
	private String status;
	private String publishedDate;
	private Double avaregeRating;
	private String itemDataBaseUrl;
	private Map<String, String> posterImageLink = new LinkedHashMap<>();

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getPosterImageLink(String key) {
		return posterImageLink.get(key);
	}

	public void addPosterImageLink(String key, String value) {
		this.posterImageLink.put(key, value);
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemDataBaseUrl() {
		return itemDataBaseUrl;
	}
	public void setItemDataBaseUrl(String itemDataBaseUrl) {
		this.itemDataBaseUrl = itemDataBaseUrl;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public Double getAvaregeRating() {
		return avaregeRating;
	}
	public void setAvaregeRating(Double avaregeRating) {
		this.avaregeRating = avaregeRating;
	}

	@Override
	public String toString() {
		return "CollectDatas [title=" + title + ", id=" + id + ", category=" + category + ", synopsis=" + synopsis
				+ ", status=" + status + ", publishedDate=" + publishedDate + ", avaregeRating=" + avaregeRating
				+ ", itemDataBaseUrl=" + itemDataBaseUrl + ", posterImageLink=" + posterImageLink + "]";
	}
	


}
