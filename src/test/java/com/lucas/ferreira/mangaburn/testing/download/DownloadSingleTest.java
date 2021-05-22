package com.lucas.ferreira.mangaburn.testing.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

public class DownloadSingleTest {
	private File file = new File("D:\\One Piece Anitube\\Anime.mp4");
	private String link = "http://video.wixstatic.com/video/e6f1d1_b1ff1d6bc4144325873b1febfeccdf15/1080p/mp4/file.mp4";
	private HttpURLConnection httpConn;
	private String referer = "https://betteranime.net";
	private URL url ;
	@Before
	public void setUp() {

		try {
			 url = new URL(link);

			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
			httpConn.addRequestProperty("REFERER", referer);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void downloadFile() throws IOException {
		CustomLogger.log(BytesUtil.convertBytesToMegasBytes(httpConn.getContentLength()));
		CustomLogger.log("Test");
		String path = file.getAbsolutePath();
		String type = null;

		try {
			type = url.getPath().substring(url.getPath().lastIndexOf("."));
		} catch (Exception e) {
			// TODO: handle exception
			type = ".mp4";
		}
		String fileName = path.substring(path.lastIndexOf("\\") + 1);
		String destination = path.substring(0, path.lastIndexOf("\\") + 1);

		File location = new File(destination);
		BufferedInputStream is;

		is = new BufferedInputStream(httpConn.getInputStream());

		location.mkdirs();
		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(location.getAbsolutePath() + "\\" + fileName + type));
		double size = BytesUtil.convertBytesToMegasBytes(httpConn.getContentLength());

		byte[] b = new byte[8192];
		int length = 0;
		int i = 0;

		CustomLogger.log("Download - " + fileName + " " + httpConn.getContentLength());
		try {
			while (length != -1) {
	
				length = is.read(b);

				i += length;

				System.out.println(i);
				try {
					os.write(b, 0, length);
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					continue;
				}


			}
		} catch (DownloadServiceException e) {
			// TODO: handle exception

			throw new IOException(e.getMessage());
		} finally {
			CustomLogger.log("Done - " + fileName + " " + size);
			is.close();
			os.close();
		}
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName + type);
		CustomLogger.log("REALLY OVER!");
	}

}
