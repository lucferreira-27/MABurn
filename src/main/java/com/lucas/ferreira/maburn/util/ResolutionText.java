package com.lucas.ferreira.maburn.util;

public class ResolutionText {
	public static String shortText(String longResolution) {
		return longResolution.replaceAll("\\d", "") + "p";
	}
}
