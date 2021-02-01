package com.lucas.ferreira.maburn.model.download.queue;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javafx.concurrent.Task;

public class DownloadQueue extends Task<Void> {
	private static DownloadQueue queue;
	private ArrayList<TitleDownload> downloadList = new ArrayList<TitleDownload>();
	private boolean live = true;

	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		live();
		return null;
	}

	public static DownloadQueue getInstance() {
		if (queue == null) {
			queue = new DownloadQueue();
		}
		return queue;
	}

	public void live() {
		while (live) {
			try {

				Thread.sleep(100);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void kill() {
		live = false;
	}

	public void addDownload(TitleDownload titleDownload) {
	
		if (getDownload(titleDownload.getId()) == null)
			downloadList.add(titleDownload);
	}

	public void removeDownload(TitleDownload titleDownload) {
		downloadList.remove(titleDownload);

	}

	public void removeDownload(int id) {
		downloadList.remove(getDownload(id));

	}

	public TitleDownload getDownload(int id) {
		try {
			return downloadList.stream().filter(download -> download.getId() == id).findAny().get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return null;
		}
	}

	public ArrayList<TitleDownload> getDownloadList() {
		return downloadList;
	}

}
