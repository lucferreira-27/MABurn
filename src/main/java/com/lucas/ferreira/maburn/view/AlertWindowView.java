package com.lucas.ferreira.maburn.view;
import java.util.Optional;

import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class AlertWindowView {
	
	
	
	public AlertWindowView() {
		// TODO Auto-generated constructor stub
	}
	
	public static  void errorAlert(String title, String header, String msg) {

		Platform.runLater(() -> // switches to GUI Thread
		{	
			Alert alert = new Alert(AlertType.ERROR);
			 Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			 stage.setAlwaysOnTop(true);
			try{
			//alert.getDialogPane().getStyleClass().add("dialog-pane");
			alert.getDialogPane().setStyle("-fx-background-color: RED");
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			CustomLogger.log(alert.getDialogPane().getStyle());
			alert.showAndWait();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});
	}

	public static  void infoAlert(String title, String header, String msg) {
		Platform.runLater(() -> // switches to GUI Thread
		{
			Alert alert = new Alert(AlertType.INFORMATION);
		
			//alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkThema.css").getPath());

			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
		});

	}

	public static void warningAlert(String title, String header, String msg) {
		Platform.runLater(() -> // switches to GUI Thread
		{
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}

	public static  boolean confirmationAlert(String title, String header, String msg) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		

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
	
	public static String inputAlet(String title, String header) {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(header);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		   return result.get();
		}

		return null;
	}

//	public  String showInputTextDialog(String title, String header, String msg) {
//		Platform.runLater(() -> // switches to GUI Thread
//		{
//			TextInputDialog dialog = new TextInputDialog();
//			dialog.setTitle(title);
//			dialog.setHeaderText(header);
//			dialog.setContentText(msg);
//			Optional<String> result = dialog.showAndWait();
//
//			result.ifPresent(name -> {
//				label = name;
//			});
//		});
//		return label;
//
//	}

//	public String getLabel() {
//		return label;
//	}

}
