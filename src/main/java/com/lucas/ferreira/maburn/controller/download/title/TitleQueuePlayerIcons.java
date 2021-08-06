package com.lucas.ferreira.maburn.controller.download.title;

import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.view.IconVisibility;
import com.lucas.ferreira.maburn.view.InteractIcons;

public class TitleQueuePlayerIcons implements InteractIcons {

	private IconVisibility iconVisibility = new IconVisibility();
	private DownloadTitleInQueueModal modal;
	private DownloadTitleInQueueController controller;
	public TitleQueuePlayerIcons(DownloadTitleInQueueModal modal, DownloadTitleInQueueController controller) {
		this.modal = modal;
		this.controller = controller;
	}
	@Override
	public void interactTurnOn() {
		onActiveIconPlay();
		onActiveIconStop();
		onActiveIconPause();
		
	}


	private void onActiveIconPlay() {
		Icon iconPlay = (Icon) modal.getImgPlay().getUserData();
		Icon iconPause = (Icon) modal.getImgPause().getUserData();
		iconPlay.setProperties(event -> {
	
			controller.resume();
			iconPlay.setVisible(false);
			iconPause.setVisible(true);

		});
	}

	private void onActiveIconStop() {
		Icon iconStop = (Icon) modal.getImgStop().getUserData();
		iconStop.setProperties(event -> {
			controller.stop();
		});
	}

	private void onActiveIconPause() {
		Icon iconPlay = (Icon) modal.getImgPlay().getUserData();
		Icon iconPause = (Icon) modal.getImgPause().getUserData();

		iconPause.setProperties(event -> {
			
			controller.pause();
			iconPause.setVisible(false);
			iconPlay.setVisible(true);
		});
	}
	
	
	public void disableAll() {
		iconVisibility.disableIcon(modal.getImgPause());
		iconVisibility.disableIcon(modal.getImgPlay());
		iconVisibility.disableIcon(modal.getImgStop());
	}
	public void enableAll() {
		iconVisibility.enableIcon(modal.getImgPause());
		iconVisibility.enableIcon(modal.getImgPlay());
		iconVisibility.enableIcon(modal.getImgStop());
	}
	public void hideAll() {
		iconVisibility.hideIcon(modal.getImgPause());
		iconVisibility.hideIcon(modal.getImgPlay());
		iconVisibility.hideIcon(modal.getImgStop());

	}
	
	
	public void showAll() {
		iconVisibility.showIcon(modal.getImgPause());
		iconVisibility.showIcon(modal.getImgPlay());
	}



}
