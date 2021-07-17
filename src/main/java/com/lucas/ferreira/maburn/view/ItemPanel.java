package com.lucas.ferreira.maburn.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.collections.management.CollectionStatus;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.util.Resources;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class ItemPanel implements Initializable {
	public static final double WIDTH = 168.75;
	public static final double HEIGT = 237.0;

	@FXML
	private AnchorPane apPanel;

	@FXML
	private ImageView imgImagePanel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		Rectangle form = new Rectangle(imgImagePanel.getFitWidth() - 1, imgImagePanel.getFitHeight() - 1);
		form.setArcHeight(20);
		form.setArcWidth(20);
		imgImagePanel.setClip(form);
		imgImagePanel.setImage(new Image(Resources.getResourceAsStream("images/86.jpg")));

	};

}
