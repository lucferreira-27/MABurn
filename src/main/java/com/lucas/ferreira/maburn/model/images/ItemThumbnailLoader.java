package com.lucas.ferreira.maburn.model.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.ImageLoaderModel;
import com.lucas.ferreira.maburn.model.effects.TransformEffects;
import com.lucas.ferreira.maburn.model.effects.TransformPanelEffect;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ItemThumbnailLoader {
	private GridPaneCell cell;
	private ImageView imageView;
	private CollectionItem item;

	public ItemThumbnailLoader(CollectionItem item) {
		// TODO Auto-generated constructor stub
		this.item = item;

	}

	public File findImage() throws ThumbnailLoadException {

		// File image = new File(item.getImageLocal());

		File image = new File(item.getImageLocal());
		if (!image.exists()) {
			throw new ThumbnailLoadException("Image don't found in " + item.getImageLocal());
		}
		return image;
	}

	public GridPaneCell downloadLoad() throws ThumbnailLoadException {
		try {
			File file = findImage();
			InputStream in = new FileInputStream(file);
			Image image = new Image(in);
			item.setImage(image);
			addImageViewInImageGrid();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ThumbnailLoadException(e.getMessage());
		}
		return cell;
	}

	public GridPaneCell onlineLoad() throws ThumbnailLoadException {
		try {
			ImageLoaderModel loader = new ImageLoaderModel();
			Image image = loader.loadImageByUrl(item.getImageUrl());
			item.setImage(image);

			addImageViewInImageGrid();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ThumbnailLoadException(e.getMessage());
		}
		return cell;
	}

	public GridPaneCell addImageViewInImageGrid() throws IllegalAccessException {

		imageView = new ImageView(item.getImage());
		imageView.setFitWidth(168.75);
		imageView.setFitHeight(237.0);
		imageView.setUserData(item);
		imageView.setCache(true);
		imageView.setCacheHint(CacheHint.SPEED);

		
		AnchorPane imageAreaPanel = new AnchorPane(imageView);

		
		
		imageAreaPanel.setCache(true);
		imageAreaPanel.setCacheHint(CacheHint.SPEED);

		imageAreaPanel.getStyleClass().add("item-image");

		cell = new GridPaneCell(imageAreaPanel);
		imageView.setLayoutX(3);
		imageView.setLayoutY(3);
		return cell;

	}


	private Pane createPaneEffect(Pane pane) {
		TransformPanelEffect transform = new TransformPanelEffect();
		pane = transform.addEffect(pane, TransformEffects.BORDER_IMAGE);
		return pane;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public GridPaneCell getCell() {
		return cell;
	}

}
