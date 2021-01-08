package com.lucas.ferreira.maburn.util.comparator;

import java.util.Comparator;

import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CollectionNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub

		AnchorPane pane1 = (AnchorPane) o1;
		AnchorPane pane2 = (AnchorPane) o2;
		FilteredList<Node> filterd1 = pane1.getChildren().filtered(child -> child instanceof ImageView);
		FilteredList<Node> filterd2 = pane2.getChildren().filtered(child -> child instanceof ImageView);

		CollectionItem item1 = (CollectionItem) ((ImageView) filterd1.get(0)).getUserData();
		CollectionItem item2 = (CollectionItem) ((ImageView) filterd2.get(0)).getUserData();
		return item1.getTitleDataBase().compareTo(item2.getTitleDataBase());

	}

}