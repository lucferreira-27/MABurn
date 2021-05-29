package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.webscraping.PageInfo;
import com.lucas.ferreira.maburn.view.ShadeLayer;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ScreenshotFullDetails {
	private final BorderPane borderPane;
	private final ImageView imgScreeshotFull;
	private final ImageView imgZoomOut;
	private ShadeLayer shadeLayer;
	private SiteScreenshot siteScreenshot;
	
	public ScreenshotFullDetails(BorderPane borderPane,ShadeLayer shadeLayer, ImageView imgScreeshotFull, ImageView imgZoomOut) {
		this.borderPane = borderPane;
		this.shadeLayer = shadeLayer;
		this.imgScreeshotFull = imgScreeshotFull;
		this.imgZoomOut = imgZoomOut;
		siteScreenshot = new SiteScreenshot(imgScreeshotFull);
	}

	public void showDetails(PageInfo pageInfo) {
		borderPane.setVisible(true);
		siteScreenshot.loadImage(pageInfo.getImageSmall());
		siteScreenshot.showImage();
		imgZoomOut.setVisible(true);
		System.out.println("ShadeLayer: " + shadeLayer);
		System.out.println(shadeLayer.getRecShade());
		shadeLayer.show();

	}

	public void hideDetails() {
		borderPane.setVisible(false);
		siteScreenshot.hideImage();
		imgZoomOut.setVisible(false);
		shadeLayer.hide();
	}

	public BorderPane getBorderPane() {
		return borderPane;
	}

	public ShadeLayer getShadeLayer() {
		return shadeLayer;
	}
	public ImageView getImgZoomOut() {
		return imgZoomOut;
	}
	public ImageView getImgScreeshotFull() {
		return imgScreeshotFull;
	}

}
