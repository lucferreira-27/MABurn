package com.lucas.ferreira.maburn.model.download.queue;

import javafx.concurrent.Task;

public class DownloadQueue extends Task<Void> {
	private boolean live = true;
	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		live();
		return null;
	}

	public void live() {
		while(live) {
			try {
				
				Thread.sleep(100);
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public void kill() {
		live = false;
	}
	
	public void addDownload(TitleDownload titleDownload) {

	}

	public void removeDownload(TitleDownload titleDownload) {

	}

}
