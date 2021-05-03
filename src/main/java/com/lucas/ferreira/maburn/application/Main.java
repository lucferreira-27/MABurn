package com.lucas.ferreira.maburn.application;

import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

public class Main {
	public static void main(String[] args) {

		MainInterfaceView.getInstance().initAndShowGUI();
		
		Navigator navigator = new Navigator();
		navigator.open(Interfaces.HOME);
		

	}

}
