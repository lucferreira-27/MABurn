package com.lucas.ferreira.maburn.testing;

import com.lucas.ferreira.maburn.model.sites.FindResourcesSites;
import com.lucas.ferreira.maburn.util.ResourcesFile;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class FindLocalSitesTest {
	
	@Test
	public void testFind() throws Exception {
		FindResourcesSites findResourcesSites = new FindResourcesSites();
		try {
			Path file = findResourcesSites.findAll();
			assertTrue(file != null && Files.exists(file));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testFindAll() throws Exception {
		FindResourcesSites findResourcesSites = new FindResourcesSites();
		try {
			Path file = findResourcesSites.findAll();
			int length = ResourcesFile.listFiles(file).size();
			assertTrue(file != null && length > 0);
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
