package com.lucas.ferreira.maburn.model.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.util.MathUtil;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Download {

	private static int div = 4;

	public static void main(String[] args) {
		
		for(int si = 0; si < 16; si++) {
		div = si + 1;
		Download download = new Download();
		DownloadFilename downloadFilename = new DownloadFilename("D:\\Teste\\test" + div +".mp4", "Test1.mp4");
		String url = "https://ns538468.ip-144-217-72.net/T/teekyuu/01.mp4";
		BooleanProperty joined = new SimpleBooleanProperty(false);
		EpisodeDownloadItemValues episodeDownloadItemValues = new EpisodeDownloadItemValues("Episode 01", url);
		episodeDownloadItemValues.getDownloadProgress().addListener((obs, oldvalue, newvalue) -> {
			double progress = MathUtil.roundDouble(newvalue.doubleValue(), 2);
			if (progress == 1) {
				String temp = System.getProperty("java.io.tmpdir");
				temp += "part";
				if (!joined.get()) {
					try {
						List<File> files = new ArrayList<File>();
						for (int i = 0; i < div; i++) {
							files.add(new File(temp + i));
						}
						mergeFiles(files, new File(downloadFilename.getPath()));
						joined.set(true);
						System.out.println("join");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// System.out.println(downloadFilename.getPath());
				}
			}
//			System.out.println(
//					"Progress: " + progress + "   " + episodeDownloadItemValues.getTotalDownloaded().doubleValue() + " "
//							+ episodeDownloadItemValues.getDownloadSize().doubleValue());
		});
			download.download(episodeDownloadItemValues, new DownloadFilename("D:\\Teste\\test", "Test.mp4"));
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ItemDownloadValues download(ItemDownloadValues itemDownloadValues, DownloadFilename downloadFilename) {
		try {

			URLConnection con = newConnection(itemDownloadValues.getDirectLink());
			con.setRequestProperty("Referer", "https://www.anitube.site");

			double size = BytesUtil.convertBytesToMegasBytes(con.getContentLength());
			System.out.println(size);
			itemDownloadValues.getDownloadSize().set(size);

			itemDownloadValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {

				itemDownloadValues.getDownloadProgress()
						.set(newvalue.doubleValue() / itemDownloadValues.getDownloadSize().doubleValue());
			});

			long part = con.getContentLength() / div;

			// TODO DOWNLOAD EACH PART
			long[] parts = new long[div];
			for (int i = 0; i < div; i++) {
				parts[i] = part * (i + 1);
			}
			String[] ranges = new String[parts.length];
			for (int i = 0; i < div; i++) {
				String range = "bytes=";

				if (i <= 0) {
					range += "0-";
				} else {
					range += parts[i - 1] + "-";
				}
				range += parts[i];
				ranges[i] = range;
			}
			for (int i = 0; i < ranges.length; i++) {
				String range = ranges[i];
				int tempPart = i;
				new Thread(() -> {
					String url = "https://ns538468.ip-144-217-72.net/T/teekyuu/01.mp4";

					EpisodeDownloadItemValues episodeDownloadItemValues = new EpisodeDownloadItemValues(
							"part" + tempPart, url);
					episodeDownloadItemValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {
						synchronized (this) {
							// System.out.println(episodeDownloadItemValues.getName() + " " +
							// newvalue.doubleValue());
							double total = itemDownloadValues.getTotalDownloaded().doubleValue()
									- oldvalue.doubleValue();
							double partTotal = newvalue.doubleValue();
							itemDownloadValues.getTotalDownloaded().set(MathUtil.roundDouble(total + partTotal, 2));

						}

//						System.out.println(episodeDownloadItemValues.getName() + " Progress: "
//								+ MathUtil.roundDouble(newvalue.doubleValue(), 3));
					});
					try {
						URLConnection partConnection = newConnectionPart(episodeDownloadItemValues.getDirectLink(),
								range);
						String temp = System.getProperty("java.io.tmpdir");
						DownloadFilename downloadPart = new DownloadFilename(temp + "part" + tempPart,
								episodeDownloadItemValues.getName());
						// System.out.println(downloadPart.getPath());
						download(episodeDownloadItemValues, downloadPart, partConnection);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}).start();
			}

			return itemDownloadValues;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private void download(ItemDownloadValues itemDownloadValues, DownloadFilename downloadFilename, URLConnection con)
			throws IOException, FileNotFoundException {
		double size = BytesUtil.convertBytesToMegasBytes(con.getContentLength());
		itemDownloadValues.getDownloadSize().set(size);
		ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());

		ReadableConsumerByteChannel consumerByteChannel = new ReadableConsumerByteChannel(rbc, (b) -> {

			itemDownloadValues.getTotalDownloaded().set(BytesUtil.convertBytesToMegasBytes(b));

		});
		FileOutputStream fos = new FileOutputStream(downloadFilename.getPath());

		itemDownloadValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {

			itemDownloadValues.getDownloadProgress()
					.set(newvalue.doubleValue() / itemDownloadValues.getDownloadSize().doubleValue());
		});

		fos.getChannel().transferFrom(consumerByteChannel, 0, Long.MAX_VALUE);
	}

	public URLConnection newConnectionPart(String url, String range) throws MalformedURLException, IOException {
		URLConnection con = newConnection(url);
		con.setRequestProperty("Range", range);
		// System.out.println("range " + range);
		con.setRequestProperty("Referer", "https://www.anitube.site");

		return con;
	}

	public URLConnection newConnection(String url) throws MalformedURLException, IOException {
		return new URL(url).openConnection();
	}

	public static void mergeFiles(List<File> files, File into) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(into);
				BufferedOutputStream mergingStream = new BufferedOutputStream(fos)) {
			for (File f : files) {
				Files.copy(f.toPath(), mergingStream);
				System.out.println(BytesUtil.convertBytesToMegasBytes(f.length()));
			}
		}
		System.out.println(into.canExecute());
	}

}

class DownloadProgress {
	private double progress;

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
}

class ReadableConsumerByteChannel implements ReadableByteChannel {

	private final ReadableByteChannel rbc;
	private final IntConsumer onRead;

	private int totalByteRead;

	public ReadableConsumerByteChannel(ReadableByteChannel rbc, IntConsumer onBytesRead) {
		this.rbc = rbc;
		this.onRead = onBytesRead;
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
