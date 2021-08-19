package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.model.sites.FindLocalSites;

public class FindLocalSitesTest {
	
	@Test
	public void testFind() {
		FindLocalSites findLocalSites = new FindLocalSites();
		try {
			File file = findLocalSites.find("SAIKO_ANIMES");
			assertTrue(file != null && file.exists());
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testFindAll() {
		FindLocalSites findLocalSites = new FindLocalSites();
		try {
			File[] files = findLocalSites.findAll();
			assertTrue(files != null && files.length > 0);
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
