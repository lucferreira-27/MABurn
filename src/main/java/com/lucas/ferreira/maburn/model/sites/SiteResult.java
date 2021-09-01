package com.lucas.ferreira.maburn.model.sites;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.webscraping.PageInfo;

public class SiteResult {

	private List<ItemValue> itemsValues = new ArrayList<>();
	private PageInfo pageInfo = new PageInfo();

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<ItemValue> getItemsValues() {
		return itemsValues;
	}

	@Override
	public String toString() {
		return "SiteResult [itemsValues=" + itemsValues + ", bufferedImage=" + pageInfo + "]";
	}

}
