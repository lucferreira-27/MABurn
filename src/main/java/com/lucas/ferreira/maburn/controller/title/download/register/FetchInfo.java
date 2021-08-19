package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.model.webscraping.PageInfo;
import com.lucas.ferreira.maburn.util.TimeText;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FetchInfo {
	
	private PageInfo pageInfo;
	private final ImageView imgScreeshotSmall;
	private SiteScreenshot siteScreenshotSmall;
	private SiteScreenshot siteScreenshotFull;
	private ScreenshotFullDetails screenshotFullDetails;
	private FetchTextDetails fetchTextDetails;
	
	public FetchInfo(PageInfo pageInfo, ImageView imgScreeshotSmall, ScreenshotFullDetails screenshotFullDetails, FetchTextDetails fetchTextDetails) {
		this.imgScreeshotSmall = imgScreeshotSmall;
		this.pageInfo = pageInfo;
		this.fetchTextDetails = fetchTextDetails;
		siteScreenshotSmall = new SiteScreenshot(imgScreeshotSmall);
		this.screenshotFullDetails = screenshotFullDetails;

	}
	
	public void showInfo() {
		siteScreenshotSmall.loadImage(pageInfo.getImageSmall());
		siteScreenshotSmall.showImage();
		fetchTextDetails.setSiteTitle(pageInfo.getTitle());
		fetchTextDetails.setSiteUrl(pageInfo.getUrl());
		fetchTextDetails.setSiteName(pageInfo.getRegisteredSite().getSiteConfig().getName());
		fetchTextDetails.setTime(TimeText.secondsToText(pageInfo.getTime()));
		fetchTextDetails.setTotalItems(String.valueOf(pageInfo.getTotalItems()));
		
	}
	public void zoomIn() {
		screenshotFullDetails.showDetails(pageInfo);

	}
	public void zoomOut() {
		screenshotFullDetails.hideDetails();

	}	


	
	public ImageView getImgScreeshotSmall() {
		return imgScreeshotSmall;
	}
	public SiteScreenshot getSiteScreenshotFull() {
		return siteScreenshotFull;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
}
