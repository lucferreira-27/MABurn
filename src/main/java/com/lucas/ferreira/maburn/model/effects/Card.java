package com.lucas.ferreira.maburn.model.effects;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Card {

	private BooleanProperty isShowing = new SimpleBooleanProperty(false);
	private Label lblTitle = new Label();
	private Label lblNumberOfSubItems = new Label();;
	private Label lblSizeInDisk = new Label();
	private Label lblStatus = new Label();
	private ProgressIndicator pi = new ProgressIndicator(-1);
	private Pane pane;

	private GridPaneCell cell;
	private ImageView image;

	public Card(GridPaneCell cell) {
		// TODO Auto-generated constructor stub
		this.cell = cell;
		pane = (Pane) cell.getNode();
		image = (ImageView) pane.getChildren().get(0);

		isShowing.addListener((obs, oldvalue, newvalue) -> {
			if (!newvalue) {
				pi.setVisible(false);
			}
		});

	}

	public void overlay() {
		// lblTitle.setStyle("-fx-font-family: Passion One, Times;");
		CollectionItem item = ((CollectionItem) cell.getUserData());
		String title = ((CollectionItem) cell.getUserData()).getTitleDataBase();

		Animations animation = new Animations((Pane) cell.getNode());

		Rectangle overlay = animation.overlay(image);

		overlay.heightProperty().addListener((obs, oldvalue, newvalue) -> {
			if (item.getDestination() != null)
				if (newvalue.doubleValue() >= 239) {
					isShowing.set(true);
					loadNumberOfItemsLabel();
					loadSizeInDiskLabel(item);
				} else if (isShowing.get()) {
					isShowing.set(false);
					unloadNumberOfItemsLabel();
					unloadSizeInDiskLabel();

				}
		});

		pane.getChildren().add(lblTitle);
		pane.getChildren().add(lblNumberOfSubItems);
		pane.getChildren().add(lblSizeInDisk);
		pane.getChildren().add(pi);
		pi.setVisible(false);
		lblTitle.setCenterShape(true);
		lblTitle.getStyleClass().add("image-panel-title");

		lblTitle.setLayoutY(8);
		lblTitle.setLayoutX(8);

		lblTitle.setMaxWidth(150);
		lblTitle.setText(title);

		lblNumberOfSubItems.setVisible(false);
		lblSizeInDisk.setText("Disk: ");
		lblSizeInDisk.setVisible(false);

		lblSizeInDisk.setLayoutY(200);
		lblNumberOfSubItems.setLayoutY(180);

		lblNumberOfSubItems.setLayoutX(8);
		lblSizeInDisk.setLayoutX(8);

		pi.setMinWidth(2);
		pi.setMinHeight(2);
//		
		pi.setLayoutX(lblSizeInDisk.getLayoutX() + 90);
		pi.setLayoutY(lblSizeInDisk.getLayoutY() - 25);

		if (item.getCategory() == Category.ANIME)
			lblNumberOfSubItems.setText("Episodes: " + item.getListSubItens().size());
		else
			lblNumberOfSubItems.setText("Chapters: " + item.getListSubItens().size());

	}

	private void unloadSizeInDiskLabel() {

		lblSizeInDisk.setVisible(false);

	}

	private void loadSizeInDiskLabel(CollectionItem item) {
		lblSizeInDisk.setVisible(true);

		String disk = "Disk: ";
		if (lblSizeInDisk.getText().equals(disk))
			new Thread(() -> {
				Platform.runLater(() -> {

					pi.setVisible(true);

				});
				long longSize = FileUtils.sizeOfDirectory(new File(item.getDestination()));
				String strSize = DataStorageUtil.converterUnit(BytesUtil.convertBytesToMegasBytes(longSize));
				Platform.runLater(() -> {
					if (isShowing.get()) {
						lblSizeInDisk.setVisible(true);
						lblSizeInDisk.setText("Disk: " + strSize);
					}
					pi.setVisible(false);

				});
			}).start();

	}

	private void loadNumberOfItemsLabel() {

		lblNumberOfSubItems.setVisible(true);

	}

	private void unloadNumberOfItemsLabel() {

		lblNumberOfSubItems.setVisible(false);

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

	public Label getLblNumberOfSubItems() {
		return lblNumberOfSubItems;
	}

	public void setLblNumberOfSubItems(Label lblNumberOfSubItems) {
		this.lblNumberOfSubItems = lblNumberOfSubItems;
	}

	public Label getLblSizeInDisk() {
		return lblSizeInDisk;
	}

	public void setLblSizeInDisk(Label lblSizeInDisk) {
		this.lblSizeInDisk = lblSizeInDisk;
	}

	public Label getLblStatus() {
		return lblStatus;
	}

	public void setLblStatus(Label lblStatus) {
		this.lblStatus = lblStatus;
	}

	public void setCell(GridPaneCell cell) {
		this.cell = cell;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

}
