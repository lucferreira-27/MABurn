package com.lucas.ferreira.maburn.model.download.service.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.BytesUtil;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;

public class DownloadSingleServiceModel extends Downloader<CollectionSubItem> {
	private File file;
	private String link;
	private HttpURLConnection httpConn;

	public DownloadSingleServiceModel() {
		// TODO Auto-generated constructor stub
	}

	public DownloadSingleServiceModel(List<String> listLink, CollectionSubItem subItem, List<File> listFile,
			ItemWebData webData) {
		// TODO Auto-generated constructor stub
		initialize(listLink, subItem, listFile, webData);

	}

	public File download() throws IOException {

		URL url = downloadSetup(link);
		startDownload(url);

		return null;
	}

	private File startDownload(URL url) throws IOException {
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

		byte[] b = new byte[BUFFER_SIZE];
		int length = 0;
		int i = 0;
		updateSize(size);
		updateName(fileName);
		Platform.runLater(() -> {
			downloadProgress.bind(progressProperty());

		});
		System.out.println("Download - " + fileName + " " + httpConn.getContentLength());
		updateState(DownloadState.READY);
		try {
			checkSpeed();
			updateState(DownloadState.DOWNLOADING);
			while (length != -1) {
				if (pauseProperty.get() || cancelProperty.get()) {
					if (cancelProperty.get()) {
						updateState(DownloadState.FAILED);
						updateProgress(0, 0);
						throw new DownloadServiceException("Download cancelled");
					} else {
						stopUntil();
					}
					updateState(DownloadState.DOWNLOADING);
				}
				length = is.read(b);

				i += length;

				updateProgress(i, httpConn.getContentLength() + 1);

				try {
					os.write(b, 0, length);
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					continue;
				}

				updateCompleted(BytesUtil.convertBytesToMegasBytes(i));

			}
		} catch (DownloadServiceException e) {
			// TODO: handle exception

			throw new IOException(e.getMessage());
		} finally {
			System.out.println("Done - " + fileName + " " + size);
			is.close();
			os.close();
		}
		updateProgress(i + 1, httpConn.getContentLength() + 1);
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName + type);
		System.out.println("REALLY OVER!");
		subItem.setDestination(downloadedFile.getAbsolutePath());
		updateState(DownloadState.FINISH);
		return downloadedFile;
	}

	public void checkSpeed() {

		new Thread(() -> {
			double start = 0;
			double end = 1;
			while (true) {

				try {
					start = completedProperty.get();
					Thread.sleep(1000);
					end = completedProperty.get();

					double downloadeSpeed = end - start;
//					System.out.println("Speed: " + downloadeSpeed);
//					System.out.println("Threads: " + Thread.currentThread().getId());
					updateSpeed(downloadeSpeed);

					if (stateProperty.getValue().equalsIgnoreCase("FAILED")
							|| stateProperty.getValue().equalsIgnoreCase("FINISH")) {
						updateSpeed(0);
						break;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	public long speedCalculation(Double downloadSpeed, long start, long end, int i) {
		// TODO Auto-generated method stub
		DecimalFormat four = new DecimalFormat("#0.00");

		downloadSpeed = Double.parseDouble(four.format(downloadSpeed).replaceAll(",", ".")) / 1048576;
		updateSpeed(downloadSpeed);

		return end;
	}

	private URL downloadSetup(String link) throws IOException {
		if (link == null) {
			updateState(DownloadState.FAILED);
			System.out.println("Error: " + subItem.getDestination());
			return null;
		}
		updateState(DownloadState.PREPARING);
		try {
			URL url = new URL(link);
			String referer = webData.getSite().getUrl();

			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
			httpConn.addRequestProperty("REFERER", referer);
			return url;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			updateState(DownloadState.FAILED);
			return null;
		}

	}

	@Override
	protected CollectionSubItem call() throws Exception {
		// TODO Auto-generated method stub
		try {
			download();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return subItem;
	}

	public void pause() {
		pauseProperty.set(true);
	}

	public void resume() {
		pauseProperty.set(false);

	}

	private void stopUntil() {
		updateState(DownloadState.PAUSE);
		while (getPauseProperty().get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		updateState(DownloadState.DOWNLOADING);
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	@Override
	public void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, ItemWebData webData) {
		// TODO Auto-generated method stub
		this.link = listLink.get(0);
		this.subItem = subItem;
		this.file = listFile.get(0);
		this.webData = webData;

	}

	public void finalize() {
		// TODO Auto-generated method stub

	}

}
