package com.lucas.ferreira.maburn.model.webscraping.navigate;

import java.util.ArrayList;
import java.util.List;

import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

public class MyBrowser {
	private static final int MAX_BROWSER_PAGES = 1;

	private List<BrowserPage> browsersPages = new ArrayList<>();
	
	private boolean headless = true;
	
	public MyBrowser(boolean headless) {
		
		this.headless = headless;
	}
	
	
	public void createBrowserPage(int nPages) {

		if (nPages > MAX_BROWSER_PAGES) {
			nPages = MAX_BROWSER_PAGES;
		}

		for (int i = 0; i < nPages; i++) {
			Playwright playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));
			BrowserContext browserContext = playwright.firefox()
					.launch(new BrowserType.LaunchOptions().setHeadless(headless)).newContext();
			// browsersPages.add(new BrowserPage(playwright));
			browsersPages.add(new BrowserPage(playwright, browserContext));

		}
	}

	public List<BrowserPage> getBrowsersPages() {
		return browsersPages;
	}

	public void killAll() {

		browsersPages.forEach(bp -> 
		{
			bp.killPlaywiright();
		});
	//	browsersPages.clear();

	}
}
