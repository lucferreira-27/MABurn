package com.lucas.ferreira.maburn.util;

import com.lucas.ferreira.maburn.model.enums.Icons;

public class IconConfig {
	private Icons icon;
	private String iconsPath;
	private String iconTip;
	
	public IconConfig(String iconsPath, Icons icon, String iconTip){
		this.icon = icon;
		this.iconsPath = iconsPath;
		this.iconTip = iconTip;
	}
	
	public IconConfig(String iconsPath, Icons icon){
		this.icon = icon;
		this.iconsPath = iconsPath;
	}

	public String getIconsPath() {
		return iconsPath;
	}
	
	public Icons getIcon() {
		return icon;
	}
	
	public String getIconTip() {
		return iconTip;
	}

	
	
}
