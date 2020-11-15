package com.lucas.ferreira.maburn.model;

import java.util.ArrayList;

import com.lucas.ferreira.maburn.view.ViewInterface;

public class InterfaceStack {

	ArrayList<ViewInterface> stack = new ArrayList<ViewInterface>();

	public void add(ViewInterface viewInterface) {
		stack.add(viewInterface);
	}

	public ViewInterface get(int index) {
		return stack.get(index);
	}

}
