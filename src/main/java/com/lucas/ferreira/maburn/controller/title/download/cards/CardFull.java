package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.model.enums.Category;

import javafx.scene.Node;

public interface CardFull<C extends CardController,P extends PaneCard, V> {
	
	public C getCardController();
	public void setCardController(C c);
	public P getCard();
	public void setCard(P c);
	public V getCardValues();
	public void setCardValues(V c);
	public void setNode(Node node);
	public Node getNode();

}
