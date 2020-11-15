package com.lucas.ferreira.maburn.view;
import java.awt.Paint;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

public class AlertWindowView {
	private  Alert alert;
	private  String label;
	
	
	
	public AlertWindowView() {
		// TODO Auto-generated constructor stub
	}
	
	public  void errorAlert(String title, String header, String msg) {

		Platform.runLater(() -> // switches to GUI Thread
		{	
			alert = new Alert(AlertType.ERROR);
			try{
			//alert.getDialogPane().getStyleClass().add("dialog-pane");
			alert.getDialogPane().setStyle("-fx-background-color: RED");
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			System.out.println(alert.getDialogPane().getStyle());
			alert.showAndWait();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});
	}

	public  void infoAlert(String title, String header, String msg) {
		Platform.runLater(() -> // switches to GUI Thread
		{
			alert = new Alert(AlertType.INFORMATION);
		
			alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkThema.css").getPath());

			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
		});

	}

	public  void warningAlert(String title, String header, String msg) {
		Platform.runLater(() -> // switches to GUI Thread
		{
			alert = new Alert(AlertType.WARNING);
			alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkThema.css").getPath());

			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}

	public  boolean confirmationAlert(String title, String header, String msg) {

		alert = new Alert(AlertType.CONFIRMATION);
		
		alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkThema.css").getPath());

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// user chose OK
			return true;
		} else {
			// user chose CANCEL or closed the dialog
			return false;
		}
	}

	public  String showInputTextDialog(String title, String header, String msg) {
		Platform.runLater(() -> // switches to GUI Thread
		{
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle(title);
			dialog.setHeaderText(header);
			dialog.setContentText(msg);

			Optional<String> result = dialog.showAndWait();

			result.ifPresent(name -> {
				label = name;
			});
		});
		return label;

	}

	public String getLabel() {
		return label;
	}

}
