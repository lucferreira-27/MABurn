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
import com.lucas.ferreira.maburn.util.BytesUtil;

public class DownloadSingleTest {
	private File file = new File("D:\\One Piece Anitube\\Anime");
	private String link = "https://video.wixstatic.com/video/4f75e1_c816b7423c2b4a6197ae4a8c08c54862/1080p/mp4/file.mp4";
	private HttpURLConnection httpConn;
	private String referer = "https://goyabu.com";
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
		System.out.println(BytesUtil.convertBytesToMegasBytes(httpConn.getContentLength()));
		System.out.println("Test");
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

		System.out.println("Download - " + fileName + " " + httpConn.getContentLength());
		try {
			while (length != -1) {
	
				length = is.read(b);

				i += length;


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
			System.out.println("Done - " + fileName + " " + size);
			is.close();
			os.close();
		}
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName + type);
		System.out.println("REALLY OVER!");
	}

}
