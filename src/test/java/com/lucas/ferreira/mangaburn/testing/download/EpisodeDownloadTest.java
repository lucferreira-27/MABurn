package com.lucas.ferreira.mangaburn.testing.download;

import org.junit.Test;

import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.item.EpisodeDownload;

public class EpisodeDownloadTest {

	@Test
	public void episodeDownloadTest() {
		String url = "https://fy.v.vrv.co/evs/4357eea7932071e96e026e203ebc12a7/assets/8b475ab24ba66e7de2a0bf1184c633c0_4079759.mp4?Expires=1623626408&Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly9meS52LnZydi5jby9ldnMvNDM1N2VlYTc5MzIwNzFlOTZlMDI2ZTIwM2ViYzEyYTcvYXNzZXRzLzhiNDc1YWIyNGJhNjZlN2RlMmEwYmYxMTg0YzYzM2MwXzQwNzk3NTkubXA0IiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNjIzNjI2NDA4fX19XX0=&Signature=9iGEcNekysGQ1p79+PQWeFpf1vtGe0JIoe0BTmkyMxg=&Key-Pair-Id=APKAJMWSQ5S7ZB3MF5VA";
		String saikoUrl = "https://download.saikoanimes.net/secure/uploads/9250?shareable_link=331.mp4";
		EpisodeDownloadItemValues episodeDownloadItemValues = new EpisodeDownloadItemValues();

		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename("Episode 01");
		downloadInfo.setRoot("D:\\Teste\\");
		downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
		downloadInfo.setUrl(saikoUrl);
		downloadInfo.setReferer("https://download.saikoanimes.net/drive/s/8Srct8b1sPKHPlNAhHzcHlGmUhzo59?_ga=2.227894045.1802495208.1623678625-2018533605.1614110752");
		episodeDownloadItemValues.getDirectLink().set(saikoUrl);
		episodeDownloadItemValues.getName().set(downloadInfo.getFilename());
		EpisodeDownload episodeDownload = new EpisodeDownload(downloadInfo, episodeDownloadItemValues);

		try {
			episodeDownload.showDownloadValuesRealTimeInfo();
						
			
			episodeDownload.download();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
