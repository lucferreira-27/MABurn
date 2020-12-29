package com.lucas.ferreira.maburn.model.enums;

public enum DownloadUnit {
	MEGABYTES_UNIT("MB"), KILOBYTES_UNIT("KB"), GIGABYTES_UNIT("GB"),
	MEGABITS_UNIT("Mb"), KILOBITS_UNIT("Kb"), GIGABITS_UNIT("Gb");
	
	
	private String value;
	
	private DownloadUnit(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
