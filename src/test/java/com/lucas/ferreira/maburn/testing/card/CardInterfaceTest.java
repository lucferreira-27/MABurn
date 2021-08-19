package com.lucas.ferreira.maburn.testing.card;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.controller.title.download.cards.CardFXML;
import com.lucas.ferreira.maburn.controller.title.download.cards.DownloadCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterCard;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeCard;
import com.lucas.ferreira.maburn.model.effects.DefaultAnimationCard;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;
import com.microsoft.playwright.Download;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CardInterfaceTest {
	@Test
	public void test() {
		new JFXPanel();
		
		Platform.runLater(() -> {
			FXMLViewLoader<Node> fxmlViewLoader = new FXMLViewLoader<Node>();
			AnchorPane anchorPane = new AnchorPane();
			try {
				DownloadCard epCard =	new EpisodeCard();
				Node node = fxmlViewLoader.load(CardFXML.DOWNLOAD_EPISODE_CARD.getFxml(), epCard,
						new StackPane());

				DefaultAnimationCard defaultAnimationCard = new DefaultAnimationCard(epCard);
				defaultAnimationCard.initAnimationCard();
				
				Stage stage = new Stage();
				Scene scene = new Scene(anchorPane);
				anchorPane.getChildren().add(node);
				stage.setScene(scene);;
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
