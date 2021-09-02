package com.lucas.ferreira.maburn.model.webscraping.navigate;

import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MyBrowser {
	private static final int MAX_BROWSER_PAGES = 1;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final List<BrowserPage> browsersPages = new ArrayList<>();

	private final boolean headless;

	public MyBrowser(boolean headless) {

		this.headless = headless;
	}

	public void createBrowserPage(int nPages) {

		if (nPages > MAX_BROWSER_PAGES) {
			nPages = MAX_BROWSER_PAGES;
		}

		for (int i = 0; i < nPages; i++) {
			LOGGER.config("Launching MyBrowser on (headless=" + headless+ ")");
			Playwright playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));
			BrowserContext browserContext = playwright.firefox().launch(
					new BrowserType.LaunchOptions().setHeadless(headless).setArgs(PlaywrightSettings.getArguments()))
					.newContext();
			LOGGER.config("Firefox launched");
			browsersPages.add(new BrowserPage(playwright, browserContext));

		}
	}

	public List<BrowserPage> getBrowsersPages() {
		return browsersPages;
	}

	public void killAll() {

		browsersPages.forEach(BrowserPage::killPlaywiright);
		LOGGER.config("Killed all BrowsersPages");

	}
}
