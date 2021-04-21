package com.lucas.ferreira.maburn.view.fxml;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class FXMLViewLoader {
	private static FXMLLoader loader;

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

	public void loadComponent(String fxml, Initializable initializable, int position) {
		this.root = MainInterfaceView.getInstance().getRoot();

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxml));
			loader.setController(initializable);

			Object fxmlLoaded = loader.load();
			Platform.runLater(() -> {
				try {
					root.getChildren().set(position, (Node) fxmlLoaded);

				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					root.getChildren().set(0, (Node) fxmlLoaded);

				}
				Navigator.getMapNodesComponts().put(initializable, (Node) fxmlLoaded);
				// root.getChildren().add((Node) fxmlLoaded);
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void hideComponent(Initializable initializable) {
		this.root = MainInterfaceView.getInstance().getRoot();

		Platform.runLater(() -> {
			Node component = Navigator.getMapNodesComponts().get(initializable);
			if (root.getChildren().contains(component)) {
				root.getChildren().get(0).setVisible(false);
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
