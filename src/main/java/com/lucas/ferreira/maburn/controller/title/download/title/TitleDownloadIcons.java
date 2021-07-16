package com.lucas.ferreira.maburn.controller.title.download.title;

import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

public class TitleDownloadIcons  implements Initialize{
	private String ICON_PATH = "icons/";
	private TitleDownload titleDownload;
	private TitleDownloadController titleDownloadController;
	private Icon iconFetch;
	private Icon iconRecover;
	private Icon iconManualSearch;
	private Icon iconDownloadStart;
	private Icon iconSource;
	private Icon iconChoose;
	private Icon iconZoomIn;
	private Icon iconZoomOut;

	public TitleDownloadIcons(TitleDownload titleDownload, TitleDownloadController titleDownloadController) {
		this.titleDownload = titleDownload;
		this.titleDownloadController = titleDownloadController;
	}

	public void initialize() {

		iconFetch = new Icon(titleDownload.getImgFetch(), new IconConfig(ICON_PATH, Icons.FETCH));
		iconRecover = new Icon(titleDownload.getImgRecover(), new IconConfig(ICON_PATH, Icons.RECOVER));
		iconManualSearch = new Icon(titleDownload.getImgManualSearch(), new IconConfig(ICON_PATH, Icons.MANUAL_SEARCH));
		iconDownloadStart = new Icon(titleDownload.getImgDownloadStart(),
				new IconConfig(ICON_PATH, Icons.DOWNLOAD_START));
		iconSource = new Icon(titleDownload.getImgSource(), new IconConfig(ICON_PATH, Icons.SOURCE));
		iconChoose = new Icon(titleDownload.getImgChoose(), new IconConfig(ICON_PATH, Icons.CHOOSE));
		iconZoomIn = new Icon(titleDownload.getImgZoomIn(), new IconConfig(ICON_PATH, Icons.ZOOM_IN_BIG));
		iconZoomOut = new Icon(titleDownload.getImgZoomOut(), new IconConfig(ICON_PATH, Icons.ZOOM_OUT_BIG));

		iconFetch.setProperties((event) -> titleDownloadController.onClickFetch());
		iconRecover.setProperties((event) -> titleDownloadController.onClickRecover());
		iconManualSearch.setProperties((event) -> titleDownloadController.onClickManualSearch());
		iconDownloadStart.setProperties((event) -> titleDownloadController.onClickDownloadStart());
		iconSource.setProperties(null);
		iconChoose.setProperties(null);
		iconZoomIn.setProperties((event) -> titleDownloadController.onClickZoomIn());
		iconZoomOut.setProperties((event) -> titleDownloadController.onClickZoomOut());

	}

	public TitleDownload getTitleDownload() {
		return titleDownload;
	}


	public TitleDownloadController getTitleDownloadController() {
		return titleDownloadController;
	}

	public void setTitleDownloadController(TitleDownloadController titleDownloadController) {
		this.titleDownloadController = titleDownloadController;
	}

	public Icon getIconFetch() {
		return iconFetch;
	}

	public void setIconFetch(Icon iconFetch) {
		this.iconFetch = iconFetch;
	}

	public Icon getIconRecover() {
		return iconRecover;
	}

	public void setIconRecover(Icon iconRecover) {
		this.iconRecover = iconRecover;
	}

	public Icon getIconManualSearch() {
		return iconManualSearch;
	}

	public void setIconManualSearch(Icon iconManualSearch) {
		this.iconManualSearch = iconManualSearch;
	}

	public Icon getIconDownloadStart() {
		return iconDownloadStart;
	}

	public void setIconDownloadStart(Icon iconDownloadStart) {
		this.iconDownloadStart = iconDownloadStart;
	}

	public Icon getIconSource() {
		return iconSource;
	}

	public void setIconSource(Icon iconSource) {
		this.iconSource = iconSource;
	}

	public Icon getIconChoose() {
		return iconChoose;
	}

	public void setIconChoose(Icon iconChoose) {
		this.iconChoose = iconChoose;
	}

	public Icon getIconZoomIn() {
		return iconZoomIn;
	}

	public void setIconZoomIn(Icon iconZoomIn) {
		this.iconZoomIn = iconZoomIn;
	}

	public Icon getIconZoomOut() {
		return iconZoomOut;
	}

	public void setIconZoomOut(Icon iconZoomOut) {
		this.iconZoomOut = iconZoomOut;
	}
	
	
}
