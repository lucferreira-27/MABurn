package com.lucas.ferreira.maburn.controller.title.download.register;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class FetchTextDetails {

	private final Label lblSiteTitle;
	private final Label lblSource;
	private final Label lblUrl;
	private final Label lblItemsTotal;

	private String siteTitle;
	private String url;
	private String itemsTotal;
	private String source;

	public FetchTextDetails(Label lblSiteTitle, Label lblSource, Label lblUrl, Label lblItemsTotal) {
		this.lblSiteTitle = lblSiteTitle;
		this.lblSource = lblSource;
		this.lblUrl = lblUrl;
		this.lblItemsTotal = lblItemsTotal;
	}

	public String getSiteTitle() {
		return siteTitle;
	}

	public void setSiteTitle(String siteTitle) {
		System.out.println("!New Title: " + siteTitle );

		if (siteTitle != null && !siteTitle.isEmpty()) {
			Platform.runLater(() ->{
				System.out.println("New Title: " + siteTitle );
				lblSiteTitle.setText(siteTitle);
				System.out.println(lblSiteTitle);
				});

			lblSiteTitle.setVisible(true);
			this.siteTitle = siteTitle;
		} else {
			lblSiteTitle.setVisible(false);
		}
	}

	public String getUrl() {
		return url;
	}

	public String getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(String itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Label getLblUrl() {
		return lblUrl;
	}

}
