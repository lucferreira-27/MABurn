package com.lucas.ferreira.maburn.model.enums;

public enum Icons {
	DOWNLOAD_1("",""),
	UPDATE_1("",""),
	HOME_1("",""),
	CALENDAR_1("",""),
	BACK("",""),
	NEXT("",""),
	READ("",""),
	WATCH("",""),
	
	
	
	STATUS("title/status_icon.png",null),
	PUBLISHED("title/published_icon.png",null),
	RATING("title/rating_icon.png",null),
	
	DOWNLOAD_2("title/download_white_icon.png", "title/download_red_icon.png"),
	UPDATE_2("title/update_white_icon.png", "title/update_red_icon.png"),
	HIDE("title/hide_white_icon.png", "title/hide_red_icon.png"),
	REMOVE("title/remove_white_icon.png", "title/remove_red_icon.png"),
	
	FETCH("title-download/fetch_white_icon.png","title-download/fetch_red_icon.png"),
	RECOVER("title-download/recover_white_icon.png","title-download/recover_red_icon.png"),
	MANUAL_SEARCH("title-download/input_white_icon.png","title-download/input_red_icon.png"),
	
	MANUAL_ALERT_SEARCH("title-download/search_white_icon.png","title-download/search_red_icon.png"),
	MANUAL_ALERT_UNDO("title-download/undo_white_icon.png","title-download/undo_red_icon.png"),
	MANUAL_ALERT_CLOSE("title-download/close_white_icon.png","title-download/close_red_icon.png"),
	ZOOM_OUT_SMALL("title-download/zoom_out_white_small_icon.png","title-download/zoom_out_red_small_icon.png"),
	ZOOM_IN_SMALL("title-download/zoom_in_white_small_icon.png","title-download/zoom_in_red_small_icon.png"),

	ZOOM_OUT_BIG("title-download/zoom_out_white_big_icon.png","title-download/zoom_out_red_big_icon.png"),
	ZOOM_IN_BIG("title-download/zoom_in_white_big_icon.png","title-download/zoom_in_red_big_icon.png"),
	
	SOURCE("title-download/source_white_icon.png",null),
	CHOOSE("title-download/choose_white_icon.png",null),
	DOWNLOAD_START("title-download/download_white_icon.png","title-download/download_red_icon.png");


	
	
	
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
