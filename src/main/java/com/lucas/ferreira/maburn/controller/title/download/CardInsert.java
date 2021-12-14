package com.lucas.ferreira.maburn.controller.title.download;

import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CardInsert {
	
	
	private VBox vBox;
	
	public CardInsert(VBox vBox) {

		this.vBox = vBox;
	}
	

	public void insertInList(String definedId, Node item) {
		Platform.runLater(() -> {
			Node card = seekCardByDefinedIdInList(definedId).findFirst().orElse(null);
			if (card != null)
				replaceFetchCardByDownloadCard(item, card);
			else
				vBox.getChildren().add(item);
		});
	}

	private void replaceFetchCardByDownloadCard(Node item, Node card) {
		vBox.getChildren().set(vBox.getChildren().indexOf(card), item);
	}

	private Stream<Node> seekCardByDefinedIdInList(String definedId) {
		return vBox.getChildren().stream().filter(child -> child.getUserData().equals(definedId));
	}
}
