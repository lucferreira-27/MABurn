package com.lucas.ferreira.maburn.view.fxml;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLViewLoader<T extends Node> {
	private static FXMLLoader loader;
	private VBox vBox = new VBox();
	private ScrollPane scrollPane = new ScrollPane();
	
	static {
		loader = new FXMLLoader();
	}

	private BooleanProperty loaded = new SimpleBooleanProperty(false);
	private Pane root;
	private long start;

	public FXMLViewLoader() {
		// TODO Auto-generated constructor stub
		start = System.currentTimeMillis(); // Gets the current date

		loaded.addListener((obs, oldvalue, newvalue) -> {
			if (newvalue) {
				long end = System.currentTimeMillis();

				System.out.println("> FXMLViewLoader ... [DONE] TIME: " + (end - start));
			}
		});
	}

	public void loadInterface(String fxml, Initializable initializable, boolean visibility) {

		this.root = MainInterfaceView.getInstance().getRoot();
		new Thread(() -> {
			remove(root); // Removes the previous nodes.
			initFX(fxml, initializable, visibility); // Initializes interface.
		}).start();

	}

	public void loadComponent(Components components) throws IOException {

		this.root = MainInterfaceView.getInstance().getRoot();
		Initializable initializable = components.getController();
		String fxml = components.getFxml();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(initializable);
		Node fxmlLoaded = loader.load();
		Platform.runLater(() -> {
			switch (components) {
			case MENU:
				root.getChildren().set(0, fxmlLoaded);
				break;
			case COLLECTION_MENU:
				StackPane stackPane = (StackPane) root.getChildren().get(1);
				System.out.println("NODE: " + fxmlLoaded);
				StackPane.setAlignment(fxmlLoaded, Pos.BOTTOM_CENTER);
				stackPane.getChildren().add(fxmlLoaded);
				break;


			default:
				break;
			}
			Navigator.getMapNodesComponts().put(initializable, fxmlLoaded);

		});

	}
	public T load(String fxml, Initializable initializable, Node root) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(initializable);
		loader.setRoot(root);
		T fxmlLoaded = loader.load();
		return fxmlLoaded;

	}

	public void hideComponent(Components components) {
		this.root = MainInterfaceView.getInstance().getRoot();
		Platform.runLater(() -> {
			Node component;
			Initializable initializable = components.getController();
			switch (components) {
			case MENU:
				component = Navigator.getMapNodesComponts().get(initializable);
				if (root.getChildren().contains(component)) {
					root.getChildren().get(0).setVisible(false);
				}
				break;
			case COLLECTION_MENU:
				StackPane stackPane = (StackPane) root.getChildren().get(2);
				component = Navigator.getMapNodesComponts().get(initializable);
				if (stackPane.getChildren().contains(component)) {
					int index = stackPane.getChildren().size() - 1;
					stackPane.getChildren().get(index).setVisible(false);
				}
				break;

			default:
				break;
			}
		});

	}

	public void addCompomentInRoot(String fxml, Initializable initializable, Pane root) {

		try {
			initFXMLLoader(initializable, root, fxml, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	public boolean isDone() {
		return loaded.get();
	}

	private void initFX(String fxml, Initializable initializable, boolean visibility) {
		CustomLogger.log("> Run " + fxml);
		Platform.runLater(() -> {

			try {
				initFXMLLoader(initializable, root, fxml, visibility);
				loaded.set(true);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	private void remove(Pane root) {
		CustomLogger.log("Root Children: " + root.getChildren().size());
		Platform.runLater(() -> {
			if (root.getChildren().size() > 1)
				root.getChildren().remove(1, root.getChildren().size());

		});
	}

	private void initFXMLLoader(Initializable initializable, Pane root, String fxml, boolean visibility)
			throws IOException {

		loader.setRoot(root);
		System.out.println(fxml);
		System.out.println(getClass().getResource(fxml));
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(initializable);
		Object fxmlLoaded;

		fxmlLoaded = loader.load();
		// Navigator.getMapRoot().put(initializable, (Pane) fxmlLoaded);
		if (root == null || fxmlLoaded == root) {
			root = (Pane) fxmlLoaded;
			root.setVisible(visibility);
			return;
		}
		root.getChildren().add((Node) fxmlLoaded);

	}

	public BooleanProperty loadProperty() {
		return loaded;
	}

}
