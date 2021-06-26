package com.lucas.ferreira.maburn.util;

public class ResolutionText {
	public static String shortText(String longResolution) {
		if (longResolution == null || longResolution.isEmpty()) {
			return "----";
		}
		return longResolution.replaceAll("\\D", "") + "p";
	}
}
