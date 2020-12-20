package com.lucas.ferreira.maburn.model.download.service.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;

public class DownloadImageServiceModel extends Downloader<File>  {
	
	private String link;
	private File file;
	private HttpURLConnection httpConn;

	
	public DownloadImageServiceModel(String link, File file) {
		// TODO Auto-generated constructor stub
		this.link = link;
		this.file = file;
	}
	
	public File download() throws IOException {
		System.out.println(file.getAbsolutePath() + link);
		URL url = downloadSetup(link);
		return startDownload(url);

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
		InputStream is;
		try {
		is = httpConn.getInputStream();
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println(httpConn.getConnectTimeout());
			throw new IOException(e.getMessage());
		}
		location.mkdirs();
		fileName += type;
		OutputStream os = new FileOutputStream(location.getAbsolutePath() + "\\" + fileName);
		double size = (double) httpConn.getContentLength() / 1048576;

		byte[] b = new byte[BUFFER_SIZE];
		int length;
		int i = 0;
		updateSize(size);
		//System.out.println("Download - " + fileName + " " + httpConn.getContentLength());
		while ((length = is.read(b)) != -1) {
			if (pauseProperty.get()) {
				stopUntil();
			}
			i += BUFFER_SIZE;
			updateProgress(i, httpConn.getContentLength() + 1);
			updateSpeed(speedCalculation());
			Platform.runLater(() -> {
				downloadProgress.set(getProgress());
			});
			os.write(b, 0, length);

		}
		//System.out.println("Done - " + fileName + " " + size);
		is.close();
		os.close();
		File downloadedFile = new File(location.getAbsolutePath() + "\\" + fileName);
		//System.out.println("REALLY OVER!");
		updateProgress(i + 1, httpConn.getContentLength() + 1);
		
		return downloadedFile;
	}

	private URL downloadSetup(String link) throws IOException {

		URL url = new URL(link);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setReadTimeout(0);
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
		return url;
	}

	@Override
	protected File call() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("!");
		return download();
	}

	@Override
	public void initialize(List<String> listLink, CollectionSubItem subItem, List<File> listFile, Sites sites) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DoubleProperty getDownloadProgress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleProperty getSizeProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleProperty getDownloadSpeedProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanProperty getPauseProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double speedCalculation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unpause() {
		// TODO Auto-generated method stub
		
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


		

}
