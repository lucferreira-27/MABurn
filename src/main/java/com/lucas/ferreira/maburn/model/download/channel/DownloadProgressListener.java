package com.lucas.ferreira.maburn.model.download.channel;

import javax.management.RuntimeErrorException;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

public abstract class DownloadProgressListener {
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
		trackByteChannel.setOnRead((readedByte) -> {

			double megabytes = BytesUtil.convertBytesToMegasBytes(readedByte);
			itemDownloadValues.getTotalDownloaded().set(megabytes);

		});
	}

	protected void checkSpeed() {
		new Thread(() -> {
			try {
				while (itemDownloadValues.getDownloadProgressState().get() == DownloadProgressState.DOWNLOADING) {
					double start = itemDownloadValues.getTotalDownloaded().get();

					Thread.sleep(1000);

					double end = itemDownloadValues.getTotalDownloaded().get();
					double downloadSpeed = end - start;
//					if(downloadSpeed == 0) {
//						continue;
//					}
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


		CustomLogger.log(downloadProgressState.toString());
		itemDownloadValues.getDownloadProgressState().set(downloadProgressState);
	}

}
