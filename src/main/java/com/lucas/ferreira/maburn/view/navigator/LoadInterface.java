package com.lucas.ferreira.maburn.view.navigator;

import com.lucas.ferreira.maburn.model.states.ObjectState;
import com.lucas.ferreira.maburn.view.Interfaces;

import javafx.fxml.Initializable;
import javafx.scene.Node;

public class LoadInterface {
	private String fxml;
	private Initializable initializable;
	private boolean visibility = true;
	private ObjectState objectState = ObjectState.NEW_OBJECT;
	private Node oldNode;
	private Interfaces interfaces;
	
	public String getFxml() {
		return fxml;
	}
	public void setFxml(String fxml) {
		this.fxml = fxml;
	}
	public Initializable getInitializable() {
		return initializable;
	}
	public void setInitializable(Initializable initializable) {
		this.initializable = initializable;
	}
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	public ObjectState getObjectState() {
		return objectState;
	}
	public void setObjectState(ObjectState objectState) {
		this.objectState = objectState;
	}
	public void setOldNode(Node oldNode) {
		this.oldNode = oldNode;
	}
	public Node getOldNode() {
		return oldNode;
	}
	public Interfaces getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(Interfaces interfaces) {
		this.interfaces = interfaces;
	}
	
	
	
	
}
