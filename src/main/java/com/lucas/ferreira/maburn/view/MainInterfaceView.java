package com.lucas.ferreira.maburn.view;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.swing.JFrame;

import com.lucas.ferreira.maburn.controller.menu.MenuController;
import com.lucas.ferreira.maburn.model.fetch.title.FetchTitle;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainInterfaceView {

	private final JFXPanel fxPanel = new JFXPanel();
	private Stage stage;
	private Scene scenePane;
	private JFrame loadFrame = new JFrame("LOAD MABurn");
	private boolean visibility = true;
	private Pane root = new VBox();
	private boolean initializeIsDone = false;
	private MenuController menuController = new MenuController();
	private static MainInterfaceView app;
	private FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();

	public MainInterfaceView() {
		
		Platform.runLater(() -> {
			stage = new Stage();
		});
	}

	public static MainInterfaceView getInstance() {
		if (app == null) {
			app = new MainInterfaceView();
		}
		return app;
	}

	public void initAndShowGUI() {
		// This method is invoked on Swing thread

		CustomLogger.log("> Run MainInterfaceView");
		frameSettings();
		initializeFXPanel();
		waitInitializeIsDone();
		hideLoadFrame();
		show();

	}

	private void hideLoadFrame() {
		

		Platform.runLater(() -> {

			loadFrame.setVisible(false);
			loadFrame.dispose();

		});

	}

	private void initializeFXPanel() {
		Platform.runLater(() -> {

			try {
//				Navigator navigator = new Navigator();
//				navigator.open(Interfaces.MAIN);
				initFX(fxPanel);
//				loadFXML();

			} catch (Exception e) {
				e.printStackTrace();
				AlertWindowView.exceptionAlert(e);
				System.exit(0);
			}

		});
	}

	private void loadFXML() {
		fxmlViewLoader.loadInterface("MainViewFXML.fxml", menuController, true);

	}

	private void waitInitializeIsDone() {
		while (!initializeIsDone) {
			// CustomLogger.log("> Loading view");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void frameSettings() {
		Resources resources = new Resources();
		ImagePanel imageContent = new ImagePanel();
		loadFrame.getContentPane().add(imageContent);

		loadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadFrame.setUndecorated(true);
		loadFrame.pack();
		loadFrame.setLocationRelativeTo(null);
		loadFrame.setBackground(new Color(0, 0, 0, 0));
		loadFrame.setVisible(true);

	}

	private void initFX(final JFXPanel fxPanel) throws IOException {

//		fxmlViewLoader.loadProperty().addListener((obs, oldvalue, newvalue) -> {
//			initializeIsDone = true;
//		});
		CustomLogger.log("> Initialize MainInterfaceView");
		createScene();
		Navigator navigator = new Navigator();
		navigator.preload(Interfaces.COLLECTION); // preload collection.
		initializeIsDone = true;

		CustomLogger.log("> Initialization Complete  MainInterfaceView");

	}

	private void createScene() {
		scenePane = new Scene(root);
		scenePane.getStylesheets().add("com/lucas/ferreira/maburn/view/css/DarkThema.css");

		InputStream in = Resources.getResourceAsStream("icons/icon.png");
		Image icon = new Image(in);
		stage.getIcons().add(icon);
		stage.setTitle("MABurn");
		stage.setMaximized(true);
//		stage.setMinWidth(1232);
//		stage.setMinHeight(771);
		stage.setScene(scenePane);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				try {
					Files.delete(FetchTitle.getDriverPath());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					Platform.exit();
					System.exit(0);
				}
			}
		});

	}

	public void hide() {
		CustomLogger.log("HIDE INTERFACE");
		Platform.runLater(() -> stage.hide());
	}

	public void show() {
		CustomLogger.log("SHOW INTERFACE");
		Platform.runLater(() -> stage.show());

	}

	public Pane getRoot() {
		return root;
	}

	public Scene getScenePane() {
		return scenePane;
	}

	public boolean isInitializeDone() {
		return initializeIsDone;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;

		if (!visibility)
			hide();
		else
			show();
	}

	public boolean IsVisibility() {
		return visibility;
	}

	public MenuController getMenuController() {
		return menuController;
	}

}
