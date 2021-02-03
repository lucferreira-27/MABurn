package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

public class DownloadInQueueController implements Initializable {
	private TitleDownload item;

	@FXML
	private Label lblTitleName;

	@FXML
	private Label lblTotal;

	@FXML
	private Label lblErros;

	@FXML
	private Label lblDownloaded;
	@FXML
	private Label lblRemain;

	@FXML
	private Button btnOpen;

	@FXML
	private Button btnRemove;

	@FXML
	private Button btnPause;

	@FXML
	private ImageView imgTitle;

	@FXML
	private ProgressBar pbProgress;

	@FXML
	private BorderPane root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		item = (TitleDownload) root.getUserData();

		lblTitleName.setText(item.getCollectionItem().getTitleDataBase());

		lblTotal.setText("Total: " + item.getTotalDownlods().intValue());
		lblErros.setText("Erros: " + item.getFailedDownlods().intValue());
		// lblDownloaded.setText("Downloaded: " +
		// item.getConcludedDownlods().intValue());

		item.getFailedDownlods().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				lblErros.setText("Erros: " + newvalue.intValue());
			});
		});
		item.getConcludedDownlods().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				lblDownloaded.setText("Downloaded: " + newvalue.intValue());
			});
		});
		item.getTotalDownlods().addListener((obs, oldvalue, newvalue) -> {
			Platform.runLater(() -> {
				lblTotal.setText("Total: " + newvalue.intValue());
			});
		});

		btnOpen.setDisable(true);
		btnRemove.setDisable(true);

		btnOpen.setOnAction(event -> onClickOnButtonOpen());
		btnPause.setOnAction(event -> onClickOnButtonPause());
		btnRemove.setOnAction(event -> onClickOnButtonRemove());

		lblRemain.textProperty().bindBidirectional(item.getRemain());

		pbProgress.progressProperty().bind(item.getTotalProgressPropery());

		try {

			imgTitle.setImage(item.getCollectionItem().getImage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@FXML
	private void onClickOnButtonOpen() {
//		TitleInterfaceView titleView = new TitleInterfaceView(this);
//		titleView.loadMainInterfaceFX();
	}

	@FXML
	private void onClickOnButtonRemove() {
//		TitleInterfaceView titleView = new TitleInterfaceView(this);
//		titleView.loadMainInterfaceFX();
	}

	@FXML
	private void onClickOnButtonPause() {
//		TitleInterfaceView titleView = new TitleInterfaceView(this);
//		titleView.loadMainInterfaceFX();
		if (item.getState().get() != DownloadState.FINISH && item.getState().get() != DownloadState.FAILED) {

			if (item.isPausing()) {
				CustomLogger.log("Wait all items pause first!");
				return;
			}

			if (!item.getPauseProperty().get()) {
				btnPause.setText("RESUME");
				item.pause();

			} else {

				btnPause.setText("PAUSE");
				item.resume();

			}
			return;
		}
		CustomLogger.log("Download is done");

	}

}
