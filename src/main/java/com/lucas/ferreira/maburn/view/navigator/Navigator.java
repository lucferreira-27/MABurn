package com.lucas.ferreira.maburn.view.navigator;

import com.lucas.ferreira.maburn.controller.download.DownloadQueueController;
import com.lucas.ferreira.maburn.controller.download.DownloadQueueModal;
import com.lucas.ferreira.maburn.controller.settings.SettingsController;
import com.lucas.ferreira.maburn.controller.settings.SettingsModel;
import com.lucas.ferreira.maburn.model.states.InterfaceState;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.FXMLViewLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Navigator {

	private static Map<Interfaces, Initializable> mapNavigator = new HashMap<>();
	private static List<Interfaces> interfacesList = new ArrayList<>();
	private static Map<Initializable, Node> mapNodesComponts = new HashMap<>();
	private FXMLViewLoader<Node> fxmlViewLoader = new FXMLViewLoader<>();

	private void loadComponent(Components component) {
		try {
			fxmlViewLoader.loadComponent(component);
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hideComponents(Components component) {

		fxmlViewLoader.hideComponent(component);

	}

	public void open(Interfaces interfaces) {
		try {
			FXMLViewLoader<Node> openFxmlViewLoader = new FXMLViewLoader<Node>();

			Initializable controller = interfaces.getFinalModalInterface();
			String fxml = interfaces.getFxml();

			mapNavigator.put(interfaces, controller);
			interfacesList.add(interfaces);
			LoadInterface loadInterface = new LoadInterface();
			loadInterface.setFxml(fxml);
			loadInterface.setInitializable(controller);
			openFxmlViewLoader.loadInterface(loadInterface);
			openFxmlViewLoader.loadProperty().addListener((obs, oldvalue, newvalue) -> {
				try {

					if (newvalue) {
						if (interfaces == Interfaces.CONFIGURATION) {
							new SettingsController((SettingsModel) controller).initialize();
						}
						else if (interfaces == Interfaces.DOWNLOADS) {
							new DownloadQueueController((DownloadQueueModal) controller).initialize();
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			});

			loadComponents(interfaces);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openFromRegisteredState(Interfaces interfaces, String id) throws IOException {
		try {
			mapNavigator.put(interfaces, interfaces.getNewModalInterface());
			interfacesList.add(interfaces);

			recoverStateAndOpen(interfaces, id);

			loadComponents(interfaces);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void recoverStateAndOpen(Interfaces interfaces, String id) {
		try {

			LoadInterface loadInterface = loadInterface(interfaces);

			RecoverInterface recoverInterface =new RecoverInterface();
			loadInterface.setInterfaces(interfaces);

			recoverInterface.recoverState(loadInterface, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private LoadInterface loadInterface(Interfaces interfaces) throws Exception {
		
		Class<?> c = Class.forName(interfaces.getNewModalInterface().getClass().getName());
		
		Initializable controller = (Initializable) c.newInstance();
		String fxml = interfaces.getFxml();
		LoadInterface loadInterface = new LoadInterface();
		
		InterfaceState interfaceState = new InterfaceState();
		loadInterface.setFxml(fxml);
		loadInterface.setInitializable(controller);
		loadInterface.setInterfaces(interfaces);
		
		return loadInterface;
	}


	private void loadComponents(Interfaces interfaces) {
		if (interfaces == Interfaces.HOME) {
			hideComponents(Components.MENU);
			return;
		}
		loadComponent(Components.MENU);
		if (interfaces == Interfaces.COLLECTION) {
			loadComponent(Components.COLLECTION_MENU);
		}


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

		LoadInterface loadInterface = new LoadInterface();
		loadInterface.setFxml(fxml);
		loadInterface.setInitializable(controller);
		fxmlViewLoader.loadInterface(loadInterface);
		loadComponents(interfaces);

	}

	public static List<Interfaces> getInterfacesList() {
		return interfacesList;
	}

	public Interfaces getParent() {
		return interfacesList.get(interfacesList.size() - 2);
	}

	public Interfaces getActual() {
		return interfacesList.get(interfacesList.size() - 1);
	}

	public static Map<Interfaces, Initializable> getMapNavigator() {
		return mapNavigator;
	}

	public static Map<Initializable, Node> getMapNodesComponents() {
		return mapNodesComponts;
	}
}
