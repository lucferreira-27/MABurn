package com.lucas.ferreira.maburn.model.enums;

public enum DefaultNullValue {
	STRING_NULL("-----");

	private String value;

	private DefaultNullValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
