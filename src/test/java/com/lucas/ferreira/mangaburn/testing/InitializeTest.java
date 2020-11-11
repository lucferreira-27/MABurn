package com.lucas.ferreira.mangaburn.testing;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.view.View;

public class InitializeTest {
	private InitializeModel initializeModel;
	private View view;

	@Before
	public void setUp() {
		//view = new CommandLineView();
		initializeModel = new InitializeModel(view);
	}
	@Test
	public void bootTest() {
		initializeModel.boot();
	}
}
