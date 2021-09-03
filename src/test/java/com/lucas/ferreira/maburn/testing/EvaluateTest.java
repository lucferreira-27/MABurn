package com.lucas.ferreira.maburn.testing;

import com.lucas.ferreira.maburn.model.sites.FindResourcesSites;
import com.lucas.ferreira.maburn.model.sites.RegisterSite;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.Evaluate;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

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
		
		FindResourcesSites findResourcesSites = new FindResourcesSites();
		Path path;

		path = findResourcesSites.findAll();

		RegisterSite registerSite = new RegisterSite();

		RegisteredSite site = registerSite.register(path);
		
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findScript(site);
		assertTrue(script != null && script.length() > 0);

	}
}
