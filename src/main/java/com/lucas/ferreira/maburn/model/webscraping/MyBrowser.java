package com.lucas.ferreira.maburn.model.webscraping;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class MyBrowser {
	private static final int MAX_BROWSER_PAGES = 3;

	private List<BrowserPage> browsersPages = new ArrayList<>();

	public void createBrowserPage(int nPages) {

		if (nPages > MAX_BROWSER_PAGES) {
			nPages = MAX_BROWSER_PAGES;
		}

		for (int i = 0; i < nPages; i++) {
			Playwright playwright = Playwright.create();
			BrowserContext browserContext = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext();
			// browsersPages.add(new BrowserPage(playwright));
			browsersPages.add(new BrowserPage(browserContext));

		}
	}

	public List<BrowserPage> getBrowsersPages() {
		return browsersPages;
	}

	public void killAll() {
		browsersPages.forEach(bp -> bp.killPlaywiright());
		browsersPages.clear();

	}
}
