package com.lucas.ferreira.maburn.application;

import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class Main {
	public static void main(String[] args) {	
		
		MainInterfaceView mainInterface = new MainInterfaceView();
		mainInterface.initAndShowGUI();
		HomeInterfaceView homeInterface = new HomeInterfaceView();
		homeInterface.loadMainInterfaceFX(mainInterface);
		
	
	}
}
