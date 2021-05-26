package com.lucas.ferreira.maburn.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SiteScreenshot {

	private ImageView imageView;

	public SiteScreenshot(ImageView imageView) {
		this.imageView = imageView;
	}

	public void loadImage(Image newImage) {

		imageView.setImage(newImage);
	}

	public void showImage() {
		
		
		
		
		if (!imageView.isVisible())
			imageView.setVisible(true);
		
	}

	public void hideImage() {
		imageView.setVisible(false);

	}

	public ImageView getImageView() {
		return imageView;
	}

}
