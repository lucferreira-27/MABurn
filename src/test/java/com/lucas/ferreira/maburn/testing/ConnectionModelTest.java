package com.lucas.ferreira.maburn.testing;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.connection.ConnectionModel;

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
		ConnectionModel.connect(MYANIMELIST,3);
		ConnectionModel.connect(UNIONMANGAS,3);
		ConnectionModel.connect(MANGAHOST,3);
		ConnectionModel.connect(ANITUBE,3);
		ConnectionModel.connect(SAIKOANIMES,3);

	}
}
