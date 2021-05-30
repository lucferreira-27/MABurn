package com.lucas.ferreira.maburn.controller.title.download.controllers;

import java.util.List;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;

import javafx.scene.Node;

public interface Controllers {

	public List<Node> getChildren();

	public FetchItemType getFetchItemType();

	public void setVisible(boolean visible);

	boolean getVisible();
}
