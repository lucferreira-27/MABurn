package com.lucas.ferreira.maburn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lucas.ferreira.maburn.model.enums.Category;

public class ParseItemsNames {
	private static final String FLAG = "\\[\\d+\\].*";

	public String parse(String str, Category category, int max) {

		String zeroChain = getZeroChain(max);

		if (str.matches(FLAG)) {
			Matcher m = Pattern.compile("\\[([\\d]+)\\]").matcher(str);
			String number = "unknow";

			if (m.find()) {
				number = m.group(1);
			}

			if (number.contains("unknow")) {
				return number;
			}

			int intNumber = Integer.valueOf(number);
			String result = "";
			if (intNumber < 10) {

				number = zeroChain += number;
				System.out.println(number);
			}
			 result = replaceFlag(str, category == Category.ANIME ? "Episode " + number : "Chapter " + number);

			return result;
		}

		return null;

	}

	private String replaceFlag(String str, String newString) {
		str = str.replaceFirst(FLAG, newString);

		return str;
	}

	private String getZeroChain(int max) {

		String str = String.valueOf(max / 10);
		Matcher m = Pattern.compile("\\d").matcher(str);
		int number = 0;
		while (m.find()) {
			number++;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < number; i++) {
			stringBuilder.append("0");
		}
		return stringBuilder.toString();
	}
}
