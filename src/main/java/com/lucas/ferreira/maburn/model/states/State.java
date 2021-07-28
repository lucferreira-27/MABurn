package com.lucas.ferreira.maburn.model.states;

import javafx.scene.Node;

public class State implements ModalStateAdapter{

	private static final long serialVersionUID = 747344881192713286L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
