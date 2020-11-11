package com.lucas.ferreira.mangaburn.testing;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.ConnectionModel;

public class ConnectionModelTest {
	public static final String MYANIMELIST = "https://myanimelist.net/";
	public static final String UNIONMANGAS = "https://unionmangas.top/ayz";
	public static final String MANGAHOST = "https://mangahost2.com/";
	public static final String ANITUBE = "https://www.anitube.site/";
	public static final String SAIKOANIMES = "https://saikoanimes.net/";



	@Before
	public void setUp() {
		
	}
	@Test
	public void connectTest() {
		
		ConnectionModel.connect(MYANIMELIST);
		ConnectionModel.connect(UNIONMANGAS);
		ConnectionModel.connect(MANGAHOST);
		ConnectionModel.connect(ANITUBE);
		ConnectionModel.connect(SAIKOANIMES);

		

	}
	@Test
	public void connectTimeOutTest() {
		ConnectionModel.connect(MYANIMELIST,10000);
		ConnectionModel.connect(UNIONMANGAS,10000);
		ConnectionModel.connect(MANGAHOST,10000);
		ConnectionModel.connect(ANITUBE,10000);
		ConnectionModel.connect(SAIKOANIMES,10000);

	}
}
