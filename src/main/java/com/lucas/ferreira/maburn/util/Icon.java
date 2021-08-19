package com.lucas.ferreira.maburn.util;

import java.util.function.Consumer;

import com.lucas.ferreira.maburn.model.enums.Icons;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icon {

	private IconConfig config;
	private ImageView icon;
	private String primaryColor;
	private String secondaryColor;
	private BooleanProperty primaryColorOn = new SimpleBooleanProperty(true);
	private boolean enable = true;
	private ChangeListener<? super Boolean> onHover;

	public Icon(ImageView icon, IconConfig config) {
		this.config = config;
		this.icon = icon;

		defineIconsColors();
		properties();
	}

	private void defineIconsColors() {
		primaryColor = config.getIconsPath() + config.getIcon().getIconName();
		secondaryColor = config.getIconsPath() + config.getIcon().getAlterIconName();
	}

	private void setAction(Consumer<ImageView> onClick) {

		icon.setOnMouseClicked(event -> {
			if (enable)
				onClick.accept(icon);
		});

	}

	public void setProperties(Consumer<ImageView> onClick) {

		if (onClick != null)
			setAction(onClick);

		properties();
	}

	public void swithIconColor() {
		if (primaryColorOn.get()) {
			changeToSecondaryColorIcon();
		} else {
			changeToPrimaryColorIcon();
		}
	}

	private void changeToPrimaryColorIcon() {
		try {
			Image image = loadImage(primaryColor, config.getIcon().getImageType());
			icon.setImage(image);
			primaryColorOn.set(true);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void changeToSecondaryColorIcon() {
		try {
			Image image = loadImage(secondaryColor, config.getIcon().getImageType());
			icon.setImage(image);
			primaryColorOn.set(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Image loadImage(String path, IconImage.ImageType imageType) throws Exception {
		return IconImage.parseImage(Resources.getResourceAsStream(path), imageType);
	}


	private void properties() {
		icon.setUserData(this);
		try {
			if (config.getIconTip() != null)
				setToolTip(config.getIconTip());
			Image image = loadImage(config.getIconsPath() + config.getIcon().getIconName(),
					config.getIcon().getImageType());
			icon.setImage(image);
			if (config.getIcon().getAlterIconName() != null)
				onHoverIcon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onHoverIcon() {
		onHover = new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (icon.isHover())
					changeToSecondaryColorIcon();
				else
					changeToPrimaryColorIcon();
			}

		};

		icon.hoverProperty().addListener(onHover);
	}

	public void setToolTip(String tip) {
		Tooltip tooltip = new Tooltip(tip);
		// Java 9 or higher
		// tooltip.setShowDelay(Duration.seconds(0.5));
		Tooltip.install(icon, tooltip);
	}

	public void setVisible(boolean visible) {
		icon.setVisible(visible);
	}

	public void enableIcon() {
		enable = true;
	}

	public void disableIcon() {
		enable = false;
	}

	public boolean isVisible() {
		return icon.isVisible();
	}

	public void reloadIcon(Icons icon) {
		config.setIcon(icon);
		properties();
	}

	public ImageView getIconImageView() {
		return icon;
	}

	public void removeListenerOnHover() {
		if (onHover != null)
			icon.hoverProperty().removeListener(onHover);
	}
}
