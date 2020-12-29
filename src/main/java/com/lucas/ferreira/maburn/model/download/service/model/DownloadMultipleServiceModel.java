package com.lucas.ferreira.maburn.model.download.service.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.BytesUtil;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DownloadMultipleServiceModel extends Downloader<CollectionSubItem> {

	private List<HttpURLConnection> httpConns = new ArrayList<HttpURLConnection>();

	private static final int BUFFER_SIZE = 8192;

	public DownloadMultipleServiceModel() {
		// TODO Auto-generated constructor stub
		scheduled();
	}

	public DownloadMultipleServiceModel(List<String> listLink, CollectionSubItem subItem, List<File> listFile,
			ItemWebData webData) {
		// TODO Auto-generated constructor stub
		initialize(listLink, subItem, listFile, webData);

	}

	public File download() throws IOException {
		// TODO Auto-generated method stub
		try {
		List<File> downloadedFiles = new ArrayList<>();
		System.out.println(listLink);
		String link = null;
		List<File> files = new ArrayList<File>();
		List<HttpURLConnection> httpConns = new ArrayList<HttpURLConnection>();
		
		for (int i = 0; i < listLink.size(); i++) {
			URL url = null;
			if (pauseProperty.get()) {
				stopUntil();
			}
			link = listLink.get(i);
			files.add(listFile.get(i));
			
			HttpURLConnection httpConn = downloadSetup(link);
			httpConns.add(httpConn);
			prepareDownload(httpConn);

		}

		for (int i = 0; i < listLink.size(); i++) {
			startDownload(files.get(i), httpConns.get(i));
			updateProgress(i, listLink.size());
			// System.out.println("updateProgress(i, listLink.size());: " + "i: " + i +
			// "listLink.size(): " + listLink.size());
			Platform.runLater(() -> {
				downloadProgress.set(getProgress());
			});
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		updateProgress(listLink.size(), listLink.size());
		return null;
	}

	private void prepareDownload(HttpURLConnection httpConn) {
		double size = BytesUtil.convertBytesToMegasBytes(httpConn.getContentLength());
		updateSize(size + sizeProperty.get());

	}

	private File startDownload(File file,  HttpURLConnection httpConn) throws IOException {
		URL url = httpConn.getURL();
		String path = file.getAbsolutePath();
		System.out.println("File: " + file.getAbsolutePath());
		System.out.println("URl: " + url);
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
		double size = BytesUtil.convertBytesToMegasBytes(httpConn.getContentLength());
		byte[] b = new byte[BUFFER_SIZE];
		int length = 0;
		int i = 0;
		System.out.println("Size: " + size);
		// System.out.println("Download - " + fileName + " " +
		// httpConn.getContentLength());
		
		while (length != -1) {
			if (pauseProperty.get()) {
				stopUntil();
			}
			length = is.read(b);

			i += BUFFER_SIZE;
			updateProgress(i, httpConn.getContentLength() + 1);
			updateCompleted(BytesUtil.convertBytesToMegasBytes(i));
			System.out.println("Megas: " + httpConn.getContentLength());  
			try {
				os.write(b, 0, length);
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
				continue;
			}

		}
		updateCompleted(BytesUtil.convertBytesToMegasBytes(i) + completedProperty.get());

		// System.out.println("Done - " + fileName + " " + size);
		//is.close();
		//os.close();
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName);
		return downloadedFile;
	}

	private HttpURLConnection downloadSetup(String link) throws IOException {
		// System.out.println(link);
		URL url = new URL(link);
		String referer = webData.getSite().getUrl();
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
		httpConn.addRequestProperty("REFERER", referer);
		
		return httpConn;
	}

	@Override
	protected CollectionSubItem call() throws Exception {
		// TODO Auto-generated method stub
		download();

		return subItem;
	}

	public void pause() {
		pauseProperty.set(true);
	}

	public void resume() {
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
	public void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, ItemWebData webData) {
		// TODO Auto-generated method stub
		this.listLink = listLink;
		this.subItem = subItem;
		this.listFile = listFile;
		this.webData = webData;

	}

	@Override
	public long speedCalculation(Double downloadSpeed, long start, long end, int i) {
		DecimalFormat four = new DecimalFormat("#0.00");

		downloadSpeed = Double.parseDouble(four.format(downloadSpeed).replaceAll(",", ".")) / 1048576;
		updateSpeed(downloadSpeed);

		return end;
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
