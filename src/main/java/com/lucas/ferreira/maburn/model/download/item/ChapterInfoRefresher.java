package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;

public class ChapterInfoRefresher implements InfoRefresher{

	private ChapterDownloadValues chapterDownloadValues;

	public ChapterInfoRefresher(ChapterDownloadValues chapterDownloadValues) {
		this.chapterDownloadValues = chapterDownloadValues;
	}

	public void increaseAmount() {
		int total = chapterDownloadValues.getTotalItemsDownloaded().get();
		int incresedTotal = total + 1;
		chapterDownloadValues.getTotalItemsDownloaded().set(incresedTotal);
		refreshChapterTotalPages(incresedTotal);
	}

	public void decreaseAmount() {

		int total = chapterDownloadValues.getTotalItemsDownloaded().get();
		if (total > 0) {
			chapterDownloadValues.getTotalItemsDownloaded().set(total - 1);
			refreshChapterTotalPages(total);

		}
	}

	public void increaseFinalSize(double size) {
		double currentSize = chapterDownloadValues.getChapterSize().get();
		double newSize = currentSize + size;
		refreshChapterSize(newSize);
	}

	public void refreshProgress() {
		double numberOfPagesDownloaded = chapterDownloadValues.getTotalItemsDownloaded().doubleValue();
		int totalPages = chapterDownloadValues.getListItemsDownloadValues().size();

		chapterDownloadValues.getDownloadProgress().set(numberOfPagesDownloaded / totalPages);
	}

	private void refreshChapterSize(double newSize) {
		chapterDownloadValues.getChapterSize().set(newSize);
	}

	private void refreshChapterTotalPages(int newTotal) {
		chapterDownloadValues.getTotalItemsDownloaded().set(newTotal);

	}

}
