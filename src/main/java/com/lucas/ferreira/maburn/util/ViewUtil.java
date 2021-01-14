package com.lucas.ferreira.maburn.util;

public class ViewUtil {
	public static int getImagesGridPaneLastColumn(int total, int columnSizeMax) {

		if (total < columnSizeMax) {
			return total;
		}

		int c = 0;

		c = (total % columnSizeMax);

		return c;
	}

	public static int getImagesGridPaneLastRow(int total, int columnSizeMax) {
		int size = total;

		double rest = size / columnSizeMax;
		int r = (int) rest;

		return r;
	}
}
