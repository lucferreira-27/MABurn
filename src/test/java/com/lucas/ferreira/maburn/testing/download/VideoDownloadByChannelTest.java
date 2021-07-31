package com.lucas.ferreira.maburn.testing.download;

import org.junit.Test;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;
import com.lucas.ferreira.maburn.model.download.channel.VideoDownloadByChannel;
import com.lucas.ferreira.maburn.util.MathUtil;

public class VideoDownloadByChannelTest {

	private ItemDownloadValues itemDownloadValues;

	@Test
	public void downloadVideoFileTest() {
		// String smallFile = "https://ns538468.ip-144-217-72.net/T/teekyuu/01.mp4";
		// String mediumFile =
		// "https://www.anitube.site/aHR0cHM6Ly9tZWxpb2Rhc2lyYTE5OTguYmxvZ3Nwb3QuY29tLzIwMjEvMDYvNzU1YzQyNzE0MTgxMmMwMy5odG1s/0/bg.mp4";
		String speedFile = "https://video.wixstatic.com/video/2aabac_cbc2d7e4a6d34c75b96ddffdd7ee5228/720p/mp4/file.mp4";
		String d = "https://video.wixstatic.com/video/35b724_a866906b8d1a474196fd9efeb4a7c277/1080p/mp4/file.mp4";
		
		
		itemDownloadValues = new EpisodeDownloadItemValues();
		DownloadByChannel downloadByChannel = new VideoDownloadByChannel((EpisodeDownloadItemValues) itemDownloadValues);
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename("Episode 01");
		downloadInfo.setRoot("D:\\Teste\\");
		downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
		downloadInfo.setReferer("https://betteranime.net");
		downloadInfo.setUrl(d);

		itemDownloadValues.getDownloadProgress().addListener((obs, oldvalue, newvalue) -> {
			double progress = MathUtil.roundDouble(newvalue.doubleValue(), 4) * 100;
			System.out.println("--- Download ---\nName: " + itemDownloadValues.getName() + "\nDirect Link: "
					+ itemDownloadValues.getDirectLink() + "\nSize: " + itemDownloadValues.getDownloadSize().get()
					+ "\nComplete: " + itemDownloadValues.getTotalDownloaded().get() + "\nResolution: "
					+ ((EpisodeDownloadItemValues) itemDownloadValues).getResolution().get() + "\nProgress: " + progress
					+ "\nState:" + itemDownloadValues.getDownloadProgressState().get() + "\nSpeed: "
					+ itemDownloadValues.getDownloadSpeed().get() + "\nTime Remain: "
					+ itemDownloadValues.getTimeRemain().get() + " seconds" + "\nPath: " + downloadInfo.getRoot()
					+ downloadInfo.getFilename() + "\nReferer: " + downloadInfo.getReferer() + "\n------------\n\n");
		});

		try {
			downloadByChannel.download(downloadInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
