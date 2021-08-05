package com.lucas.ferreira.maburn.controller.download.title;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.lucas.ferreira.maburn.controller.Controller;
import com.lucas.ferreira.maburn.controller.title.download.title.Title;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadListCard;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.LabelIcon;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.scene.image.Image;

public class DownloadTitleInQueueController implements Controller {
	private static final String ICON_PATH = "icons/";

	private DownloadTitleInQueueModal modal;

	private Title title;

	public DownloadTitleInQueueController(DownloadTitleInQueueModal modal,
			TitleDownloadListCard titleDownloadListCard) {
		this.modal = modal;
		this.title = titleDownloadListCard.getTitle();
	}

	@Override
	public void initialize() {
		initializeIcons();
		initializeTitleInfos();
	}

	private void initializeTitleInfos() {

		String strTittle = title.getCollectionTitle().getTitleDataBase();
		modal.getLblTitle().setText(strTittle);

		Image image = title.getCollectionTitle().getImage();
		modal.getImgTitleCover().setImage(image);

		String dest = title.getCollectionTitle().getDestination();
		modal.getLblFolder().setText(dest);
	}

	private void initializeIcons() {
		Icon iconComplete = new Icon(modal.getImgCompleted(), new IconConfig(ICON_PATH, Icons.COMPLETED_ICON));
		Icon iconError = new Icon(modal.getImgErros(), new IconConfig(ICON_PATH, Icons.ERROR_ICON));
		Icon iconDownloading = new Icon(modal.getImgDownloads(), new IconConfig(ICON_PATH, Icons.DOWNLOADING_ICON));
		Icon iconLaunch = new Icon(modal.getImgLaunch(), new IconConfig(ICON_PATH, Icons.LAUNCH_ICON));
		iconLaunch.setProperties(click -> openTitleDownload());
		Icon iconPlay = new Icon(modal.getImgPause(), new IconConfig(ICON_PATH, Icons.PLAY_IN_CARD));
		Icon iconStop = new Icon(modal.getImgStop(), new IconConfig(ICON_PATH, Icons.STOP_IN_CARD));
		Icon iconTime = new Icon(modal.getImgTime(), new IconConfig(ICON_PATH, Icons.TIME_ICON));
		Icon iconFolder = new Icon(modal.getImgFolder(), new IconConfig(ICON_PATH, Icons.OPEN_FOLDER_ICON));

		LabelIcon labelIconFolder = new LabelIcon(iconFolder, modal.getLblFolder());
		labelIconFolder.setOnMouseHover((label) -> {

			labelIconFolder.alterIconColor();
			LabelIcon.underline(label, true);

		}, (label) -> {
			labelIconFolder.alterIconColor();
			LabelIcon.underline(label, false);
		});
		labelIconFolder.setOnMousePressedLabel((label) -> {
			try {
				Desktop.getDesktop().open(new File(label.getText()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	private void openTitleDownload() {
		Navigator navigator = new Navigator();
		try {
			navigator.openFromRegisteredState(Interfaces.TITLE_DOWNLOAD,
					String.valueOf(title.getCollectionTitle().getId()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
