package com.lucas.ferreira.maburn.testing;

import com.lucas.ferreira.maburn.model.sites.FindResourcesSites;
import com.lucas.ferreira.maburn.model.sites.RegisterSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class RegisterSiteTest {

	@Test
	public void testRegisterSite() throws Exception {
		try {
			FindResourcesSites findResourcesSites = new FindResourcesSites();
			Path path;

			path = findResourcesSites.findAll();

			RegisterSite registerSite = new RegisterSite();

			RegisteredSite site = registerSite.register(path);


			
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterAllSites() throws Exception {
		try {
			FindResourcesSites findResourcesSites = new FindResourcesSites();
			List<Path> files;

			files = Collections.singletonList(findResourcesSites.findAll());

			RegisterSite registerSite = new RegisterSite();

			List<RegisteredSite> sites = registerSite.registerAll(files);
			System.out.println(sites);


			
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
