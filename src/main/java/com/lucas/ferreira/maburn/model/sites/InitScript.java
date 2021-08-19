package com.lucas.ferreira.maburn.model.sites;

import java.io.FileNotFoundException;

import com.lucas.ferreira.maburn.model.webscraping.Evaluate;

public class InitScript {
	public String script;

	public InitScript() {
	}

	public String init(RegisteredSite registeredSite) throws FileNotFoundException {
		Evaluate evaluate = new Evaluate();
		String script = evaluate.findMainScript() + "\n" + evaluate.findScript(registeredSite);
		return script;
	}
}