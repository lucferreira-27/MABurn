package com.lucas.ferreira.maburn.testing.download;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import com.lucas.ferreira.maburn.controller.title.download.cards.GroupDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.exceptions.ChapterDownloadException;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;

public class ChapterDownloadTest {
	@Test
	public void shouldDownloadAllPagesTest() {
		GroupDownloadValues chapter = new ChapterDownloadValues();

		List<ItemDownloadValues> listItemsDownlaodValues = new ArrayList<ItemDownloadValues>();

		List<String> links = Arrays.asList("https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg",
				"https://img-host.filestatic3.xyz/mangas_files/solo-leveling/1/01.jpg");
		links.forEach(link -> {
			PageDownloadItemValues pageDownloadItemValues = new PageDownloadItemValues();
			pageDownloadItemValues.getDirectLink().set(link);
			listItemsDownlaodValues.add(pageDownloadItemValues);
		});

		chapter.getListItemsDownloadValues().addAll(listItemsDownlaodValues);
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename("Chapter 01");
		downloadInfo.setRoot("D:\\Teste\\");
		downloadInfo.setReferer("https://mangahosted.com");
		downloadInfo.setPrefFiletype(FileTypeAccept.JPEG);
		ChapterDownload chapterDownload = new ChapterDownload(chapter, downloadInfo);

		try {
			chapterDownload.showDownloadValuesRealTimeInfo();
			new Thread(() ->{
				try {
					chapterDownload.download();
				} catch (ChapterDownloadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();;
			boolean t = true;
			boolean allMath =false;
			
				Thread.sleep(1000);
				chapterDownload.pause();
				Thread.sleep(1000);
				chapterDownload.resume();
				Thread.sleep(10000);

			assertTrue(allMath);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void notShouldDownloadAllPagesTest() {
		GroupDownloadValues chapter = new ChapterDownloadValues();

		List<ItemDownloadValues> listItemsDownlaodValues = new ArrayList<ItemDownloadValues>();

		List<String> links = Arrays.asList(
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0001.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0002.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0003.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0004.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0005.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0006.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0007.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0008.jpg",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/broken/link",
				"https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0009.jpg");
		links.forEach(link -> {
			PageDownloadItemValues pageDownloadItemValues = new PageDownloadItemValues();
			pageDownloadItemValues.getDirectLink().set(link);
			listItemsDownlaodValues.add(pageDownloadItemValues);
		});

		chapter.getListItemsDownloadValues().addAll(listItemsDownlaodValues);
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename("Chapter 01");
		downloadInfo.setRoot("D:\\Teste\\");
		downloadInfo.setReferer("https://mangahosted.com");
		downloadInfo.setPrefFiletype(FileTypeAccept.JPEG);
		ChapterDownload chapterDownload = new ChapterDownload(chapter, downloadInfo);

		try {
			chapterDownload.showDownloadValuesRealTimeInfo();
			chapterDownload.download();

			boolean allMath = chapter.getListItemsDownloadValues().stream()
					.allMatch(page -> page.getDownloadProgressState().get() == DownloadProgressState.COMPLETED);
			assertTrue(!allMath);
		} catch (ChapterDownloadException e) {

			e.printStackTrace();
		}

	}
}
