package com.lucas.ferreira.maburn.application;

import com.lucas.ferreira.maburn.view.HomeInterfaceView;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class Main {
	public static void main(String[] args) {

		// StackDumper.dumpWhenSysOutContains("");

		MainInterfaceView.getInstance().initAndShowGUI();
		HomeInterfaceView homeInterface = new HomeInterfaceView();
		homeInterface.loadMainInterfaceFX();

	}
}
