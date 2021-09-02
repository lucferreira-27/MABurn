package com.lucas.ferreira.maburn.controller.title.download.title;

import com.lucas.ferreira.maburn.model.Initialize;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.sites.RecoverSites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TitleDownloadControlers implements Initialize {

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
		initializeLabels();

	}

	private void initializeButtons() {
		titleDownload.getBtnDownload().setOnAction(event -> titleDownloadController.onClickDownloadStart());
	}

	private void initializeSelectItemsValues() {
		try {

			RecoverSites recoverSites = new RecoverSites();

			List<RegisteredSite> registeredSites = recoverSites.recoverAll().stream()
					.filter((f) -> f.getSiteConfig().getCategory() == title.getCategory()).collect(Collectors.toList());
			titleDownload.getCbSource().getItems().addAll(registeredSites);
			titleDownload.getCbSource().setCellFactory(param -> addCellFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		titleDownload.getCbSelect().getItems().addAll(Arrays.asList((FetchItemType.values())));

	}
	public ListCell<RegisteredSite> addCellFactory(){
		IntegerProperty count = new SimpleIntegerProperty();
		return new ListCell<RegisteredSite>() {

			final Label label = new Label();

			@Override
			protected void updateItem(RegisteredSite item, boolean empty) {
				super.updateItem(item, empty);
				count.set(count.get() + 1);
				if (item == null || empty) {
					setGraphic(null);
				} else {
					String name = item.getSiteConfig().getName();
					String language = getLongLanguagName(item.getSiteConfig().getLanguage());
					label.setText(name + " (" + language +")");
					setGraphic(label);
				}
			}
			private String getLongLanguagName(String shortLanguageName){
				if(shortLanguageName.equals("PT_BR")){
					return "Portuguese BR";
				}
				if(shortLanguageName.equals("EN_USA")){
					return "English USA";
				}
				return null;
			}
		};

	}

	private void initializeLabels() {

		labelItemsSelector();

	}

	private void labelItemsSelector() {
		Platform.runLater(() -> {
			if (title.getCategory() == Category.MANGA) {
				titleDownload.getLblItemsSelecter().setText(CHAPTERS_LABEL_NAME);
			} else if (title.getCategory() == Category.ANIME) {
				titleDownload.getLblItemsSelecter().setText(EPISODES_LABEL_NAME);

			}
		});
	}

}
