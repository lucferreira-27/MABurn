package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.ChapterDownloadValues;
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

	public void add(DownloadCard downloadCard, DownloadInfo downloadInfo, CardFXML cardFxml) {
		loadCardFxml((Initializable) downloadCard, downloadInfo.getFilename(), cardFxml);
		
		
		switch (cardFxml) {
		case DOWNLOAD_EPISODE_CARD:
			addCardController(new EpisodeCardController((EpisodeCard) downloadCard, downloadInfo));

			break;
		case DOWNLOAD_CHAPTER_CARD:
			addCardController(new ChapterCardController((ChapterCard) downloadCard, downloadInfo));
			break;
		default:
			break;
		}
		

	}

	private void addCardController(DownloadCardController controller) {

		cardsControllers.add(controller);
		controller.initialize();

	}
	

	public void loadCardFxml(Initializable initializable, String name, CardFXML cardFxml) {
		StackPane stackPane = new StackPane();
		try {
			System.out.println(cardFxml.getFxml());
			StackPane item = fxmlViewLoader.load(cardFxml.getFxml(), initializable, stackPane);
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
