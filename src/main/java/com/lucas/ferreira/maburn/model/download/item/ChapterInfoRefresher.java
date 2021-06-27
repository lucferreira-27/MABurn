package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;

public class ChapterInfoRefresher {

	private ChapterDownloadValues chapterDownloadValues;

	public ChapterInfoRefresher(ChapterDownloadValues chapterDownloadValues) {
		this.chapterDownloadValues = chapterDownloadValues;
	}

	public void incresePagesAmount() {
		int total = chapterDownloadValues.getTotalPagesDownloaded().get();
		int incresedTotal = total + 1;
		chapterDownloadValues.getTotalPagesDownloaded().set(incresedTotal);
		refreshChapterTotalPages(incresedTotal);
	}

	public void decreasePagesAmount() {

		int total = chapterDownloadValues.getTotalPagesDownloaded().get();
		if (total > 0) {
			chapterDownloadValues.getTotalPagesDownloaded().set(total - 1);
			refreshChapterTotalPages(total);

		}
	}

	public void increseChapterSize(double size) {
		double currentSize = chapterDownloadValues.getChapterSize().get();
		double newSize = currentSize + size;
		refreshChapterSize(newSize);
	}

	public void refreshChapterProgress() {
		double numberOfPagesDownloaded = chapterDownloadValues.getTotalPagesDownloaded().doubleValue();
		int totalPages = chapterDownloadValues.getListItemsDownloadValues().size();

		chapterDownloadValues.getDownloadProgress().set(numberOfPagesDownloaded / totalPages);
	}

	private void refreshChapterSize(double newSize) {
		chapterDownloadValues.getChapterSize().set(newSize);
	}

	private void refreshChapterTotalPages(int newTotal) {
		chapterDownloadValues.getTotalPagesDownloaded().set(newTotal);

	}

}
