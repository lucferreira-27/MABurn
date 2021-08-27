package com.lucas.ferreira.maburn.testing;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.sites.FindLocalSites;
import com.lucas.ferreira.maburn.model.sites.RegisterSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;

public class RegisterSiteTest {

	@Test
	public void testRegisterSite() throws Exception {
		try {
			FindLocalSites findLocalSites = new FindLocalSites();
			Path path;

			path = findLocalSites.find("SAIKO_ANIMES");

			RegisterSite registerSite = new RegisterSite();

			RegisteredSite site = registerSite.register(path);
			System.out.println(site.getSiteConfig());
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterAllSites() throws Exception {
		try {
			FindLocalSites findLocalSites = new FindLocalSites();
			List<Path> files = new ArrayList<>();

			files = Arrays.asList(findLocalSites.findAll());

			RegisterSite registerSite = new RegisterSite();

			List<RegisteredSite> sites = registerSite.registerAll(files);
			System.out.println(sites);
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
