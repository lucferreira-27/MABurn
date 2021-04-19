package com.lucas.ferreira.maburn.view;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import com.lucas.ferreira.maburn.controller.MenuController;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub

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
				loadFXML();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AlertWindowView.exceptionAlert(e);
				System.exit(0);
			}

		});
	}

	private void loadFXML() {
		fxmlViewLoader.loadInterface("MainViewFXML.fxml", menuController);

	}

	private void waitInitializeIsDone() {
		while (!initializeIsDone && !fxmlViewLoader.isDone()) {
			// CustomLogger.log("> Loading view");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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

		fxmlViewLoader.loadProperty().addListener((obs, oldvalue, newvalue) -> {
			initializeIsDone = true;

		});
		CustomLogger.log("> Initialize MainInterfaceView");
		createScene();

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

		stage.setScene(scenePane);

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
