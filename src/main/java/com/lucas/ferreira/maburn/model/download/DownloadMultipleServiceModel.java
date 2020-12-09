package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DownloadMultipleServiceModel extends Downloader<CollectionSubItem> {

	private HttpURLConnection httpConn;


	private static final int BUFFER_SIZE = 8192;

	public DownloadMultipleServiceModel() {
		// TODO Auto-generated constructor stub
		scheduled();
	}

	public DownloadMultipleServiceModel(List<String> listLink, CollectionSubItem subItem, List<File> listFile,
			Sites sites) {
		// TODO Auto-generated constructor stub
		initialize(listLink, subItem, listFile, sites);

	}

	public File download() throws IOException {
		// TODO Auto-generated method stub
		List<File> downloadedFiles = new ArrayList<>();
		System.out.println(listLink);

		for (int i = 0; i < listLink.size(); i++) {

			if (pauseProperty.get()) {
				stopUntil();
			}
			String link = listLink.get(i);
			File file = listFile.get(i);
			URL url = downloadSetup(link);

			downloadedFiles.add(startDownload(file, url));

			updateProgress(i, listLink.size());
			// System.out.println("updateProgress(i, listLink.size());: " + "i: " + i +
			// "listLink.size(): " + listLink.size());
			Platform.runLater(() -> {
				downloadProgress.set(getProgress());
			});
		}
		updateProgress(listLink.size(), listLink.size());
		return null;
	}

	private File startDownload(File file, URL url) throws IOException {
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
		InputStream is;
		try {
			is = httpConn.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("url: " + url);
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		
		location.mkdirs();
		OutputStream os = new FileOutputStream(location.getAbsolutePath() + "\\" + fileName + type);
		double size = (double) httpConn.getContentLength() / 1048576;

		byte[] b = new byte[BUFFER_SIZE];
		int length;
		int i = 0;

		// System.out.println("Download - " + fileName + " " +
		// httpConn.getContentLength());
		while ((length = is.read(b)) != -1) {
			if (pauseProperty.get()) {
				stopUntil();
			}
			i += BUFFER_SIZE;
			updateSpeed(speedCalculation());
			os.write(b, 0, length);

		}
		// System.out.println("Done - " + fileName + " " + size);
		is.close();
		os.close();
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName);
		return downloadedFile;
	}

	private URL downloadSetup(String link) throws IOException {
		// System.out.println(link);
		URL url = new URL(link);
		String referer = site.getUrl();
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
		httpConn.addRequestProperty("REFERER", referer);
		return url;
	}

	@Override
	protected CollectionSubItem call() throws Exception {
		// TODO Auto-generated method stub
		download();

		return subItem;
	}

	public DoubleProperty getDownloadProgress() {
		return downloadProgress;
	}

	public void pause() {
		pauseProperty.set(true);
	}

	public void unpause() {
		pauseProperty.set(false);
	}

	private void stopUntil() {
		while (getPauseProperty().get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	@Override
	public void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, Sites sites) {
		// TODO Auto-generated method stub
		this.listLink = listLink;
		this.subItem = subItem;
		this.listFile = listFile;
		this.site = sites;

	}

	@Override
	public DoubleProperty getSizeProperty() {
		// TODO Auto-generated method stub
		return sizeProperty;
	}

	@Override
	public DoubleProperty getDownloadSpeedProperty() {
		// TODO Auto-generated method stub
		return speedProperty;
	}

	@Override
	public double speedCalculation() {
		// TODO Auto-generated method stub
		return 0;
	}


}
