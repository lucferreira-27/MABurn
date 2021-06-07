package com.lucas.ferreira.maburn.model.download.item;

import java.io.File;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.exceptions.ChapterDownloadException;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;
import com.lucas.ferreira.maburn.model.download.channel.ImageDownloadByChannel;

import javafx.collections.ListChangeListener.Change;

public class ChapterDownload implements ItemDownload {

	private String folderPath;
	private DownloadInfo chapterDownloadInfo;
	private ChapterDownloadValues chapterDownloadValues;

	private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();

	public ChapterDownload(DownloadValues downloadValues,DownloadInfo chapterDownloadInfo) {
		this.chapterDownloadValues = (ChapterDownloadValues) downloadValues;
		this.chapterDownloadInfo = chapterDownloadInfo;
		
	}

	@Override
	public void download() throws ChapterDownloadException {
		try {
			createChapterFolder();
			List<ItemDownloadValues> pagesDownloadValues = chapterDownloadValues.getListItemsDownloadValues();
			forEachPage(pagesDownloadValues);

		} catch (Exception e) {
			throw new ChapterDownloadException(e.getMessage());
		}

	}

	public void createChapterFolder() {
		folderPath = chapterDownloadInfo.getPath() + chapterDownloadInfo.getFilename() + "\\";

		File file = new File(folderPath);
		file.mkdir();
	}

	public String pageNameForPosition(int position) {
		String name = "";
		if (position <= 9) {
			name += "0";
		}
		name += String.valueOf(position);
		return name;
	}

	public void forEachPage(List<ItemDownloadValues> pages) {
		
		
		chapterDownloadValues.getTotalPagesDownloaded().addListener((obs, oldvalue, newvalue) ->{
			double numberOfPagesDownloaded = newvalue.doubleValue();
			int totalPages = chapterDownloadValues.getListItemsDownloadValues().size();

			chapterDownloadValues.getDownloadProgress().set(numberOfPagesDownloaded / totalPages);
		});
		
		chapterDownloadValues.getObsListPageDownlaodItemsValues().addListener((Change<? extends ItemDownloadValues> c) ->{
			c.next();
			c.wasAdded();
			int total = chapterDownloadValues.getTotalPagesDownloaded().get();
			chapterDownloadValues.getTotalPagesDownloaded().set(total + 1);
		});
		for (int i = 0; i < pages.size(); i++) {

			String pageName = pageNameForPosition(i);

			PageDownloadItemValues page = (PageDownloadItemValues) pages.get(i);

			DownloadInfo pageDownloadInfo = new DownloadInfo();
			pageDownloadInfo.setPath(folderPath);
			pageDownloadInfo.setUrl(page.getDirectLink());
			pageDownloadInfo.setFilename(pageName);
			pageDownloadInfo.setPrefFiletype(chapterDownloadInfo.getPrefFiletype());
			DownloadByChannel downloadByChannel = new ImageDownloadByChannel(page);
			try {
				downloadByChannel.download(pageDownloadInfo);
				chapterDownloadValues.getObsListPageDownlaodItemsValues().add(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void showDownloadValuesRealTimeInfo() {

		downloadRealTimeInfo.showInfoWithProgress(chapterDownloadValues);

	}

	@Override
	public void hideDownloadValuesRealTimeInfo() {
		downloadRealTimeInfo.stopShowInfo();
	}

}
