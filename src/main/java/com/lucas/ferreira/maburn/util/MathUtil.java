package com.lucas.ferreira.maburn.util;

public class MathUtil {
	public static double roundDouble(double value, int places) {

		double m = Math.pow(10, places);

		return (double) Math.round(value * m) / m;
	}
}
