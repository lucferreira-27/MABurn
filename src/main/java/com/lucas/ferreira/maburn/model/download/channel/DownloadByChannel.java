package com.lucas.ferreira.maburn.model.download.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lucas.ferreira.maburn.controller.title.download.HttpResponseCode;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.exceptions.FileTypeNotSupportException;
import com.lucas.ferreira.maburn.exceptions.HttpResponseCodeException;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileTypeDetection;
import com.lucas.ferreira.maburn.model.download.URLFixer;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.FileArchive;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

import net.lingala.zip4j.ZipFile;

public class DownloadByChannel extends DownloadProgressListener {

	protected String url;
	protected String filename;
	protected String absolutePath;
	protected String path;
	protected String referer;
	protected String prefFileType;
	protected FileOutputStream fos;
	protected ReadableByteChannel channel;
	protected TrackByteChannel trackByteChannel;

	public DownloadByChannel(ItemDownloadValues itemDownloadValues) {

		super(itemDownloadValues);

	}

	public ItemDownloadValues download(DownloadInfo downloadInfo) throws Exception {

		downloadInfos(downloadInfo);
		try {

			prefDownload();
			changeDownloadState(DownloadProgressState.DOWNLOADING);
			initTransfer();

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof FileTypeNotSupportException)
				changeDownloadState(DownloadProgressState.FAILED, "Type Unsupported");
			else if (e instanceof HttpResponseCodeException)
				changeDownloadState(DownloadProgressState.FAILED, e.getMessage());
			else
				changeDownloadState(DownloadProgressState.FAILED);
			throw e;
		} finally {
			try {
				if (channel != null)
					closeChannel(channel);
				if (trackByteChannel != null)
					closeTrack(trackByteChannel);
				if (fos != null) {
					closeOutPutStream(fos);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		finishDownload();

		return itemDownloadValues;
	}

	private void finishDownload() throws Exception {


		if (itemDownloadValues.getDownloadProgressState().get() != DownloadProgressState.CANCELED)
			changeDownloadState(DownloadProgressState.COMPLETED);
	}

	private void prefDownload() throws MalformedURLException, IOException, FileNotFoundException,
			FileTypeNotSupportException, HttpResponseCodeException {
		URLConnection connection = newConnection();
		setDownloadSize(connection.getContentLengthLong());
		setDownloadUrl(url);
		channel = newChannel(connection);
		setFileType(connection.getContentType());
		trackByteChannel = newTrackChannel(channel);
		itemDownloadValues.getName().set(filename);
		appendFilenameAndPath();
		newOutputStream(absolutePath);
	}

	private void downloadInfos(DownloadInfo downloadInfo) {
		CustomLogger.log(downloadInfo.toString());

		url = downloadInfo.getUrl();
		filename = downloadInfo.getFilename();
		path = downloadInfo.getRoot();
		referer = downloadInfo.getReferer();
		prefFileType = downloadInfo.getPrefFiletype().getName();
	}

	private void setDownloadSize(long contentLenght) {
		double megabytes = BytesUtil.convertBytesToMegasBytes(contentLenght);
		itemDownloadValues.getDownloadSize().set(megabytes);

	}

	private void setDownloadUrl(String url) {
		itemDownloadValues.getDirectLink().set(url);
	}

	private void setFileType(String contentType) throws FileTypeNotSupportException {
		String splitContentType = contentType.split("/")[1];
		String type = FileTypeDetection.isAcceptType(splitContentType) ? splitContentType : prefFileType;
		if(type.equals("octet-stream")) {
			type = "zip";
		}
		filename += "." + type;
	}

	private void appendFilenameAndPath() {
		absolutePath = path + "\\" + filename;
	}

	private URLConnection newConnection() throws MalformedURLException, IOException, HttpResponseCodeException {
		if (URLFixer.needBeFixed(url)) {
			url = URLFixer.addHttpInUrl(url);
		}
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Method", "GET");
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
		connection.setRequestProperty("Referer", referer);

		CustomLogger.log("Request Propeties: " + connection.getRequestProperties());

		HttpResponseCode httpResponseCode = new HttpResponseCode();
		int code = httpResponseCode.getCode((HttpURLConnection) connection);
		CustomLogger.log("Response Code: " + code);
		return connection;
	}

	private TrackByteChannel newTrackChannel(ReadableByteChannel channel) {
		trackByteChannel = new TrackByteChannel(channel);
		trackDownloadBytes(trackByteChannel);
		return trackByteChannel;

	}

	private ReadableByteChannel newChannel(URLConnection con) throws IOException {
		return Channels.newChannel(con.getInputStream());
	}

	private void newOutputStream(String path) throws FileNotFoundException {
		fos = new FileOutputStream(path);

	}

	protected void initTransfer() throws IOException {
		listenerProgress();
		checkSpeed();
		calculateTimeRemain();
		fos.getChannel().transferFrom(trackByteChannel, 0, Long.MAX_VALUE);

	}

	public void pause() {
		if (trackByteChannel != null) {
			trackByteChannel.setRunning(false);
			changeDownloadState(DownloadProgressState.PAUSE);
		}
	}

	public void resume() {
		if (trackByteChannel != null) {
			trackByteChannel.setRunning(true);
			changeDownloadState(DownloadProgressState.DOWNLOADING);
		}
	}

	public void stop() {
		try {
			changeDownloadState(DownloadProgressState.CANCELED);
			if (fos != null)
				fos.close();
			if (channel != null)
				channel.close();
			if (trackByteChannel != null) {
				trackByteChannel.close();
				trackByteChannel.setRunning(false);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void closeTrack(TrackByteChannel trackByteChannel) throws IOException {
		trackByteChannel.close();
	}

	private void closeChannel(ReadableByteChannel channel) throws IOException {
		channel.close();
	}

	private void closeOutPutStream(FileOutputStream fos) {
		try {
			fos.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public FileOutputStream getFos() {
		return fos;
	}

	public void setFos(FileOutputStream fos) {
		this.fos = fos;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

}
