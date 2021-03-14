package com.lucas.ferreira.maburn.application;

import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Main {
	public static void main(String[] args) {

		// StackDumper.dumpWhenSysOutContains("");
		

		MainInterfaceView.getInstance().initAndShowGUI();
		HomeInterfaceView homeInterface = new HomeInterfaceView();
		homeInterface.loadMainInterfaceFX();
		

	}

	
}
