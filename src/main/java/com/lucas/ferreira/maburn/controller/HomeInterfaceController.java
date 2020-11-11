package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.MangaCollection;
import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.ItensInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class HomeInterfaceController implements Initializable {

	private MainInterfaceView mainView;
	private MainInterfaceController mainController = new MainInterfaceController();
	private HomeInterfaceView homeView;
	private Pane collectionMenu;
	private Button btnCollectionAnime = new Button();
	private Button btnCollectionManga = new Button();

	public HomeInterfaceController(MainInterfaceView mainView) {
		// TODO Auto-generated constructor stub
		this.mainView = mainView;

	}

	public boolean active() {
		try {

			collectionMenu = (Pane) mainView.getRoot().getChildren().get(1);

			btnCollectionAnime = setClickEvents("collectionsBtnAnime");
			btnCollectionManga = setClickEvents("collectionsBtnManga");

			onClickOnAnime();
			onClickOnManga();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private Button setClickEvents(String id) {
		// TODO Auto-generated method stub
		ObservableList<Node> nodes = collectionMenu.getChildren();
		// nodes.stream().filter(n -> n.getId().equals(id) && n instanceof
		// Button).forEach(b -> btnCollectionAnime = (Button) b);
		for (Node node : nodes) {
			if (node.getId().equals(id)) {
				System.out.println(node.getId());

				return (Button) node;
			}
		}
		return null;

	}

	public void onClickOnAnime() {
		System.out.println("Anime");

		new Thread(() -> {
			mainController.selectCollection(new AnimeCollection());
			ItensInterfaceView itensView = new ItensInterfaceView(mainController.getFutureCollection());
			itensView.loadMainInterfaceFX(mainView);
		}).start();

	}

	public void onClickOnManga() {
		System.out.println("Manga");

		new Thread(() -> {
			mainController.selectCollection(new MangaCollection());
			ItensInterfaceView itensView = new ItensInterfaceView(mainController.getFutureCollection());
			itensView.loadMainInterfaceFX(mainView);
		}).start();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
