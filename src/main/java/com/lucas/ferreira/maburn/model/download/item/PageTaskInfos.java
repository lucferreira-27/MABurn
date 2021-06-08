package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;

public class PageTaskInfos {
	
	private String chapterPath;
	private PageDownloadItemValues pageDownloadItemValues;
	private int pagePosition;
	private String prefFileType;
	
	public String getChapterPath() {
		return chapterPath;
	}
	public void setChapterPath(String chapterPath) {
		this.chapterPath = chapterPath;
	}
	public PageDownloadItemValues getPageDownloadItemValues() {
		return pageDownloadItemValues;
	}
	public void setPageDownloadItemValues(PageDownloadItemValues pageDownloadItemValues) {
		this.pageDownloadItemValues = pageDownloadItemValues;
	}

	public int getPagePosition() {
		return pagePosition;
	}
	public void setPagePosition(int pagePosition) {
		this.pagePosition = pagePosition;
	}
	public String getPrefFileType() {
		return prefFileType;
	}
	public void setPrefFileType(String prefFileType) {
		this.prefFileType = prefFileType;
	}
	
	
	
	
	
}
