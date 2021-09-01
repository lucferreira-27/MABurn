package com.lucas.ferreira.maburn.testing;

import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.ParseItemsNames;

public class ParseItemsNamesTest {
	@Test
	public void parseTest() {
		try {
			ParseItemsNames parseItemsNames = new ParseItemsNames();
			String r = parseItemsNames.parse("[1]", Category.ANIME, 1012);
			String expect = "Episode 1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
