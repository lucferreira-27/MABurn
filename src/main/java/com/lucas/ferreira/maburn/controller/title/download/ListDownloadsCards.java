package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.EpisodeCardController;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.effects.AnimationOpacityCard;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ListDownloadsCards {
	private VBox vBox;
	private FXMLViewLoader<StackPane> fxmlViewLoader = new FXMLViewLoader<StackPane>();
	private List<DownloadCardController> cardsControllers = new ArrayList<DownloadCardController>();

	public ListDownloadsCards(VBox vBox) {
		this.vBox = vBox;
	}

	public void add(DownloadCard downloadCard, DownloadInfo downloadInfo) {
		loadCardFxml((Initializable) downloadCard, downloadInfo.getFilename());
		addCardController(downloadCard, downloadInfo);
	}

	private void addCardController(DownloadCard downloadCard, DownloadInfo downloadInfo) {
		EpisodeCardController episodeCardController = new EpisodeCardController((EpisodeCard) downloadCard,
				downloadInfo);
		cardsControllers.add(episodeCardController);
		
		Platform.runLater(() ->{
			downloadCard.getBorderPaneDetails().setOpacity(0);
			AnimationOpacityCard animationOpacityCard = new AnimationOpacityCard(downloadCard.getBorderPaneDetails());
			animationOpacityCard.fadeInCardBody(1, 0.5 / 100);
		});

		episodeCardController.initialize();

	}

	public void loadCardFxml(Initializable initializable, String name) {
		StackPane stackPane = new StackPane();
		try {
			StackPane item = fxmlViewLoader.load(Components.DOWNLOAD_CARD.getFxml(), initializable, stackPane);
			item.setUserData(name);
			Platform.runLater(() -> {
				Node fetchCard = vBox.getChildren().stream().filter(child -> child.getUserData().equals(name)).findFirst().get();
				vBox.getChildren().set(vBox.getChildren().indexOf(fetchCard),item);
	
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
