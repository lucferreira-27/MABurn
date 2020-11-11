package com.lucas.ferreira.maburn.model.effects;

import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TransformImagesViewEffect {

	private ImageView image;

	public ImageView addEffect(ImageView image, TransformEffects effect) {
		switch (effect) {
		case BORDER_IMAGE:
			this.image = borderEffect(image);
			break;
		case BRIGHTNESS:
			this.image = brightnessEffect(image);

			break;
		default:
			break;
		}
		return image;
	}

	private ImageView brightnessEffect(ImageView image) {

		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-0.5);

		image.setEffect(colorAdjust);

		image.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

			image.setEffect(null);

		});

		image.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			image.setEffect(colorAdjust);
		});
		return image;
	}

	private ImageView borderEffect(ImageView image) {
		image.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			
			image.getStyleClass().add("item-image");

		});

		image.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {

			image.getStyleClass().remove(image.getStyleClass().size() - 1);
		});
		return image;
	}

}
