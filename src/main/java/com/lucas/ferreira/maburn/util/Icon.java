package com.lucas.ferreira.maburn.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Icon {

	private IconConfig config;
	private ImageView icon;
	private String primaryColor;
	private String secondaryColor;
	private BooleanProperty primaryColorOn = new SimpleBooleanProperty(true);

	public Icon(ImageView icon, IconConfig config) {
		// TODO Auto-generated constructor stub
		this.config = config;
		this.icon = icon;
		defineIconsColors();
	}

	private void defineIconsColors() {
		primaryColor = config.getIconsPath() + config.getIcon().getIconName();
		secondaryColor = config.getIconsPath() + config.getIcon().getAlterIconName();
	}

	private void setAction(EventHandler<? super MouseEvent> event) {
		icon.setOnMouseClicked(event);
	}

	public void setProperties(EventHandler<? super MouseEvent> event) {

		if (event != null)
			setAction(event);

		properties();
	}

	public void setProperties() {
		try {
			properties();
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void swithIconColor() {
		if (primaryColorOn.get()) {
			changeToSecondaryColorIcon();
		} else {
			changeToPrimaryColorIcon();
		}
	}

	private void changeToPrimaryColorIcon() {
		icon.setImage(new Image(Resources.getResourceAsStream(primaryColor)));
		primaryColorOn.set(true);
	}

	private void changeToSecondaryColorIcon() {
		icon.setImage(new Image(Resources.getResourceAsStream(secondaryColor)));
		primaryColorOn.set(false);

	}

	private void properties() {
		if (config.getIconTip() != null)
			setToolTip(config.getIconTip());

		icon.setImage(new Image(Resources.getResourceAsStream(config.getIconsPath() + config.getIcon().getIconName())));
		if (config.getIcon().getAlterIconName() != null)
			onHoverIcon();
	}

	private void onHoverIcon() {

		icon.hoverProperty().addListener((event) -> {

			if (icon.isHover())
				changeToSecondaryColorIcon();
			else
				changeToPrimaryColorIcon();

		});
	}

	public void setToolTip(String tip) {
		Tooltip tooltip = new Tooltip(tip);
		tooltip.setShowDelay(Duration.seconds(0.5));
		Tooltip.install(icon, tooltip);
	}
}
