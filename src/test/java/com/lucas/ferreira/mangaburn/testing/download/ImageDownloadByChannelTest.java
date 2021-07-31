package com.lucas.ferreira.mangaburn.testing.download;

import org.junit.Test;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;
import com.lucas.ferreira.maburn.model.download.channel.ImageDownloadByChannel;
import com.lucas.ferreira.maburn.util.MathUtil;

public class ImageDownloadByChannelTest {
	private ItemDownloadValues itemDownloadValues;

	@Test
	public void downloadImageFileTest() {

		String url = "https://img-host.filestatic1.xyz/mangas_files/one-piece-br/1015/img_or0406210922_0001.jpg";
		itemDownloadValues = new PageDownloadItemValues();
		DownloadByChannel downloadByChannel = new ImageDownloadByChannel((PageDownloadItemValues) itemDownloadValues);
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename(itemDownloadValues.getName().get());
		downloadInfo.setRoot("D:\\Teste\\");
		downloadInfo.setPrefFiletype(FileTypeAccept.JPEG);
		downloadInfo.setReferer("https://mangahosted.com");
		downloadInfo.setUrl(url);

		itemDownloadValues.getDownloadProgress().addListener((obs, oldvalue, newvalue) -> {
			double progress = MathUtil.roundDouble(newvalue.doubleValue(), 4) * 100;
			System.out.println("--- Download ---\nName: " + itemDownloadValues.getName() + "\nDirect Link: "
					+ itemDownloadValues.getDirectLink() + "\nSize: " + itemDownloadValues.getDownloadSize().get()
					+ "\nComplete: " + itemDownloadValues.getTotalDownloaded().get()  + "\nProgress: " + progress
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
