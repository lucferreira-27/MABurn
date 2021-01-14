package com.lucas.ferreira.mangaburn.testing;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.view.View;

public class InitializeTest {
	private InitializeModel initializeModel;

	@Before
	public void setUp() {
		//view = new CommandLineView();
		initializeModel = new InitializeModel();
	}
	@Test
	public void bootTest() {
		initializeModel.boot();
	}
}
