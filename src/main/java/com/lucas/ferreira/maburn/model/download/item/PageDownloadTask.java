package com.lucas.ferreira.maburn.model.download.item;

import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;

public class PageDownloadTask implements Callable<Void> {
	

	
	private PageTaskInfos pageTaskInfos;
	
	
	public PageDownloadTask(PageTaskInfos pageTaskInfos) {
		this.pageTaskInfos = pageTaskInfos;
	}
	
	
	@Override
	public Void call() throws Exception {
		

		DownloadInfo pageDownloadInfo = fillPageDownloadInfo(pageTaskInfos.getPagePosition(), pageTaskInfos.getPageDownloadItemValues());

		PageDownload pageDownload = new PageDownload(pageTaskInfos.getPageDownloadItemValues(), pageDownloadInfo);

		downloadPage(pageDownload);
		
		
		return null;
	}
	public String pageNameForPosition(int position) {
		String name = "";
		if (position <= 9) {
			name += "0";
		}
		name += String.valueOf(position);
		return name;
	}
	
	
	private void downloadPage(PageDownload pageDownload) {
		try {
			pageDownload.download();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private DownloadInfo fillPageDownloadInfo(int pagePostion, PageDownloadItemValues page) {
		DownloadInfo pageDownloadInfo = new DownloadInfo();
		String pageName = pageNameForPosition(pagePostion);

		pageDownloadInfo.setPath(pageTaskInfos.getChapterPath());
		pageDownloadInfo.setUrl(page.getDirectLink());
		pageDownloadInfo.setFilename(pageName);
		pageDownloadInfo.setPrefFiletype(pageTaskInfos.getPrefFileType());
		return pageDownloadInfo;
	}
}
