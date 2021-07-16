package com.lucas.ferreira.maburn.view.navigator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownload;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadController;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class Navigator {

	private static Map<Interfaces, Initializable> mapNavigator = new HashMap<Interfaces, Initializable>();
	private static List<Interfaces> interfacesList = new ArrayList<Interfaces>();
	private static Map<Initializable, Node> mapNodesComponts = new HashMap<Initializable, Node>();

	private void loadComponent(Components component) {
		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		try {
			fxmlViewLoader.loadComponent(component);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void hideComponenet(Components component) {
		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();

		fxmlViewLoader.hideComponent(component);

	}

	public void open(Interfaces interfaces) {

		Initializable controller = interfaces.getController();
		String fxml = interfaces.getFxml();
		System.out.println("> OPENING: " + fxml + " | " + interfaces.name() + " MapNavigator: " + mapNavigator.size());

		mapNavigator.put(interfaces, interfaces.getController());
		interfacesList.add(interfaces);
		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.loadInterface(fxml, controller, true);
		fxmlViewLoader.loadProperty().addListener((obs, oldvalue, newvalue) ->{
			if(newvalue) {
				if(interfaces == Interfaces.TITLE_DOWNLOAD) {
						new TitleDownloadController((TitleDownload) interfaces.getController());
				}
			}
		});

		loadComponents(interfaces);
	}

	private void loadComponents(Interfaces interfaces) {
		if (interfaces == Interfaces.HOME) {
			hideComponenet(Components.MENU);
			return;
		}
		loadComponent(Components.MENU);
		if (interfaces == Interfaces.COLLECTION) {
			loadComponent(Components.COLLECTION_MENU);
		}
		if(interfaces == Interfaces.TITLE_DOWNLOAD) {
			//loadComponent(Components.DOWNLOAD_CARD);
		}

	}

	public void preload(Interfaces interfaces) {
		Initializable controller = interfaces.getController();
		String fxml = interfaces.getFxml();
		System.out.println("> PRELOAD: " + fxml + " | " + interfaces.name() + " MapNavigator: " + mapNavigator.size());

		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.loadInterface(fxml, controller, true);

		if (interfaces == Interfaces.HOME) {
			hideComponenet(Components.MENU);
		} else {
			loadComponent(Components.MENU);
		}

	}

	public void back() {

		Interfaces interfaceParent = getParent();
		Interfaces interfaceActual = getActual();
		interfacesList.remove(interfaceActual);
		get(interfaceParent);
	}

	public static Interfaces getLastInterface() {
		if (interfacesList.size() > 0)
			return interfacesList.get(interfacesList.size() - 2);
		else 
			return null;
	}

	public void get(Interfaces interfaces) {

		Initializable controller = mapNavigator.get(interfaces);
		String fxml = interfaces.getFxml();
		System.out.println("> OPENING: " + fxml + " | " + interfaces.name() + " MapNavigator: " + mapNavigator.size());

		FXMLViewLoader fxmlViewLoader = new FXMLViewLoader();
		fxmlViewLoader.loadInterface(fxml, controller, true);
		loadComponents(interfaces);

	}

	public static List<Interfaces> getInterfacesList() {
		return interfacesList;
	}

	public Interfaces getParent() {
		Interfaces interfaces = interfacesList.get(interfacesList.size() - 2);
		return interfaces;
	}

	public Interfaces getActual() {
		Interfaces interfaces = interfacesList.get(interfacesList.size() - 1);
		return interfaces;
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

	public static Map<Initializable, Node> getMapNodesComponts() {
		return mapNodesComponts;
	}
}
