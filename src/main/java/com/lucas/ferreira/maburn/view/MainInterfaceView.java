package com.lucas.ferreira.maburn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import com.lucas.ferreira.maburn.controller.MenuController;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.Resources;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainInterfaceView extends ViewInterface {

	private JFrame frame;
	private boolean visibility = true;
	private VBox root = new VBox();
	private final JFXPanel fxPanel = new JFXPanel();
	private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	private GridPane menuGridPane = new GridPane();
	private Scene scenePane;
	private boolean initializeIsDone = false;
	private MenuController menuController = new MenuController();
	private static MainInterfaceView app;
	
	
	public static MainInterfaceView getInstance() {
		if(app == null) {
			app = new MainInterfaceView();
		}
		return app;
	}
	
	public void initMainInterfaceView() throws IOException {
		
		initFXMLLoader(menuController, root,"MainViewFXML.fxml");
		initMenuPane();
		initRoot();

	}

	public void initAndShowGUI() {
		// This method is invoked on Swing thread
		CustomLogger.log("> Run MainInterfaceView");
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

				try {
					initFX(fxPanel);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		while (!initializeIsDone) {
			CustomLogger.log("> Loading view");
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
		super.root = this.root;
		root.getChildren().add(menuGridPane);

	}

	@Override
	protected void initFXMLLoader(Initializable initializable, Pane root, String fxml) throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(root);
		loader.setLocation(getClass().getResource(fxml));
		loader.setController(initializable);

		root = loader.<VBox>load();
		
	}
	private void initFX(final JFXPanel fxPanel) throws IOException {

		CustomLogger.log("> Initialize MainInterfaceView");
		initMainInterfaceView();

		createScene();

		fxPanel.setScene(scenePane);
		CustomLogger.log("> Initialization Complete  MainInterfaceView");
		frame.setVisible(visibility);

		initializeIsDone = true;

	}

	private void createScene() {
		scenePane = new Scene(root, 600, 300);
		
		//BufferedReader reader = new BufferedReader(new InputStreamReader(Resources.getResourceAsStream("DarkThema")));

		
		
		

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
	
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	protected void loadMainInterfaceFX() {
		// TODO Auto-generated method stub
		
	}
	
	public MenuController getMenuController() {
		return menuController;
	}


}
