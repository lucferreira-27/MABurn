package com.lucas.ferreira.maburn.model.states;

public class InterfaceState {
	private ControllerStateAdapter controllerStateAdapter;
	private ModalStateAdapter modalStateAdapter;

	public InterfaceState() {

	}

	public ControllerStateAdapter getControllerStateAdapter() {
		return controllerStateAdapter;
	}

	public void setControllerStateAdapter(ControllerStateAdapter controllerStateAdapter) {
		this.controllerStateAdapter = controllerStateAdapter;
	}

	public ModalStateAdapter getModalStateAdapter() {
		return modalStateAdapter;
	}

	public void setModalStateAdapter(ModalStateAdapter modalStateAdapter) {
		this.modalStateAdapter = modalStateAdapter;
	}
}
