package com.lucas.ferreira.maburn.controller.title.download;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Sites;

public class TitleDownloadControlers  implements Initialize{

	private TitleDownloadController titleDownloadController;
	private TitleDownload titleDownload;
	private Title title;

	public TitleDownloadControlers(TitleDownload titleDownload, TitleDownloadController titleDownloadController,
			Title title) {
		this.titleDownload = titleDownload;
		this.titleDownloadController = titleDownloadController;
		this.title = title;
	}

	public void initialize() {
		initializeButtons();
		initializeSelectItemsValues();
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
}
