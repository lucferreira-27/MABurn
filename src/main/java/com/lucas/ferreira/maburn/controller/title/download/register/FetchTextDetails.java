package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadModel;
import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;
import com.lucas.ferreira.maburn.view.LabelIcon;

import javafx.application.Platform;

public class FetchTextDetails {

	private TitleDownloadModel titleDownloadModel;

	private static final String ICON_PATH = "icons/";
	private Icon iconLabel;
	private Icon iconTab;
	private Icon iconLink;
	private Icon iconListForm;
	private Icon iconTime;

	public FetchTextDetails(TitleDownloadModel titleDownloadModel) {
		this.titleDownloadModel = titleDownloadModel;
		loadIcons();
	}

	private void loadIcons() {
		iconLabel = new Icon(titleDownloadModel.getImgLabel(), new IconConfig(ICON_PATH, Icons.LABEL_ICON));
		iconTab = new Icon(titleDownloadModel.getImgTab(), new IconConfig(ICON_PATH, Icons.TAB_ICON));
		iconLink = new Icon(titleDownloadModel.getImgDetailsLink(), new IconConfig(ICON_PATH, Icons.LINK));
		iconListForm = new Icon(titleDownloadModel.getImgListForm(), new IconConfig(ICON_PATH, Icons.FORM_LIST_ICON));
		iconTime = new Icon(titleDownloadModel.getImgTime(), new IconConfig(ICON_PATH, Icons.TIME_ICON));

		LabelIcon labelIconLabel = new LabelIcon(iconLabel, titleDownloadModel.getLblLabel());
		labelIconLabel.setOnMouseHover(
		(label) -> {
			labelIconLabel.alterIconColor();
		}, (label) -> {
			labelIconLabel.alterIconColor();
		});
		LabelIcon labelIconTab = new LabelIcon(iconTab, titleDownloadModel.getLblSiteTitle());
		labelIconTab.setOnMouseHover(
		(label) -> {
			labelIconTab.alterIconColor();
		}, (label) -> {
			labelIconTab.alterIconColor();
		});
		LabelIcon labelIconLink = new LabelIcon(iconLink, titleDownloadModel.getLblSiteUrl());
		labelIconLink.setOnMouseHover(
		(label) -> {
			labelIconLink.alterIconColor();
		}, (label) -> {
			labelIconLink.alterIconColor();
		});
		labelIconLink.setOnMousePressedLabel(
		(label) -> {
			LabelIcon.underline(label, true);
			LabelIcon.copyText(label);
		});
		labelIconLink.setOnMouseReleasedLabel(
		(label) -> {
			LabelIcon.underline(label, false);
		});
		
		LabelIcon labelIconListForm = new LabelIcon(iconListForm, titleDownloadModel.getLblItemsTotal());
		labelIconListForm.setOnMouseHover(
		(label) -> {
			labelIconListForm.alterIconColor();
		}, (label) -> {
			labelIconListForm.alterIconColor();
		});
		LabelIcon labelIconTime = new LabelIcon(iconTime, titleDownloadModel.getLblTime());
		labelIconTime.setOnMouseHover(
		(label) -> {
			labelIconTime.alterIconColor();
		}, (label) -> {
			labelIconTime.alterIconColor();
		});

	}

	public void setSiteName(String siteName) {

		if (siteName != null && !siteName.isEmpty()) {
			Platform.runLater(() -> {

				titleDownloadModel.getLblLabel().setText(siteName);
				iconLabel.setVisible(true);
			});

			titleDownloadModel.getLblLabel().setVisible(true);

		} else {
			titleDownloadModel.getLblLabel().setVisible(false);
			iconLabel.setVisible(false);
		}
	}

	public void setSiteTitle(String siteTitle) {

		if (siteTitle != null && !siteTitle.isEmpty()) {
			Platform.runLater(() -> {

				titleDownloadModel.getLblSiteTitle().setText(siteTitle);
				iconTab.setVisible(true);
			});

			titleDownloadModel.getLblSiteTitle().setVisible(true);

		} else {
			titleDownloadModel.getLblSiteTitle().setVisible(false);
			iconTab.setVisible(false);
		}
	}

	public void setSiteUrl(String url) {

		if (url != null && !url.isEmpty()) {
			Platform.runLater(() -> {

				titleDownloadModel.getLblSiteUrl().setText(url);
				iconLink.setVisible(true);
			});

			titleDownloadModel.getLblSiteUrl().setVisible(true);

		} else {
			titleDownloadModel.getLblSiteUrl().setVisible(false);
			iconLink.setVisible(false);
		}
	}

	public void setTotalItems(String totalItems) {
		if (totalItems != null && !totalItems.isEmpty()) {
			Platform.runLater(() -> {

				titleDownloadModel.getLblItemsTotal().setText(totalItems);
				iconListForm.setVisible(true);
			});

			titleDownloadModel.getLblItemsTotal().setVisible(true);

		} else {
			titleDownloadModel.getLblItemsTotal().setVisible(false);
			iconListForm.setVisible(false);
		}
	}

	public void setTime(String time) {
		if (time != null && !time.isEmpty()) {
			Platform.runLater(() -> {

				titleDownloadModel.getLblTime().setText(time);
				iconTime.setVisible(true);
			});

			titleDownloadModel.getLblTime().setVisible(true);

		} else {
			titleDownloadModel.getLblTime().setVisible(false);
			iconTime.setVisible(false);
		}
	}

}
