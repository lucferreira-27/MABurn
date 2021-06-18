package com.lucas.ferreira.maburn.util;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Icon {

	private IconConfig config;
	private ImageView icon;

	public Icon(ImageView icon, IconConfig config) {
		// TODO Auto-generated constructor stub
		this.config = config;
		this.icon = icon;
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
		}catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void properties() {
		if (config.getIconTip() != null)
			setToolTip(config.getIconTip());

		icon.setImage(new Image(Resources.getResourceAsStream(config.getIconsPath() + config.getIcon().getIconName())));
		if (config.getIcon().getAlterIconName() != null)
			onHoverIcon();
	}

	private void onHoverIcon() {

		String normal = config.getIconsPath() + config.getIcon().getAlterIconName();
		String alter = config.getIconsPath() + config.getIcon().getIconName();

		icon.hoverProperty().addListener((event) -> {

			if (icon.isHover())
				icon.setImage(new Image(Resources.getResourceAsStream(normal)));
			else
				icon.setImage(new Image(Resources.getResourceAsStream(alter)));

		});
	}

	public void setToolTip(String tip) {
		Tooltip tooltip = new Tooltip(tip);
		tooltip.setShowDelay(Duration.seconds(0.5));
		Tooltip.install(icon, tooltip);
	}
}
