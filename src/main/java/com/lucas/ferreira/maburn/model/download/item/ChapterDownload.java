package com.lucas.ferreira.maburn.model.download.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.exceptions.ChapterDownloadException;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

public class ChapterDownload implements ItemDownload {

	private static final int NUMBER_PAGES_THREADS = 1;

	private String folderPath;
	private DownloadInfo chapterDownloadInfo;
	private ChapterDownloadValues chapterDownloadValues;
	private List<PageDownload> pageDownloads = new ArrayList<>();
	private List<PageDownloadItemValues> pageValues = new ArrayList<>();

	private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();
	private ChapterInfoRefresher chapterInfoRefresher;

	private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_PAGES_THREADS);

	public ChapterDownload(DownloadValues downloadValues, DownloadInfo chapterDownloadInfo) {
		this.chapterDownloadValues = (ChapterDownloadValues) downloadValues;
		this.chapterDownloadInfo = chapterDownloadInfo;
		chapterInfoRefresher = newChapterInfoRefresher();

	}

	@Override
	public void download() throws ChapterDownloadException {
		try {
			createChapterFolder();
			List<ItemDownloadValues> pagesDownloadValues = chapterDownloadValues.getListItemsDownloadValues();
			forEachPage(pagesDownloadValues);
			waitUntilDownloadFinish();
		} catch (Exception e) {
			throw new ChapterDownloadException(e.getMessage());
		}

	}

	private void waitUntilDownloadFinish() {
		while (chapterDownloadValues.getObsListNewPageDownloadItemsValues().size() < chapterDownloadValues
				.getListItemsDownloadValues().size()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	private ChapterInfoRefresher newChapterInfoRefresher() {
		return new ChapterInfoRefresher(chapterDownloadValues);
	}

	public void createChapterFolder() {
		folderPath = chapterDownloadInfo.getRoot() + "\\" + chapterDownloadInfo.getFilename() + "\\";

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

	private void forEachPage(List<ItemDownloadValues> pages) {

		for (int pagePostion = 0; pagePostion < pages.size(); pagePostion++) {
			PageDownloadItemValues page = (PageDownloadItemValues) pages.get(pagePostion);

			DownloadInfo pageDownloadInfo = fillPageDownloadInfo(pagePostion, page);

			PageDownload pageDownload = new PageDownload(page, pageDownloadInfo);
			pageDownload.onDownloadComplete(downloadedPage -> {
				synchronized (this) {
					readSizeFromMetadata(pageDownload.readPageMetadata(), page);
					notifyPageDownloadComplete(page);
					checkState();
				}

			});
			pageDownload.onDownloadFailed(downloadedPage -> notifyPageDownloadFailed(page));

			Thread thDownloadPage = new Thread(() -> downloadPage(pageDownload));
			executorService.execute(thDownloadPage);
			pageDownloads.add(pageDownload);
			pageValues.add(page);
		}

	}

	private void checkState() {

		if (chapterDownloadValues.getDownloadProgressState().get() != DownloadProgressState.FAILED
				&& chapterDownloadValues.getDownloadProgressState().get() != DownloadProgressState.CANCELED) {

			boolean allMath = chapterDownloadValues.getListItemsDownloadValues().stream()
					.allMatch(page -> page.getDownloadProgressState().get() == DownloadProgressState.COMPLETED);
			if (allMath) {
				chapterDownloadValues.getDownloadProgressState().set(DownloadProgressState.COMPLETED);
			} else {
				chapterDownloadValues.getDownloadProgressState().set(DownloadProgressState.DOWNLOADING);

			}
		}
	}

	private DownloadInfo fillPageDownloadInfo(int pagePostion, PageDownloadItemValues page) {
		DownloadInfo pageDownloadInfo = new DownloadInfo();
		String pageName = pageNameForPosition(pagePostion);

		pageDownloadInfo.setRoot(folderPath);
		pageDownloadInfo.setUrl(page.getDirectLink().get());
		pageDownloadInfo.setFilename(pageName);
		pageDownloadInfo.setPrefFiletype(chapterDownloadInfo.getPrefFiletype());
		return pageDownloadInfo;
	}

	private void downloadPage(PageDownload pageDownload) {
		try {
			pageDownload.download();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void notifyPageDownloadFailed(PageDownloadItemValues page) {

		chapterInfoRefresher.decreasePagesAmount();
		chapterInfoRefresher.refreshChapterProgress();
		chapterDownloadValues.getObsListNewPageDownloadItemsValues().add(page);
		chapterDownloadValues.getDownloadProgressState().set(DownloadProgressState.FAILED);

	}

	private void notifyPageDownloadComplete(PageDownloadItemValues page) {

		chapterInfoRefresher.incresePagesAmount();
		chapterInfoRefresher.refreshChapterProgress();
		chapterDownloadValues.getObsListNewPageDownloadItemsValues().add(page);

	}

	private void readSizeFromMetadata(ImageMetadata imageMetadata, PageDownloadItemValues pageDownloadItemValues) {

		long bytesSize = imageMetadata.getLongSize();
		double megabytesSize = BytesUtil.convertBytesToMegasBytes(bytesSize);
		pageDownloadItemValues.getDownloadSize().set(megabytesSize);
		chapterInfoRefresher.increseChapterSize(megabytesSize);
	}

	@Override
	public void showDownloadValuesRealTimeInfo() {

		downloadRealTimeInfo.showInfoWithProgress(chapterDownloadValues);

	}

	@Override
	public void hideDownloadValuesRealTimeInfo() {
		downloadRealTimeInfo.stopShowInfo();
	}

	@Override
	public void pause() {

		pageDownloads.forEach(page -> {
			page.pause();
		});
	}

	@Override
	public void resume() {
		pageDownloads.forEach(page -> {
			page.resume();
		});
	}

	@Override
	public void stop() {
		pageDownloads.forEach(page -> {
			page.stop();
		});
	}

}
