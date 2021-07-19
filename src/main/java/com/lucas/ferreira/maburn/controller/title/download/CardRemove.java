package com.lucas.ferreira.maburn.controller.title.download;

import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CardRemove {
	
	private VBox vBox;
	public CardRemove(VBox vBox) {
		this.vBox = vBox;
	}
	
	public void removeInList(String definedId, Node item) {
		Platform.runLater(() -> {
			Node card = seekCardByDefinedIdInList(definedId).findFirst().orElse(null);
			vBox.getChildren().remove(card);

		});
	}
	


	private Stream<Node> seekCardByDefinedIdInList(String definedId) {
		return vBox.getChildren().stream().filter(child -> child.getUserData().equals(definedId));
	}
}
