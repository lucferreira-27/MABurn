package com.lucas.ferreira.maburn.model.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadValues;

import javafx.beans.value.ChangeListener;

public class DownloadRealTimeInfo {

	private boolean showInfoEnable = false;
	private ChangeListener<Number> changeListenerProgress;
	
	public void stopShowInfo() {
		showInfoEnable = false;
	}
	
	public void showInfoForTime(DownloadValues downloadValues, long time) {
		showInfoEnable = true;
		while (showInfoEnable || !downloadIsRunning(downloadValues)) {

			printInfos(downloadValues);
			sleep(time);
		}
	}


	public void showInfoWithProgress(DownloadValues downloadValues) {
		changeListenerProgress = (obs, oldValue, newValue) -> {
			printInfos(downloadValues);
			if(showInfoEnable || !downloadIsRunning(downloadValues)) {
				removeChangeListerProgress(downloadValues);
			}
		};
		downloadValues.getDownloadProgress().addListener(changeListenerProgress);
	}
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private boolean downloadIsRunning(DownloadValues downloadValues) {
		DownloadProgressState downloadProgressState = downloadValues.getDownloadProgressState().get();
		
		if(downloadProgressState == DownloadProgressState.COMPLETED || downloadProgressState == DownloadProgressState.FAILED) {
			return false;
		}
		
		return true;
	}
	
	private void removeChangeListerProgress(DownloadValues downloadValues) {
		downloadValues.getDownloadProgress().removeListener(changeListenerProgress);

	}
	private void printInfos(DownloadValues downloadValues) {
		System.out.println("\n - Download Real Time Infos - ");
		System.out.println(downloadValues.toString());
		System.out.println("\n\n");
	}
}
