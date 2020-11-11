package com.lucas.ferreira.maburn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.lucas.ferreira.maburn.controller.MenuController;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainInterfaceView {

	private JFrame frame;
	private VBox root = new VBox();
	private final JFXPanel fxPanel = new JFXPanel();
	private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	private GridPane menuGridPane = new GridPane();
	private Scene scenePane;
	private boolean initializeIsDone = false;
	// #### DEVELOPMENT ######
	// Note: Keep the scene and change the childrens.

	public void initMainInterfaceView() {
		initFXMLLoader();
		initMenuPane();
		initRoot();

	}

	public void initAndShowGUI() {
		// This method is invoked on Swing thread
		System.out.println("> Run MainInterfaceView");
		frame = new JFrame("MA Burn");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		frame.add(fxPanel, BorderLayout.CENTER);
		frame.setBackground(Color.BLACK);
		frame.setVisible(false);
		frame.setSize(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
	 	Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("resources/icon.png"));
		frame.setIconImage(icon);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				initFX(fxPanel);
			}
		});
		while (!initializeIsDone) {
			System.out.println("> Loading view");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initRoot() {
		// root.setStyle("-fx-background-color: #1C1C1C");
		root.getChildren().add(menuGridPane);

	}

	private void initFXMLLoader() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainViewFXML.fxml"));
		MenuController controller = new MenuController(this);
		loader.setController(controller);
		loader.setRoot(root);
		try {
			root = loader.<VBox>load();
			System.out.println(root.getChildren().size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initFX(final JFXPanel fxPanel) {

		System.out.println("> Initialize MainInterfaceView");
		initMainInterfaceView();

		createScene();

		fxPanel.setScene(scenePane);
		System.out.println("> Initialization Complete  MainInterfaceView");
		frame.setVisible(true);
		System.out.println();

		initializeIsDone = true;

	}

	private void createScene() {
		scenePane = new Scene(root, 600, 300);

		scenePane.getStylesheets().add("com/lucas/ferreira/maburn/view/DarkThema.css");

		fxPanel.setScene(scenePane);
	}

	private void initMenuPane() {

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


}
