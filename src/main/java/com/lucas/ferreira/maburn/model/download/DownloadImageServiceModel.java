package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class DownloadImageServiceModel extends Downloader<File> {

	private String link;
	private File file;
	private HttpURLConnection httpConn;
	private String fileName;

	public DownloadImageServiceModel(String link, File file) {
		// TODO Auto-generated constructor stub
		this.link = link;
		this.file = file;
	}

	public File download() throws IOException {
		progressProperty().addListener((obs, oldValue, newValue) -> {
			CustomLogger.log(("[Download Image] " + fileName + "[" + (int) (newValue.doubleValue() * 100) + "%]"));

		});
		CustomLogger.log(file.getAbsolutePath() + link);
		URL url = null;
		String failedMsg = "";
		try {
			downloadSetup(link);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			failedMsg = e.getMessage();
		}
		if (url == null) {
			new ConnectException(failedMsg);
		}
		return startDownload(url);

	}

	private File startDownload(URL url) throws IOException {
		String path = file.getAbsolutePath();
		String type = ".jpg";

		fileName = path.substring(path.lastIndexOf("\\") + 1);
		String destination = path.substring(0, path.lastIndexOf("\\") + 1);

		File location = new File(destination);
		InputStream is;

		is = ConnectionModel.getInputStream(httpConn, 3);

		location.mkdirs();
		fileName += type;

		CustomLogger.log(("[Download Image] (" + link + ")"));
		
		OutputStream os = new FileOutputStream(location.getAbsolutePath() + "\\"  +fileName);
		double size = (double) httpConn.getContentLength() / 1048576;

		byte[] b = new byte[BUFFER_SIZE];
		int length = 0;
		int i = 0;

		updateSize(size);

		while (length != -1) {
			if (pauseProperty.get()) {
				stopUntil();
			}

			length = is.read(b);

			// updateSpeed(speedCalculation(startTime, endTime, length));

			i += BUFFER_SIZE;
			updateProgress(i, httpConn.getContentLength() + 1);

			try {
				os.write(b, 0, length);
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
				continue;
			}
		}

		is.close();
		os.close();
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName);
		// CustomLogger.log("REALLY OVER!");
		updateProgress(i + 1, httpConn.getContentLength() + 1);

		return downloadedFile;
	}

	private void downloadSetup(String link) throws IOException {
		httpConn = ConnectionModel.openConnection(link, 3);
	}

	@Override
	protected File call() throws Exception {
		// TODO Auto-generated method stub
		CustomLogger.log("!");
		return download();
	}



	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	private void stopUntil() {
		while (pauseProperty.get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
