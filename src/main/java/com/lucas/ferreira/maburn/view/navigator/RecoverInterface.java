package com.lucas.ferreira.maburn.view.navigator;

import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadController;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadModel;
import com.lucas.ferreira.maburn.model.states.ControllerStateAdapter;
import com.lucas.ferreira.maburn.model.states.InterfaceState;
import com.lucas.ferreira.maburn.model.states.ModalStateAdapter;
import com.lucas.ferreira.maburn.model.states.ObjectState;
import com.lucas.ferreira.maburn.model.states.RegisteredStates;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.fxml.FXMLViewLoader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class RecoverInterface {

	FXMLViewLoader<Node> fxmlViewLoader = new FXMLViewLoader<Node>();

	
	
	
	
	private InterfaceState interfaceState = new InterfaceState();

	public void recoverState(LoadInterface loadInterface, String id) {
		try {



			if (RegisteredStates.containsState(id)) {
				loadInterface = recover(loadInterface, id);
			} else {
				loadInterface.setObjectState(ObjectState.NEW_OBJECT);
			}
			
			fxmlViewLoader.loadProperty().addListener(onInterfaceLoaded(loadInterface,id));
			fxmlViewLoader.loadInterface(loadInterface);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LoadInterface recover(LoadInterface loadInterface, String id) {
		interfaceState = RegisteredStates.recoverState(id);
		loadInterface.setObjectState(ObjectState.OLD_OBJECT);
		loadInterface.setOldNode(interfaceState.getModalStateAdapter().getRoot());
		return loadInterface;
	}

	private ChangeListener<? super Boolean> onInterfaceLoaded(LoadInterface loadInterface, String id) {

		return new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					try {
						if (loadInterface.getInterfaces() == Interfaces.TITLE_DOWNLOAD) {

							if (!RegisteredStates.containsState(id)) {
								InterfaceState interfaceState = loadContollerAndModal(loadInterface);
								saveInterfaceState(interfaceState, id);

								interfaceState.getControllerStateAdapter().initialize();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			private InterfaceState loadContollerAndModal(LoadInterface loadInterface) {
				InterfaceState recoverInterfaceState = new InterfaceState();

				ControllerStateAdapter controller = new TitleDownloadController( (TitleDownloadModel) loadInterface.getInitializable());
				ModalStateAdapter modal =  (TitleDownloadModel) loadInterface.getInitializable();
				
				recoverInterfaceState.setControllerStateAdapter(controller);
				recoverInterfaceState.setModalStateAdapter(modal);
			
				return recoverInterfaceState;
			}
		};

	}

	private void saveInterfaceState(InterfaceState recoverInterfaceState, String id) {
		RegisteredStates.saveState(recoverInterfaceState, id);

	}

}
