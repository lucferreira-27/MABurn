package com.lucas.ferreira.maburn.model.download.channel;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

import java.util.logging.Logger;

public abstract class DownloadProgressListener {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	protected ItemDownloadValues itemDownloadValues;

	public DownloadProgressListener(ItemDownloadValues itemDownloadValues) {
		this.itemDownloadValues = itemDownloadValues;
	}

	protected void listenerProgress() {
		itemDownloadValues.getTotalDownloaded().addListener((obs, oldvalue, newvalue) -> {
			itemDownloadValues.getDownloadProgress()
					.set(newvalue.doubleValue() / itemDownloadValues.getDownloadSize().doubleValue());
		});
	}

	protected void trackDownloadBytes(TrackByteChannel trackByteChannel) {
		trackByteChannel.setOnRead((readByte) -> {

			double megabytes = BytesUtil.convertBytesToMegasBytes(readByte);
			itemDownloadValues.getTotalDownloaded().set(megabytes);

		});
	}

	protected void checkSpeed() {
		new Thread(() -> {
			try {
				while (itemDownloadValues.getDownloadProgressState().get() == DownloadProgressState.DOWNLOADING) {
					double start = itemDownloadValues.getTotalDownloaded().get();

					Thread.sleep(1000); // Download speed per second

					double end = itemDownloadValues.getTotalDownloaded().get();
					double downloadSpeed = end - start;
					itemDownloadValues.getDownloadSpeed().set(downloadSpeed);
				}

			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			} finally {
				itemDownloadValues.getDownloadSpeed().set(0);
			}
		}).start();
	}

	protected void calculateTimeRemain() {
		itemDownloadValues.getDownloadSpeed().addListener((obs, oldvalue, newvalue) -> {
			double remain = itemDownloadValues.getDownloadSize().doubleValue()
					- itemDownloadValues.getTotalDownloaded().doubleValue();
			double time = remain / newvalue.doubleValue();
			itemDownloadValues.getTimeRemain().set(time);

		});

	}

	protected void changeDownloadState(DownloadProgressState downloadProgressState) {
		LOGGER.config("Download: " + itemDownloadValues.getName() + " changed state to " + downloadProgressState);
		itemDownloadValues.getDownloadProgressState().set(downloadProgressState);
	}
	protected void changeDownloadState(DownloadProgressState downloadProgressState, String msg) {
		LOGGER.config("Download: " + itemDownloadValues.getName() + " changed state to " + downloadProgressState);
		itemDownloadValues.getDownloadProgressState().set(downloadProgressState);
		itemDownloadValues.setMessage(msg);
	}

}
