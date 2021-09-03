package com.lucas.ferreira.maburn.application;

import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import java.util.logging.Logger;

public class Main {
	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void main(String[] args) {



		MainInterfaceView.getInstance().initAndShowGUI();
		LOGGER.info("GUI initialize");

		Navigator navigator = new Navigator();
		navigator.open(Interfaces.HOME);

	}

}
