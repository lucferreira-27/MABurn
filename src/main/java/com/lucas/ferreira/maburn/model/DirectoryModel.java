package com.lucas.ferreira.maburn.model;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class DirectoryModel {
	
	
	public static void openDirectory(String diretory) throws IOException {
		Desktop.getDesktop().open(new File(diretory));

	}
	
	public static File selectDirectory(Window window) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		return directoryChooser.showDialog(window);

	}
}
