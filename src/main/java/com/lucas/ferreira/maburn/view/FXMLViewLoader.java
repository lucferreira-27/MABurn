package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.model.states.ObjectState;
import com.lucas.ferreira.maburn.util.JarUtils;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.LoadInterface;
import com.lucas.ferreira.maburn.view.navigator.Navigator;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Logger;

public class FXMLViewLoader<T extends Node> {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final String FXML_ROOT;
	private static FXMLLoader loader;

	static {
		loader = new FXMLLoader();
	}

	private BooleanProperty loaded = new SimpleBooleanProperty(false);
	private Pane root;

	public FXMLViewLoader() {
		boolean isResourceInJar = false;
		try {
			isResourceInJar = JarUtils.isResourceInJar();
		}catch(Exception e){

		}
		if(isResourceInJar){
			FXML_ROOT = "../../../../../fxml/";
		}else{
			FXML_ROOT = "../../../../../fxml/";
		}

	}

	public void loadInterface(LoadInterface loadInterface) {

		this.root = MainInterfaceView.getInstance().getRoot();
		new Thread(() -> {
			remove(root); // Removes the previous nodes.
			initFX(loadInterface); // Initializes
									// new
			// interface.

		}).start();

	}

	public void loadComponent(Components components) throws Exception {

		this.root = MainInterfaceView.getInstance().getRoot();
		Initializable initializable = components.getController();
		String fxml = components.getFxml();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Resources.getResourcePath("fxml/" + fxml).toUri().toURL());
		loader.setController(initializable);
		Node fxmlLoaded = loader.load();
		Platform.runLater(() -> {
			switch (components) {
			case MENU:
				root.getChildren().set(0, fxmlLoaded);
				break;
			case COLLECTION_MENU:
				StackPane stackPane = (StackPane) root.getChildren().get(1);

				StackPane.setAlignment(fxmlLoaded, Pos.BOTTOM_CENTER);
				stackPane.getChildren().add(fxmlLoaded);
				break;

			default:
				break;
			}
			Navigator.getMapNodesComponents().put(initializable, fxmlLoaded);

		});

	}

	public T load(String fxml, Initializable initializable, Node root) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Resources.getResourcePath("fxml/" + fxml).toUri().toURL());
		loader.setController(initializable);
		loader.setRoot(root);
		T fxmlLoaded = loader.load();
		return fxmlLoaded;

	}

	public T loadContainer(String fxml, Initializable initializable) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Resources.getResourcePath("fxml/" + fxml).toUri().toURL());
		loader.setController(initializable);
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
				component = Navigator.getMapNodesComponents().get(initializable);
				if (root.getChildren().contains(component)) {
					root.getChildren().get(0).setVisible(false);
				}
				break;
			case COLLECTION_MENU:
				StackPane stackPane = (StackPane) root.getChildren().get(2);
				component = Navigator.getMapNodesComponents().get(initializable);
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

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isDone() {
		return loaded.get();
	}

	private void initFX(LoadInterface loadInterface) {
		LOGGER.config("Initialize FXML: " + loadInterface.getFxml());
		Platform.runLater(() -> {

			try {
				if (loadInterface.getObjectState() == ObjectState.NEW_OBJECT)
					initFXMLLoader(loadInterface.getInitializable(), root, loadInterface.getFxml(),
							loadInterface.isVisibility());
				else
					addNodeInRoot(root, loadInterface.isVisibility(), loadInterface.getOldNode());

				loaded.set(true);

			} catch (Exception e) {

				e.printStackTrace();
			}

		});

	}

	private void remove(Pane root) {
		LOGGER.config("Remove child from root");
		Platform.runLater(() -> {
			if (root.getChildren().size() > 1)
				root.getChildren().remove(1, root.getChildren().size());

		});
	}

	private void initFXMLLoader(Initializable initializable, Pane root, String fxml, boolean visibility)
			throws Exception {
		loader.setRoot(root);


		loader.setLocation(Resources.getResourcePath("fxml/" + fxml).toUri().toURL());
		loader.setController(initializable);
		Object fxmlLoaded;

		fxmlLoaded = loader.load();

		addNodeInRoot(root, visibility, fxmlLoaded);

	}

	private void addNodeInRoot(Pane root, boolean visibility, Object fxmlLoaded) {
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
