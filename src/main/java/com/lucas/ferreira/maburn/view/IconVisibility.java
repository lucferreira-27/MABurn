package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.util.Icon;

import javafx.scene.image.ImageView;

public class IconVisibility {
	public void hideIcon(Icon icon) {
		icon.setVisible(false);
	}
	public void hideIcon(LabelIcon icon) {
		icon.setVisible(false);
	}
	public void hideIcon(ImageView icon) {
		icon.setVisible(false);
	}
	public void showIcon(Icon icon) {
		icon.setVisible(true);
	}
	public void showIcon(ImageView icon) {
		icon.setVisible(true);
	}
	public void showIcon(LabelIcon icon) {
		icon.setVisible(true);
	}
	public void disableIcon(Icon icon) {
		
	}
	public void disableIcon(ImageView imageIcon) {
		Icon icon = (Icon) imageIcon.getUserData();
		icon.disableIcon();
	}
	public void enableIcon(ImageView imageIcon) {
		Icon icon = (Icon) imageIcon.getUserData();
		icon.enableIcon();	
	}
}
