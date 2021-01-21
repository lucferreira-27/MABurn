package com.lucas.ferreira.maburn.view;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AlertWindowView {
	
	
	
	public AlertWindowView() {
		// TODO Auto-generated constructor stub
	}
	
	public static  void errorAlert(String title, String header, String msg) {
		
		// switches to GUI Thread
		Platform.runLater(() ->
		
		{							
			Alert alert = new Alert(AlertType.ERROR);
			 Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			 stage.setAlwaysOnTop(true);
			try{
			alert.getDialogPane().getStyleClass().add("dialog-pane");
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			CustomLogger.log(alert.getDialogPane().getStyleClass().get(1));
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
	public static void exceptionAlert(Throwable e) {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		
		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
	
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		
		Alert alert = new Alert(AlertType.ERROR);
		

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();

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
