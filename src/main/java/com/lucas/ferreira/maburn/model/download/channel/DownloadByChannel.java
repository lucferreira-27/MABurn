package com.lucas.ferreira.maburn.model.download.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileTypeDetection;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

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
	private static final int BUFFER_SIZE = 4096;

	public DownloadByChannel(ItemDownloadValues itemDownloadValues) {
		super(itemDownloadValues);
	}

	public ItemDownloadValues download(DownloadInfo downloadInfo) throws Exception{


			downloadInfos(downloadInfo);
			try {

				prefDownload();
				changeDownloadState(DownloadProgressState.DOWNLOADING);
				initTransfer();

			} catch (Exception e) {
				changeDownloadState(DownloadProgressState.FAILED);
				throw e;
			} finally {
				try {
					if (channel != null)
						closeChannel(channel);
					if (trackByteChannel != null)
						closeTrack(trackByteChannel);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changeDownloadState(DownloadProgressState.COMPLETED);
		return itemDownloadValues;
	}

	private void prefDownload() throws MalformedURLException, IOException, FileNotFoundException {
		URLConnection connection = newConnection();
		setDownloadSize(connection.getContentLengthLong());
		channel = newChannel(connection);
		setFileType(connection.getContentType());
		trackByteChannel = newTrackChannel(channel);
		itemDownloadValues.setName(filename);
		appendFilenameAndPath();
		newOutputStream(absolutePath);
	}

	private void downloadInfos(DownloadInfo downloadInfo) {
		url = downloadInfo.getUrl();
		filename = downloadInfo.getFilename();
		path = downloadInfo.getPath();
		referer = downloadInfo.getReferer();
		prefFileType = downloadInfo.getPrefFiletype();
	}

	private void setDownloadSize(long contentLenght) {
		double megabytes = BytesUtil.convertBytesToMegasBytes(contentLenght);
		itemDownloadValues.getDownloadSize().set(megabytes);

	}

	private void setFileType(String contentType) {
		String splitContentType = contentType.split("/")[1];
		String type = FileTypeDetection.isAcceptType(splitContentType) ? splitContentType : prefFileType;
		filename += "." + type;
	}

	private void appendFilenameAndPath() {
		absolutePath = path + filename;
	}

	private URLConnection newConnection() throws MalformedURLException, IOException {
		URLConnection connection = new URL(url).openConnection();
		connection.addRequestProperty("Referer", referer);
		return connection;
	}

	private TrackByteChannel newTrackChannel(ReadableByteChannel channel) {
		TrackByteChannel trackByteChannel = new TrackByteChannel(channel);
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

	private void closeTrack(TrackByteChannel trackByteChannel) throws IOException {
		trackByteChannel.close();
	}

	private void closeChannel(ReadableByteChannel channel) throws IOException {
		channel.close();
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