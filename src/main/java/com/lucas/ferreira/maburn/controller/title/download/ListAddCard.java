package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;
import java.util.stream.Stream;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardController;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardFull;
import com.lucas.ferreira.maburn.controller.title.download.cards.fetch.FetchCardValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ListAddCard {
	private VBox vBox;
	private FXMLViewLoader<StackPane> fxmlViewLoader = new FXMLViewLoader<StackPane>();

	public ListAddCard(VBox vBox) {
		this.vBox = vBox;
	}

	public FetchCardFull createFetchCardFull(FetchCardLoaded fetchCardLoaded) {

		FetchCard fetchCard = fetchCardLoaded.getFetchCard();
		FetchCardValues fetchCardValues = fetchCardLoaded.getFetchCardValues();
		loadCardFxml((Initializable) fetchCard, fetchCardValues.getItemName(), CardFXML.FETCH_CARD);

		return new FetchCardController(fetchCard, fetchCardValues).initialize();
	}

	public DownloadCardFull createDownloadCardFull(DownloadCardLoaded downloadCardLoaded) {

		DownloadCard downloadCard = downloadCardLoaded.getDownloadCard();
		DownloadInfo downloadInfo = downloadCardLoaded.getDownloadInfo();
		CardFXML cardFXML = downloadCardLoaded.getCardFXML();

		loadCardFxml((Initializable) downloadCard, downloadInfo.getFilename(), cardFXML);

		switch (downloadCardLoaded.getCardFXML()) {
		case DOWNLOAD_EPISODE_CARD:
			try {
				return new EpisodeCardController((EpisodeCard) downloadCard, downloadInfo).initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}

		case DOWNLOAD_CHAPTER_CARD:
			try {
				return new ChapterCardController((ChapterCard) downloadCard, downloadInfo).initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}

		default:
			break;
		}
		return null;

	}

	public void loadCardFxml(Initializable initializable, String name, CardFXML cardFxml) {
		StackPane stackPane = new StackPane();
		try {
			StackPane item = fxmlViewLoader.load(cardFxml.getFxml(), initializable, stackPane);
			defineId(name, item);
			insertInList(name, item);

		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	}

	private void defineId(String name, StackPane item) {
		String definedId = name;
		item.setUserData(definedId);
	}

	private void insertInList(String definedId, StackPane item) {
		Platform.runLater(() -> {
			Node card = seekCardByDefinedIdInList(definedId).findFirst().orElse(null);
			if (card != null)
				replaceFetchCardByDownloadCard(item, card);
			else
				vBox.getChildren().add(item);

		});
	}

	private void replaceFetchCardByDownloadCard(StackPane item, Node card) {
		vBox.getChildren().set(vBox.getChildren().indexOf(card), item);
	}

	private Stream<Node> seekCardByDefinedIdInList(String definedId) {
		return vBox.getChildren().stream().filter(child -> child.getUserData().equals(definedId));
	}

}
