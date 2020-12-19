package com.lucas.ferreira.maburn.model.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import com.lucas.ferreira.maburn.exceptions.ThumbnailLoadException;
import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.effects.TransformEffects;
import com.lucas.ferreira.maburn.model.effects.TransformImagesViewEffect;
import com.lucas.ferreira.maburn.model.effects.TransformPanelEffect;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ItemThumbnailLoader implements Callable<GridPaneCell> {
	private GridPaneCell cell;
	private ImageView imageView;
	private CollectionItem item;


	public ItemThumbnailLoader(CollectionItem item) {
		// TODO Auto-generated constructor stub
		this.item = item;

	}

	public File findImage() throws ThumbnailLoadException {
		
		//File image = new File(item.getImageLocal());
		System.out.println("> Tittle: " + item.getTitleDataBase());
		System.out.println("> Image Local: " + item.getImageLocal());
		File image = new File(item.getImageLocal());
		if (!image.exists()) {
			throw new ThumbnailLoadException("Image don't found in " + item.getImageLocal());
		}
		return image;
	}

	@Override
	public GridPaneCell call() throws Exception {
		// TODO Auto-generated method stub
		File file = findImage();
		InputStream in = new FileInputStream(file);
		Image image = new Image(in);
		imageView = new ImageView(image);
		addImageViewInImageGrid();

		return cell;
	}

	public GridPaneCell addImageViewInImageGrid() throws IllegalAccessException {

		imageView.setFitWidth(168.75);
		imageView.setFitHeight(237.0);
		imageView.setUserData(item);

		imageView = createImageEffect(imageView, TransformEffects.BORDER_IMAGE);

		AnchorPane imageAreaPanel = new AnchorPane(imageView);
		imageAreaPanel = (AnchorPane) createPaneEffect(imageAreaPanel);

		imageAreaPanel.getStyleClass().add("item-image");

		cell = new GridPaneCell(imageAreaPanel);
		// gridTable.add(cell);

		imageView.setLayoutX(3);
		imageView.setLayoutY(3);
		return cell;

	}

	private ImageView createImageEffect(ImageView imageView, TransformEffects effect) {

		TransformImagesViewEffect transformEffect = new TransformImagesViewEffect();

		imageView = transformEffect.addEffect(imageView, effect);

		return imageView;
	}

	private Label createLabelEffect(Label label) {
		label.setMaxWidth(120);
		label.getStyleClass().add("image-panel-title");
		return label;
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
