package com.lucas.ferreira.maburn.controller.title.download.cards.chapter;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ChapterCard implements DownloadCard {
	@FXML
	private StackPane root;
	@FXML
	private BorderPane borderPaneDetails;
	@FXML
	private BorderPane borderPaneMain;

	@FXML
	private Label labelItemTitle;
	@FXML
	private Label labelDownloadLink;
	@FXML
	private Label labelTotalSize;
	@FXML
	private Label labelDownloadSpeed;
	@FXML
	private Label labelTimeRemain;
	@FXML
	private Label labelPorcentageConcluded;
	@FXML
	private Label labelTotalPagesDownloaded;
	@FXML
	private Label labelDownloadedLink;
	@FXML
	private Label labelDownloadState;
	@FXML
	private Label labelCompletedDownloaded;
	@FXML
	private Label labelVideoResoution;
	@FXML
	private ProgressBar progressBarDownload;
	@FXML
	private ImageView imageViewDownloadIcon;
	@FXML
	private ImageView imageViewPlayerIcon;
	@FXML
	private ImageView imageViewStopIcon;
	@FXML
	private ImageView imageViewPauseIcon;
	@FXML
	private ImageView imageViewLinkIcon;
	@FXML
	private ImageView imageViewPages;
	@FXML
	private ImageView imageViewOpenFolderIcon;
	@FXML
	private ImageView imageViewReadIcon;
	@FXML
	private ImageView imageViewDeleteIcon;
	@FXML
	private ImageView imageViewRefreshIcon;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public StackPane getRoot() {
		return root;
	}

	@Override
	public BorderPane getBorderPaneDetails() {
		return borderPaneDetails;
	}

	@Override
	public Label getLabelItemTitle() {
		return labelItemTitle;
	}

	@Override
	public Label getLabelDownloadLink() {
		return labelDownloadedLink;
	}

	@Override
	public Label getLabelTotalSize() {
		return labelTotalSize;
	}

	@Override
	public Label getLabelDownloadSpeed() {
		return labelDownloadSpeed;
	}

	@Override
	public Label getLabelTimeRemain() {
		return labelTimeRemain;
	}

	@Override
	public Label getLabelPorcentageConcluded() {
		return labelPorcentageConcluded;
	}

	@Override
	public Label getLabelCompletedDownload() {
		return labelCompletedDownloaded;
	}

	@Override
	public Label getLabelDownloadState() {
		return labelDownloadState;
	}

	@Override
	public Label getLabelVideoResoution() {
		return labelVideoResoution;
	}

	@Override
	public ProgressBar getProgressBarDownload() {
		return progressBarDownload;
	}

	@Override
	public ImageView getImageViewDownloadIcon() {
		return imageViewDownloadIcon;
	}

	@Override
	public ImageView getImageViewPlayerIcon() {
		return imageViewPlayerIcon;
	}

	@Override
	public ImageView getImageViewStopIcon() {
		return imageViewStopIcon;
	}

	@Override
	public ImageView getImageViewPauseIcon() {
		return imageViewPauseIcon;
	}

	public ImageView getImageViewPages() {
		return imageViewPages;
	}

	@Override
	public ImageView getImageViewLinkIcon() {
		return imageViewLinkIcon;
	}

	public Label getLabelTotalPagesDownloaded() {
		return labelTotalPagesDownloaded;
	}

	public Label getLabelDownloadedLink() {
		return labelDownloadedLink;
	}
	public ImageView getImageViewOpenFolderIcon() {
		return imageViewOpenFolderIcon;
	}
	public ImageView getImageViewReadIcon() {
		return imageViewReadIcon;
	}

	@Override
	public ImageView getImageViewTitleMediaIcon() {
		
		return imageViewReadIcon;
	}

	@Override
	public ImageView getImageViewDeleteIcon() {
		
		return imageViewDeleteIcon;
	}

	@Override
	public ImageView getImageViewRefreshIcon() {
		
		return imageViewRefreshIcon;
	}

	@Override
	public BorderPane getBorderPaneMain() {
		return borderPaneMain;
	}
}
