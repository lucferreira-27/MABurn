package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.MarkTime;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

public abstract class AutoBrowser {
	private Playwright playwright;
	private BrowserContext context;
	protected MarkTime markTime = new MarkTime();

	public AutoBrowser() {

	}



	protected void launch(boolean headless) {
		playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));

		context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless).setArgs(PlaywrightSettings.getArguments())).newContext();
	}

	protected Page newPage() {
		if (playwright == null) {
			return null;
		}
		Page page = context.newPage();

		return page;

	}





	protected void close() {

		if (context != null) {
			context.close();
		}

		if (playwright != null) {
			playwright.close();
		}
	}

}
