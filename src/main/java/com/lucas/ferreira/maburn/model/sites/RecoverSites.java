package com.lucas.ferreira.maburn.model.sites;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecoverSites {
	public List<RegisteredSite> recoverAll() throws Exception {
		FindLocalSites findLocalSites = new FindLocalSites();
		List<File> files = new ArrayList<>();

		files = Arrays.asList(findLocalSites.findAll());

		RegisterSite registerSite = new RegisterSite();

		List<RegisteredSite> sites = registerSite.registerAll(files);
		return sites;
	}
}
