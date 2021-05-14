package com.lucas.ferreira.maburn.application;

import java.io.IOException;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.RulesProperties;
import com.lucas.ferreira.maburn.model.webscraping.ScrapingRuler;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.MainInterfaceView;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

public class Main {
	public static void main(String[] args) {
		ScrapingRuler ruler = new ScrapingRuler();
		try {
			RulesProperties rulesProperties =	ruler.readProperties(Sites.ANITUBE);
			System.out.println(rulesProperties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		MainInterfaceView.getInstance().initAndShowGUI();
//		
//		Navigator navigator = new Navigator();
//		navigator.open(Interfaces.HOME);

	}

}
