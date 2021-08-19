package com.lucas.ferreira.maburn.model.enums;

import com.lucas.ferreira.maburn.util.IconImage;

public enum Icons {

	STATUS("title/status_icon.png", null), PUBLISHED("title/published_icon.png", null),
	RATING("title/rating_icon.png", null),

	DOWNLOAD_2("title/download_white_icon.png", "title/download_red_icon.png"),
	UPDATE_2("title/update_white_icon.png", "title/update_red_icon.png"),
	HIDE("title/hide_white_icon.png", "title/hide_red_icon.png"),
	REMOVE("title/remove_white_icon.png", "title/remove_red_icon.png"),

	// FETCH AREA ICONS
	FETCH("title-download/fetch_white_icon.svg", "title-download/fetch_red_icon.svg"),
	RECOVER("title-download/recover_white_icon.svg", "title-download/recover_red_icon.svg"),
	MANUAL_SEARCH("title-download/input_white_icon.png", "title-download/input_red_icon.png"),
	SOURCE("title-download/source_white_icon.svg", null), CHOOSE("title-download/choose_white_icon.svg", null),
	DOWNLOAD_START("title-download/download_white_icon.svg", "title-download/download_red_icon.svg"),

	// MANUAL SEARCH AREA ICONS
	MANUAL_ALERT_SEARCH("title-download/search_white_icon.png", "title-download/search_red_icon.png"),
	MANUAL_ALERT_UNDO("title-download/undo_white_icon.png", "title-download/undo_red_icon.png"),
	MANUAL_ALERT_CLOSE("title-download/close_white_icon.png", "title-download/close_red_icon.png"),

	// SITE IMAGE AREA ICONS
	ZOOM_OUT_SMALL("title-download/zoom_out_white_small_icon.svg", "title-download/zoom_out_red_small_icon.svg"),
	ZOOM_IN_SMALL("title-download/zoom_in_white_small_icon.svg", "title-download/zoom_in_red_small_icon.svg"),
	ZOOM_OUT_BIG("title-download/zoom_out_white_big_icon.svg", "title-download/zoom_out_red_big_icon.svg"),
	ZOOM_IN_BIG("title-download/zoom_in_white_big_icon.svg", "title-download/zoom_in_red_big_icon.svg"),

	// DOWNLOAD QUEUE
	COMPLETED_ICON("download-queue/completed_white_icon.png", "download-queue/completed_red_icon.png"),
	DOWNLOADING_ICON("download-queue/downloading_white_icon.png", "download-queue/downloading_red_icon).png"),
	ERROR_ICON("download-queue/error_white_icon.png", "download-queue/error_red_icon.png"),
	LAUNCH_ICON("download-queue/launch_white_icon.png", "download-queue/launch_red_icon.png"),

//	FOLDER_ICON("",""),
//	PLAY_ICON("",""),
//	STOP_ICON("",""),

	// BROWSER INSTALL

	BROWSER_INSTALL_ICON("browser-install/browser_install_white_icon.png", null),
	CLOSE("browser-install/close_white_icon.png", "browser-install/close_red_icon.png"),

	// SETTINGS

	BROWSER_INSTALL_ICON_SETTINGS("settings/browser_install_white_icon.png", null),
	BROWSER_CHECK_BAD("settings/installed_bad_red_icon.png", null),
	BROWSER_CHECK_OK("settings/installed_ok_green_icon.png", null),

	// DOWNLOAD CARDS ICONS
	LABEL_ICON("title-download/label_white_icon.svg", "title-download/label_red_icon.svg"),
	TAB_ICON("title-download/tab_white_icon.svg", "title-download/tab_red_icon.svg"),
	FORM_LIST_ICON("title-download/form_list_white_icon.svg", "title-download/form_list_red_icon.svg"),
	TIME_ICON("title-download/time_white_icon.svg", "title-download/time_red_icon.svg"),
	LINK("title-download/link_white_icon.svg", "title-download/link_red_icon.svg"),
	FETCH_IN_CARD("title-download/fetch_small_icon.png", null),
	DOWNLOAD_IN_CARD("title-download/download_card_icon.png", null),
	OPEN_PAGES("title-download/open_pages_white_icon.png", "title-download/open_pages_red_icon.png"),
	PLAY_IN_CARD("title-download/play_white_icon.png", "title-download/play_red_icon.png"),
	PAUSE_IN_CARD("title-download/pause_white_icon.png", "title-download/pause_red_icon.png"),
	STOP_IN_CARD("title-download/stop_white_icon.png", "title-download/stop_red_icon.png"),
	OPEN_FOLDER_ICON("title-download/open_folder_white_icon.png", "title-download/open_folder_red_icon.png"),
	WATCH_ICON("title-download/watch_white_icon.png", "title-download/watch_red_icon.png"),
	READ_ICON("title-download/read_white_icon.png", "title-download/read_red_icon.png"),
	CLOSE_ICON("title-download/close_white_icon.png", "title-download/close_red_icon.png"),
	REFRESH_ICON("title-download/refresh_white_icon.png", "title-download/refresh_red_icon.png"),
	DELETE_ICON("title-download/delete_white_icon.png", "title-download/delete_red_icon.png");

	private String iconName;
	private String alterIconName;
	private IconImage.ImageType imageType;

	private Icons(String iconName, String alterIconName) {
		this.iconName = iconName;
		this.alterIconName = alterIconName;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getAlterIconName() {
		return alterIconName;
	}

	public void setAlterIconName(String alterIconName) {
		this.alterIconName = alterIconName;
	}

	public IconImage.ImageType getImageType() {
		if (iconName == null) {
			return null;
		}
		
		String type = iconName.substring(iconName.lastIndexOf("."));
	
		if (type.equals(".svg")) {
			return IconImage.ImageType.SVG;
		} else if (type.equals(".jpg") || type.equals(".png")) {
			return IconImage.ImageType.PNG;
		} else {
			return null;
		}

	}

}
