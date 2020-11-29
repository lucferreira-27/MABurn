package com.lucas.ferreira.maburn.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.model.enums.Sites;

public class DownloadServiceModel implements Callable<Void> {

	private File location;
	private String link;
	private static final int BUFFER_SIZE = 8192;

	public DownloadServiceModel(String link, File file) {
		// TODO Auto-generated constructor stub
		this.link = link;
		this.location = file;

	}

	public File download(String link, Sites site) throws IOException {
		URL url = new URL(link);
		String referer = site.getUrl();
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
		httpConn.addRequestProperty("REFERER", referer);
		String type = null;
		try {
			type = url.getPath().substring(url.getPath().lastIndexOf("."));
		} catch (Exception e) {
			// TODO: handle exception
			type = ".mp4";
		}
		String fileName = location.getAbsolutePath().substring(location.getAbsolutePath().lastIndexOf("\\") + 1);
		String destination = location.getAbsolutePath().substring(0, location.getAbsolutePath().lastIndexOf("\\") + 1);

		location = new File(destination);
		InputStream is = httpConn.getInputStream();
		location.mkdirs();
		OutputStream os = new FileOutputStream(location.getAbsolutePath() + "\\" + fileName + type);
		double size = (double) httpConn.getContentLength() / 1048576;

		byte[] b = new byte[BUFFER_SIZE];
		int length;
		int i = 0;


		System.out.println("Download - " + fileName + " " + size);
		while ((length = is.read(b)) != -1) {
			//i += BUFFER_SIZE;

			// System.out.println(8192/1000000);
			//d += i / 1048576;
//			int actual = i * BUFFER_SIZE / 1000;
//			int total = httpConn.getContentLength() / 1000;
//
//			System.out.println(fileName + " " + actual + " MB" + "\\" + total);
//			i++;
			os.write(b, 0, length);

		}
		System.out.println("Done - " + fileName + " " + size );
		is.close();
		os.close();
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName);
		return downloadedFile;
	}

	@Override
	public Void call() throws Exception {
		// TODO Auto-generated method stub
		// download(link);
		return null;
	}

}
