package com.lucas.ferreira.maburn.view;

import com.lucas.ferreira.maburn.controller.menu.MenuController;
import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.model.fetch.title.FetchTitle;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class MainInterfaceView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final JFXPanel fxPanel = new JFXPanel();
	private Stage stage;
	private Scene scenePane;
	private JFrame loadFrame = new JFrame("LOAD MABurn");
	private boolean visibility = true;
	private Pane root = new VBox();
	private boolean initializeIsDone = false;
	private MenuController menuController = new MenuController();
	private static MainInterfaceView app;
	private InitializeModel initialize = new InitializeModel();

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
		frameSettings();
		initializeFXPanel();
		waitInitializeIsDone();
		hideLoadFrame();
		initialize.boot();
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

				initFX(fxPanel);

			} catch (Exception e) {
				e.printStackTrace();
				AlertWindowView.exceptionAlert(e);
				System.exit(0);
			}

		});
	}

	private void waitInitializeIsDone() {
		while (!initializeIsDone) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void frameSettings() {
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

		LOGGER.config("Initialize MainInterfaceView");
		createScene();

		initializeIsDone = true;
		LOGGER.config("Initialization Complete  MainInterfaceView");

	}

	private void createScene() {
		scenePane = new Scene(root);
		scenePane.getStylesheets().add("/style/DarkThema.css");

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
					LocalServer.getWebServer().stop();
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
		LOGGER.config("Hide MainFrame");
		Platform.runLater(() -> stage.hide());
	}

	public void show() {
		LOGGER.config("Show MainFrame");
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
