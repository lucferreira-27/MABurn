package com.lucas.ferreira.maburn.model.enums;

public enum Icons {


	
	
	STATUS("title/status_icon.png",null),
	PUBLISHED("title/published_icon.png",null),
	RATING("title/rating_icon.png",null),
	
	DOWNLOAD_2("title/download_white_icon.png", "title/download_red_icon.png"),
	UPDATE_2("title/update_white_icon.png", "title/update_red_icon.png"),
	HIDE("title/hide_white_icon.png", "title/hide_red_icon.png"),
	REMOVE("title/remove_white_icon.png", "title/remove_red_icon.png"),
	
	// FETCH AREA ICONS
	FETCH("title-download/fetch_white_icon.png","title-download/fetch_red_icon.png"),
	RECOVER("title-download/recover_white_icon.png","title-download/recover_red_icon.png"),
	MANUAL_SEARCH("title-download/input_white_icon.png","title-download/input_red_icon.png"),
	SOURCE("title-download/source_white_icon.png",null),
	CHOOSE("title-download/choose_white_icon.png",null),
	DOWNLOAD_START("title-download/download_white_icon.png","title-download/download_red_icon.png"),
	
	// MANUAL SEARCH AREA ICONS
	MANUAL_ALERT_SEARCH("title-download/search_white_icon.png","title-download/search_red_icon.png"),
	MANUAL_ALERT_UNDO("title-download/undo_white_icon.png","title-download/undo_red_icon.png"),
	MANUAL_ALERT_CLOSE("title-download/close_white_icon.png","title-download/close_red_icon.png"),

	
	// SITE IMAGE AREA ICONS
	ZOOM_OUT_SMALL("title-download/zoom_out_white_small_icon.png","title-download/zoom_out_red_small_icon.png"),
	ZOOM_IN_SMALL("title-download/zoom_in_white_small_icon.png","title-download/zoom_in_red_small_icon.png"),
	ZOOM_OUT_BIG("title-download/zoom_out_white_big_icon.png","title-download/zoom_out_red_big_icon.png"),
	ZOOM_IN_BIG("title-download/zoom_in_white_big_icon.png","title-download/zoom_in_red_big_icon.png"),
	
	

	// BROWSER INSTALL
	
	BROWSER_INSTALL_ICON("browser-install/browser_install_white_icon.png",null),
	CLOSE("browser-install/close_white_icon.png","browser-install/close_red_icon.png"),
	
	
	
	// SETTINGS
	
	BROWSER_INSTALL_ICON_SETTINGS("settings/browser_install_white_icon.png",null),
	BROWSER_CHECK_BAD("settings/installed_bad_red_icon.png", null),
	BROWSER_CHECK_OK("settings/installed_ok_green_icon.png", null),

	
	// DOWNLOAD CARDS ICONS	 
	LINK("title-download/link_white_icon.png","title-download/link_red_icon.png"),
	FETCH_IN_CARD("title-download/fetch_small_icon.png",null),
	DOWNLOAD_IN_CARD("title-download/download_card_icon.png",null),
	OPEN_PAGES("title-download/open_pages_white_icon.png","title-download/open_pages_red_icon.png"),
	PLAY_IN_CARD("title-download/play_white_icon.png","title-download/play_red_icon.png"),
	PAUSE_IN_CARD("title-download/pause_white_icon.png","title-download/pause_red_icon.png"),
	STOP_IN_CARD("title-download/stop_white_icon.png","title-download/stop_red_icon.png"),
	OPEN_FOLDER_ICON("title-download/open_folder_white_icon.png","title-download/open_folder_red_icon.png"),
	WATCH_ICON("title-download/watch_white_icon.png","title-download/watch_red_icon.png"),
	READ_ICON("title-download/read_white_icon.png","title-download/read_red_icon.png"),
	CLOSE_ICON("title-download/close_white_icon.png","title-download/close_red_icon.png"),
	REFRESH_ICON("title-download/refresh_white_icon.png","title-download/refresh_red_icon.png"),
	DELETE_ICON("title-download/delete_white_icon.png","title-download/delete_red_icon.png");
	
	
	private String iconName;
	private String alterIconName;
	
	
	
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
	
	
	
}
