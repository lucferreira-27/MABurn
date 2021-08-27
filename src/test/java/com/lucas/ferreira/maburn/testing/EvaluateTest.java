package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.model.sites.FindLocalSites;
import com.lucas.ferreira.maburn.model.sites.RegisterSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;

public class EvaluateTest {
	@Test
	public void testFindMainScript(){
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findMainScript();
		assertTrue(script != null && script.length() > 0);
	}
	@Test
	public void testFindScriptByName() {
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findScript("SAIKO_ANIMES", "saiko.js");
		assertTrue(script != null && script.length() > 0);

	}
	@Test
	public void testFindScriptByRegisteredSite() throws Exception {
		
		FindLocalSites findLocalSites = new FindLocalSites();
		Path path;

		path = findLocalSites.find("SAIKO_ANIMES");

		RegisterSite registerSite = new RegisterSite();

		RegisteredSite site = registerSite.register(path);
		
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findScript(site);
		assertTrue(script != null && script.length() > 0);

	}
}
