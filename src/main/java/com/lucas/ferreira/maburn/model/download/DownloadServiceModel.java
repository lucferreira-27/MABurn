package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import com.lucas.ferreira.maburn.exceptions.DownloadServiceException;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

import javafx.beans.property.BooleanProperty;

public class DownloadServiceModel extends Downloader<CollectionItem> implements ReadableByteChannel {
	private ReadableByteChannel rbc;
	private int totalByteRead;

	public DownloadServiceModel() {
		
	}

	public File download() throws IOException {
		updateName(subItem.getName());
		URL url = downloadSetup(super.listLink.get(0));
		if (url == null) {
			updateState(DownloadState.FAILED);
			return null;
		}
		startDownload(url);

		return null;
	}

	private File startDownload(URL url) throws IOException {


		if (listLink.size() == 1)
			beginReader(listLink.get(0));
		else {

			beginReaderAll(listLink);

		}
		return null;
	}

	private File getURLFileProprieres(URL url, int i) {
		String path = super.listFile.get(i).getAbsolutePath();
		String type = null;

		try {
			type = url.getPath().substring(url.getPath().lastIndexOf("."));
		} catch (Exception e) {
			
			type = ".mp4";
		}
		String fileName = path.substring(path.lastIndexOf("\\") + 1);
		String destination = path.substring(0, path.lastIndexOf("\\") + 1);

		File location = new File(destination);
		new Thread(() ->{
			double size = BytesUtil.convertBytesToMegasBytes(httpConn.getContentLength());
			updateSize(size + sizeProperty.get());
		}).start();

		location.mkdirs();
		
		File file = new File(location.getAbsolutePath() + "\\" + fileName + type);
		return file;
	}

	public void beginReader(String link) {

		updateState(DownloadState.READY);
		try {
			
			URL url = downloadSetup(link);
			File location = getURLFileProprieres(url, 0);
			rbc = Channels.newChannel(httpConn.getInputStream());

			FileOutputStream fos = new FileOutputStream(location);

			updateState(DownloadState.DOWNLOADING);
			checkSpeed();

			
			
			fos.getChannel().transferFrom(this, 0, Long.MAX_VALUE);
			fos.close();
			subItem.setDestination(location.getAbsolutePath());

			updateProgress(1, 1);

			updateState(DownloadState.FINISH);
		} catch (Exception e) {
			
			e.printStackTrace();
			updateState(DownloadState.FAILED);
			failedProperty.set(true);
		}
	}
	

	public void beginReaderAll(List<String> links) {

		updateState(DownloadState.READY);
		List<URL> urls = new ArrayList<URL>();
		List<File> locations = new ArrayList<File>();
		List<ReadableByteChannel> readables = new ArrayList<ReadableByteChannel>();

		try {
			checkSpeed();

			for (int i = 0; i < links.size(); i++) {

				URL url = downloadSetup(links.get(i));
				urls.add(url);
				File location = getURLFileProprieres(url, i);
				locations.add(location);

				// CustomLogger.log(links.get(i));
				// CustomLogger.log(location.getAbsolutePath());

				rbc = Channels.newChannel(httpConn.getInputStream());
				readables.add(rbc);
			}

			for (int i = 0; i < urls.size(); i++) {
				File location = locations.get(i);
				rbc = readables.get(i);

				FileOutputStream fos = new FileOutputStream(location);

				updateState(DownloadState.DOWNLOADING);
				fos.getChannel().transferFrom(this, 0, Long.MAX_VALUE);
				fos.close();
			}
			subItem.setDestination(locations.get(0).getParent());
			updateProgress(1, 1);

			updateState(DownloadState.FINISH);
		} catch (Exception e) {
			
			e.printStackTrace();
			updateState(DownloadState.FAILED);
			failedProperty.set(true);
		}
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
					updateSpeed(downloadeSpeed);

					if (stateProperty.getValue() == DownloadState.FAILED
							|| stateProperty.getValue() == DownloadState.FINISH) {
						updateSpeed(0);
						break;
					}
				} catch (InterruptedException e) {
					 
					e.printStackTrace();
				}
			}
		}).start();

	}

	public long speedCalculation(Double downloadSpeed, long start, long end, int i) {
		
		DecimalFormat four = new DecimalFormat("#0.00");

		downloadSpeed = Double.parseDouble(four.format(downloadSpeed).replaceAll(",", ".")) / 1048576;
		updateSpeed(downloadSpeed);

		return end;
	}

	private URL downloadSetup(String link) throws IOException {
		if (link == null) {
			updateState(DownloadState.FAILED);
			failedProperty.set(true);
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
						e.printStackTrace();
			return null;
		}

	}

	@Override
	protected CollectionItem call() throws Exception {
		
		try {
			download();
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return subItem;
	}

	public void pause() {
		updateState(DownloadState.PAUSING);
		pauseProperty.set(true);
	}

	public void resume() {
		pauseProperty.set(false);

	}

	public void refresh() {
		try {
			pauseProperty.set(false);
			cancelProperty.set(false);
			download();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}

	}

	protected void stopUntil() {
		updateState(DownloadState.PAUSE);
		while (getPauseProperty().get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}
		}
		updateState(DownloadState.DOWNLOADING);
	}

	public BooleanProperty getPauseProperty() {
		return pauseProperty;
	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		int nRead = rbc.read(dst);
		notifyBytesRead(nRead);
		return nRead;
	}

	protected void notifyBytesRead(int nRead) {
		if (nRead <= 0) {
			return;
		}
		totalByteRead += nRead;
		updateCompleted(BytesUtil.convertBytesToMegasBytes(totalByteRead));
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
		IntConsumer onRead = new IntConsumer() {

			@Override
			public void accept(int value) {
				
				updateProgress(value, BytesUtil.convertMegasBytesToBytes(sizeProperty.get()));

			}
		};
		onRead.accept(totalByteRead);

	}

	@Override
	public boolean isOpen() {
		return rbc.isOpen();
	}

	@Override
	public void close() throws IOException {
		rbc.close();
	}

}
