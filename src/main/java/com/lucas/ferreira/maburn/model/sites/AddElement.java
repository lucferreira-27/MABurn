package com.lucas.ferreira.maburn.model.sites;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.logging.Logger;

public class AddElement {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	public boolean addScript(String content, Page page) {

		String script =
				"(content) =>{\n var head = document.getElementsByTagName('head')[0];\n"
						+ "		var script = document.createElement('script');\n"
						+ "		script.type = 'text/javascript';\n" + "		script.text = content\n"
						+ "		head.appendChild(script);\n}";
		String pageContent = page.content();

		if (!pageContent.contains("class Maburn")) {
			page.waitForLoadState(LoadState.LOAD);
			page.evaluate(script, content);
			Boolean result = (Boolean) page.evaluate("() => typeof execute === 'function'");
			if(!result) {
				try {
					Thread.sleep(2000);
					page.evaluate(script, content);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			LOGGER.config("Maburn script added in page");
			return true;
		}
		return false;

	}

}
