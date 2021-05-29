package com.lucas.ferreira.maburn.model.webscraping.browser;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class MyBrowser {
	private static final int MAX_BROWSER_PAGES = 1;

	private List<BrowserPage> browsersPages = new ArrayList<>();
	
	private boolean headless = true;
	
	public MyBrowser(boolean headless) {
		// TODO Auto-generated constructor stub
		this.headless = headless;
	}
	
	
	public void createBrowserPage(int nPages) {

		if (nPages > MAX_BROWSER_PAGES) {
			nPages = MAX_BROWSER_PAGES;
		}

		for (int i = 0; i < nPages; i++) {
			Playwright playwright = Playwright.create();
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
