package com.lucas.ferreira.maburn.model.effects;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.items.CollectionItem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class SearchCard implements Card {
	private BooleanProperty isShowing = new SimpleBooleanProperty(false);
	private Label lblTitle = new Label();
	private Label lblScore = new Label();
	private Label lblDate = new Label();

	private Pane pane;

	private GridPaneCell cell;
	private ImageView image;

	public SearchCard(GridPaneCell cell) {
		// TODO Auto-generated constructor stub
		this.cell = cell;
		pane = (Pane) cell.getNode();
		image = (ImageView) pane.getChildren().get(0);


	}

	@Override
	public void overlay() {
		CollectionItem item = ((CollectionItem) cell.getUserData());
		String title = item.getTitleDataBase();
		String score = item.getScore() == null ? "0.00" : item.getScore().toString();
		String date = item.getDate();
		Animations animation = new Animations((Pane) cell.getNode());

		Rectangle overlay = animation.overlay(image);

		overlay.heightProperty().addListener((obs, oldvalue, newvalue) -> {
			if (newvalue.doubleValue() >= 239) {
				isShowing.set(true);
				loadScoreLabel();
				loadDateLabel();
			}else if (isShowing.get()) {
				isShowing.set(false);
				unloadDateLabel();
				unloadScoreLabel();

			}
		});

		pane.getChildren().add(lblTitle);
		pane.getChildren().add(lblScore);
		pane.getChildren().add(lblDate);
		
		
		lblTitle.setCenterShape(true);
		lblTitle.getStyleClass().add("image-panel-title");

		lblTitle.setLayoutY(8);
		lblTitle.setLayoutX(8);
		lblTitle.setMaxWidth(150);
		lblTitle.setText(title);

		lblScore.setLayoutY(200);
		lblScore.setLayoutX(8);
		lblScore.setText("Score: " + score);
		lblScore.setVisible(false);

		lblDate.setLayoutY(180);
		lblDate.setLayoutX(8);
		lblDate.setText("Date: " + date);
		lblDate.setVisible(false);


	}

	private void loadDateLabel() {
		lblDate.setVisible(true);
	}

	private void loadScoreLabel() {
		lblScore.setVisible(true);

	}
	private void unloadDateLabel() {
		lblDate.setVisible(false);
	}

	private void unloadScoreLabel() {
		lblScore.setVisible(false);

	}

	public GridPaneCell getCell() {
		return cell;
	}

	public ImageView getImage() {
		return image;
	}

	public Label getLblTitle() {
		return lblTitle;
	}

	public void setLblTitle(Label lblTitle) {
		this.lblTitle = lblTitle;
	}

	public void setCell(GridPaneCell cell) {
		this.cell = cell;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
}
