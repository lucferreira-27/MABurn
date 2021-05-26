package com.lucas.ferreira.maburn.model.enums;

public enum FetchItemType {
	SINGLE("Download Single"), 
	ALL("Download All"), 
	UPDATE("Update"), 
	BETWEEN("Download Between");
	
	
	private String name;
	
	FetchItemType(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
