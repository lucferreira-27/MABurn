package com.lucas.ferreira.maburn.view;

import java.io.IOException;

import com.lucas.ferreira.maburn.util.Resources;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalAlertWindow {
	private DialogPane dialogPane;
	private Stage stage;

	public void infoWindow(String title, String content) {
		final FXMLLoader loader = new FXMLLoader();

		Platform.runLater(() -> {
			loader.setLocation(ModalAlertWindow.class.getResource(ModalWindows.INFO_WINDOW.getFxml()));

			try {
				dialogPane = loader.load();
				dialogPane.getStylesheets().add("/style/ModalWindowThema.css");
				stage = new Stage();
				stage.setScene(new Scene(dialogPane));

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		waitModalLoaded();
		Label lblTitle = (Label) loader.getNamespace().get("lblTitle");
		Button btnOk = (Button) loader.getNamespace().get("btnOk");
		ImageView imgInfo = (ImageView) loader.getNamespace().get("imgInfo");

		btnOk.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				stage.hide();
			}
		});
		imgInfo.setImage(new Image(Resources.getResourceAsStream("icons/modal_info_medium.png")));
		TextArea areaContent = (TextArea) loader.getNamespace().get("areaContent");

		lblTitle.setText(title);
		areaContent.setText(content);

		stage.getIcons().add(new Image(Resources.getResourceAsStream("icons/modal_info_small.png")));
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);

		stage.initOwner(MainInterfaceView.getInstance().getScenePane().getWindow());
		Platform.runLater(() -> stage.show());

	}

	public void errorWindow(String title, String content) {
		final FXMLLoader loader = new FXMLLoader();

		Platform.runLater(() -> {
			loader.setLocation(ModalAlertWindow.class.getResource(ModalWindows.INFO_WINDOW.getFxml()));

			try {
				dialogPane = loader.load();
				stage = new Stage();
				stage.setScene(new Scene(dialogPane));

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		waitModalLoaded();
		
		
		try {

			Label lblTitle = (Label) loader.getNamespace().get("lblTitle");
			Button btnOk = (Button) loader.getNamespace().get("btnOk");
			ImageView imgInfo = (ImageView) loader.getNamespace().get("imgInfo");

			btnOk.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY) {
					stage.hide();
				}
			});
			imgInfo.setImage(new Image(Resources.getResourceAsStream("icons/modal_error_medium.png")));
			TextArea areaContent = (TextArea) loader.getNamespace().get("areaContent");

			lblTitle.setText(title);
			areaContent.setText(content);

			stage.getIcons().add(new Image(Resources.getResourceAsStream("icons/modal_error_small.png")));
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);

			stage.initOwner(MainInterfaceView.getInstance().getScenePane().getWindow());
			Platform.runLater(() -> stage.show());

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	private void waitModalLoaded() {
		while(dialogPane == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public BooleanProperty questionWindow(String title, String content) {
		final BooleanProperty output = new SimpleBooleanProperty(false);
		final FXMLLoader loader = new FXMLLoader();

		Platform.runLater(() -> {
			loader.setLocation(ModalAlertWindow.class.getResource(ModalWindows.QUESTION_WINDOW.getFxml()));

			try {
				dialogPane = loader.load();
				stage = new Stage();
				stage.setScene(new Scene(dialogPane));

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		waitModalLoaded();
		Label lblTitle = (Label) loader.getNamespace().get("lblTitle");
		Button btnYes = (Button) loader.getNamespace().get("btnYes");
		Button btnNo = (Button) loader.getNamespace().get("btnNo");

		ImageView imgInfo = (ImageView) loader.getNamespace().get("imgInfo"); 

		btnYes.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				output.set(true);
				stage.hide();
			}
		});
		btnNo.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				output.set(false);
				stage.hide();
			}
		});
		imgInfo.setImage(new Image(Resources.getResourceAsStream("icons/modal_question_medium.png")));
		TextArea areaContent = (TextArea) loader.getNamespace().get("areaContent");

		lblTitle.setText(title);
		areaContent.setText(content);

		stage.getIcons().add(new Image(Resources.getResourceAsStream("icons/modal_question_small.png")));
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);

		stage.initOwner(MainInterfaceView.getInstance().getScenePane().getWindow());
		Platform.runLater(() -> stage.show());
		return output;

	}
	
	public void confirmationWindow(String title, String content) {
		final FXMLLoader loader = new FXMLLoader();

		Platform.runLater(() -> {
			loader.setLocation(ModalAlertWindow.class.getResource(ModalWindows.INFO_WINDOW.getFxml()));

			try {
				dialogPane = loader.load();
				stage = new Stage();
				stage.setScene(new Scene(dialogPane));

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		waitModalLoaded();
		Label lblTitle = (Label) loader.getNamespace().get("lblTitle");
		Button btnOk = (Button) loader.getNamespace().get("btnOk");
		ImageView imgInfo = (ImageView) loader.getNamespace().get("imgInfo");

		btnOk.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				stage.hide();
			}
		});
		imgInfo.setImage(new Image(Resources.getResourceAsStream("icons/modal_confirmation_medium.png")));
		TextArea areaContent = (TextArea) loader.getNamespace().get("areaContent");

		lblTitle.setText(title);
		areaContent.setText(content);

		stage.getIcons().add(new Image(Resources.getResourceAsStream("icons/modal_confirmation_small.png")));
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);

		stage.initOwner(MainInterfaceView.getInstance().getScenePane().getWindow());
		Platform.runLater(() -> stage.show());

	}

}
