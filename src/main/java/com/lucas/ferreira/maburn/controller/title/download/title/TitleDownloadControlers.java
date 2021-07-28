package com.lucas.ferreira.maburn.controller.title.download.title;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Sites;

import javafx.application.Platform;

public class TitleDownloadControlers  implements Initialize{

	private TitleDownloadController titleDownloadController;
	private TitleDownloadModel titleDownload;
	private Title title;
	private final static String CHAPTERS_LABEL_NAME = "Chapters";
	private final static String EPISODES_LABEL_NAME = "Episodes";

	public TitleDownloadControlers(TitleDownloadModel titleDownload, TitleDownloadController titleDownloadController,
			Title title) {
		this.titleDownload = titleDownload;
		this.titleDownloadController = titleDownloadController;
		this.title = title;
	}

	public void initialize() {
		initializeButtons();
		initializeSelectItemsValues();
		intializeLabels();

	}
	private void initializeButtons() {
		titleDownload.getBtnDownload().setOnAction(event -> titleDownloadController.onClickDownloadStart());
	}

	private void initializeSelectItemsValues() {

		List<Sites> sites = Arrays.asList(Sites.values()).stream().filter((f) -> f.getCategory() == title.getCategory())
				.collect(Collectors.toList());
		titleDownload.getCbSource().getItems().addAll(sites);

		titleDownload.getCbSelect().getItems().addAll(Arrays.asList((FetchItemType.values())));
	}
	
	private void intializeLabels() {
		
		labelItemsSelector();
		

	}

	private void labelItemsSelector() {
		Platform.runLater(() ->{
			if(title.getCategory() == Category.MANGA) {
				titleDownload.getLblItemsSelecter().setText(CHAPTERS_LABEL_NAME);
			}else  if(title.getCategory() == Category.ANIME){
				titleDownload.getLblItemsSelecter().setText(EPISODES_LABEL_NAME);

			}
		});
	}
		

}
