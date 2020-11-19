package com.lucas.ferreira.maburn.model.enums;

public enum Definition {
	
	DEFINITION_360(360), DEFINITION_480(480), DEFINITION_720(720), DEFINITION_1080(1080), DEFINITION_UNDEFINED(-1);

	private int size;

	private Definition(int size) {
		// TODO Auto-generated constructor stub
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
}
