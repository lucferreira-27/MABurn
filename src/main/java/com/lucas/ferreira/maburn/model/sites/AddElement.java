package com.lucas.ferreira.maburn.model.sites;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class AddElement {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println("Maburn script added in page");
			
			return true;
		}
		return false;

	}

}
