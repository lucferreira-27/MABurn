package com.lucas.ferreira.maburn.model;

import java.util.Comparator;
import java.util.regex.Pattern;

import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class ItemFileComparator implements Comparator<CollectionSubItem> {

	@Override
	public int compare(CollectionSubItem o1, CollectionSubItem o2) {
		// TODO Auto-generated method stub

		String dest1 = o1.getDestination();
		String dest2 = o2.getDestination();

		dest1 = dest1.substring(dest1.lastIndexOf(" ")).trim();
		dest2 = dest2.substring(dest2.lastIndexOf(" ")).trim();

		if (dest1.contains(".") && dest2.contains(".")) {
			dest1 = dest1.substring(0, dest1.lastIndexOf("."));

			dest2 = dest2.substring(0, dest2.lastIndexOf("."));
			int x = Integer.parseInt(dest1);
			int y = Integer.parseInt(dest2);

			return Integer.compare(x, y);
		} else if (dest1.matches("^[0-9]*$") && dest2.matches("^[0-9]*$")) {

			int x = Integer.parseInt(dest1);
			int y = Integer.parseInt(dest2);

			return Integer.compare(x, y);
		} else {
			double x = 0;
			double y = 0;

			if (Pattern.compile("[^\\d - .]").matcher(dest1).find()) { // . -> TO FIX
				dest1 = dest1.replaceAll("[^\\d - .]", "");
				if (!dest1.isEmpty())

					x = Double.parseDouble(dest1) + 999999;
			} else {
				dest1 = dest1.replaceAll("[^\\d - .]", "");
				if (!dest1.isEmpty())

					x = Double.parseDouble(dest1);

			}

			if (Pattern.compile("[^\\d - .]").matcher(dest2).find()) {
				dest2 = dest2.replaceAll("[^\\d - .]", "");
				if (!dest2.isEmpty())
					y = Double.parseDouble(dest2) + 999999;
			} else {
				dest2 = dest2.replaceAll("[^\\d - .]", "");
				if (!dest2.isEmpty())
					y = Double.parseDouble(dest2);

			}

			return Double.compare(x, y);

		}

	}

}
