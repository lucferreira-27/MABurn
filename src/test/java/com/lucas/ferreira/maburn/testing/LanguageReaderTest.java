package com.lucas.ferreira.maburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.util.LanguageReader;

public class LanguageReaderTest {
	
	@Test
	public void readerTest1() {
		String expect = "It looks like you don't have ${value} in the collection!";
		String result = LanguageReader.read("LABEL_SEARCH");

		assertThat(expect, is(result));
	}
	

}
