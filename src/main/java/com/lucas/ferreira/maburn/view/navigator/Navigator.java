package com.lucas.ferreira.maburn.view.navigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class Navigator {

	private static Map<Interfaces, Initializable> mapNavigator = new HashMap<Interfaces, Initializable>();
	private static List<Interfaces> interfacesList = new ArrayList<Interfaces>();
	private static Map<Initializable, Pane> mapRoot = new HashMap<Initializable, Pane>();

	// **DEFAULT MENU ALL INTERFACES CONTAINS**
	private void addMenu() {
		Initializable menuController = Components.MENU.getController();
		String fxml = Components.MENU.getFxml();
		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.loadComponent(fxml, menuController, 0);
	}

	public void open(Interfaces interfaces) {

		Initializable controller = interfaces.getController();
		String fxml = interfaces.getFxml();
		System.out.println("> OPENING: " + fxml + " | " + interfaces.name() + " MapNavigator: " + mapNavigator.size());

		mapNavigator.put(interfaces, interfaces.getController());
		interfacesList.add(interfaces);
		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.loadInterface(fxml, controller);

		addAllComponents();
	}

	public void back() {

		Interfaces interfaceParent = getParent();
		Interfaces interfaceActual = getActual();
		interfacesList.remove(interfaceActual);
		get(interfaceParent);
	}

	public void get(Interfaces interfaces) {

		Initializable controller = mapNavigator.get(interfaces);
		String fxml = interfaces.getFxml();
		System.out.println("> OPENING: " + fxml + " | " + interfaces.name() + " MapNavigator: " + mapNavigator.size());

		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.loadInterface(fxml, controller);

		addAllComponents();
	}

	public Interfaces getParent() {
		Interfaces interfaces = interfacesList.get(interfacesList.size() - 2);
		return interfaces;
	}

	public Interfaces getActual() {
		Interfaces interfaces = interfacesList.get(interfacesList.size() - 1);
		return interfaces;
	}

	private void addAllComponents() {
		addMenu();
	}

//	public static void preLoadInterfaces() {
//		for (Interfaces interfaces : Interfaces.values()) {
//			if (interfaces.getController() != null) {
//				System.out.println(interfaces.getFxml());
//
//			}
//		}
//	}

	public static Map<Interfaces, Initializable> getMapNavigator() {
		return mapNavigator;
	}

	public static Map<Initializable, Pane> getMapRoot() {
		return mapRoot;
	}

}