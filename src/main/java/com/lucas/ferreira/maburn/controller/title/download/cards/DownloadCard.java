package com.lucas.ferreira.maburn.controller.title.download.cards;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public interface DownloadCard extends PaneCard{
	public StackPane getRoot();

	public BorderPane getBorderPaneDetails();

	public Label getLabelItemTitle();

	public Label getLabelDownloadLink();

	public Label getLabelTotalSize();

	public Label getLabelDownloadSpeed();

	public Label getLabelTimeRemain();

	public Label getLabelPorcentageConcluded();

	public Label getLabelDownloadState();

	public Label getLabelVideoResoution();

	public ProgressBar getProgressBarDownload();

	public ImageView getImageViewDownloadIcon();

	public ImageView getImageViewPlayerIcon();

	public ImageView getImageViewStopIcon();

	public ImageView getImageViewPauseIcon();

	public ImageView getImageViewLinkIcon();
	
	public ImageView getImageViewTitleMediaIcon();
	public ImageView getImageViewOpenFolderIcon();
	public ImageView getImageViewDeleteIcon();
	public ImageView getImageViewRefreshIcon();

	
	Label getLabelCompletedDownload();
}
