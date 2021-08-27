package com.lucas.ferreira.maburn.application;

import java.io.IOException;

import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

public class Main {
	public static void main(String[] args) throws IOException {

		MainInterfaceView.getInstance().initAndShowGUI();
	


		Navigator navigator = new Navigator();
		navigator.open(Interfaces.HOME);

	}

}
