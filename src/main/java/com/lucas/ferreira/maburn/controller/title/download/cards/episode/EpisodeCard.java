package com.lucas.ferreira.maburn.controller.title.download.cards.episode;

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

public class EpisodeCard implements DownloadCard{
	
	@FXML
	private StackPane root;
	@FXML
	private BorderPane borderPaneDetails;
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
	private ImageView imageViewOpenFolderIcon;
	@FXML
	private ImageView imageViewWatchIcon;
	
	@FXML
	private ImageView imageViewLinkIcon;
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


	@Override
	public ImageView getImageViewLinkIcon() {
		return imageViewLinkIcon;
	}
	
	public ImageView getImageViewOpenFolderIcon() {
		return imageViewOpenFolderIcon;
	}
	public ImageView getImageViewWatchIcon() {
		return imageViewWatchIcon;
	}
	@Override
	public ImageView getImageViewTitleMediaIcon() {
		
		return imageViewWatchIcon;
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
	public String toString() {
		return String.format(
				"EpisodeCard [root=%s, borderPaneDetails=%s, labelItemTitle=%s, labelDownloadLink=%s, labelTotalSize=%s, labelDownloadSpeed=%s, labelTimeRemain=%s, labelPorcentageConcluded=%s, labelDownloadedLink=%s, labelDownloadState=%s, labelCompletedDownloaded=%s, labelVideoResoution=%s, progressBarDownload=%s, imageViewDownloadIcon=%s, imageViewPlayerIcon=%s, imageViewStopIcon=%s, imageViewPauseIcon=%s, imageViewLinkIcon=%s]",
				root, borderPaneDetails, labelItemTitle, labelDownloadLink, labelTotalSize, labelDownloadSpeed,
				labelTimeRemain, labelPorcentageConcluded, labelDownloadedLink, labelDownloadState,
				labelCompletedDownloaded, labelVideoResoution, progressBarDownload, imageViewDownloadIcon,
				imageViewPlayerIcon, imageViewStopIcon, imageViewPauseIcon, imageViewLinkIcon);
	}












	


}
