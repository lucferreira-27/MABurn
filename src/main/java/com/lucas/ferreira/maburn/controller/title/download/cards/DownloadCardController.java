package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.effects.AnimationCard;
import com.lucas.ferreira.maburn.model.effects.Animations;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class DownloadCardController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private BorderPane bpDetails;

	@FXML
	private Label lblItemTitle;

	@FXML
	private Label lblDownloadLink;

	@FXML
	private Label lblTotalSize;

	@FXML
	private Label lblDownloadSpeed;

	@FXML
	private Label lblTimeRemain;

	@FXML
	private Label lblPorcentageConcluded;

	@FXML
	private Label lblDownloaded;

	@FXML
	private ProgressBar pbDownloadProgress;

	@FXML
	private ImageView imgDownloadIcon;
	@FXML
	private ImageView imgPlayerIcon;
	@FXML
	private ImageView imgStopIcon;
	private FetchCardValues fetchCardValues;
	private String ICON_PATH = "icons/";

	private ItemDownloadValues downloadValues;

	public DownloadCardController(ItemDownloadValues downloadValues) {
		this.downloadValues = downloadValues;
	}

	public void initializeValuesCard() {
		
		pbDownloadProgress.progressProperty().bind(downloadValues.getDownloadProgress());
	
		lblDownloadLink.setText(downloadValues.getDirectLink());

		downloadValues.getDownloadSize().addListener((obs, oldvalue, newvalue) ->{
			lblTotalSize.setText(Double.toString(newvalue.doubleValue()));

		});
		downloadValues.getDownloadSpeed().addListener((obs, oldvalue, newvalue) ->{
			lblDownloadSpeed.setText(Double.toString(newvalue.doubleValue()));

		});
		lblItemTitle.setText(downloadValues.getName());
		

	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AnimationCard animations = new AnimationCard(bpDetails);


		animations.onPlayAnimation(pane -> {
			pane.setVisible(true);
		});

		animations.onFinishAnimation(pane -> {
			pane.setVisible(false);
		});

		root.hoverProperty().addListener((obs, oldvalue, newvalue) -> {
			if (root.isHover()) {
				animations.showCardDetails(180, 0.0005);
			} else {
				animations.hideCardDetails(115, 0.0005);
			}
		});
	}

}
