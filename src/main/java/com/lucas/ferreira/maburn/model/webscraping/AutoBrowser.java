package com.lucas.ferreira.maburn.model.webscraping;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.options.WaitUntilState;
import com.microsoft.playwright.Playwright;

public class AutoBrowser {
	private Playwright playwright;
	private BrowserContext context;

	
	public AutoBrowser(Playwright playwright) {
		// TODO Auto-generated constructor stub
		this.playwright = playwright;
	}
	
	protected void launch() {
		context = playwright.firefox().launch().newContext();
	}
	protected void launch(boolean headless) {
		context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)).newContext();
	}
	protected Page newPage() {
		if (playwright == null) {
			return null;
		}

		Page page = context.newPage();
		return page;

	}
	protected Page newPage(RulesProperties rulesProperties) {
		if (playwright == null) {
			return null;
		}
		
		NavigateOptions options = new NavigateOptions();
		options.setTimeout(rulesProperties.getTimeOut());
		options.setWaitUntil(WaitUntilState.DOMCONTENTLOADED);
		
		Page page = context.newPage();
		
		
		
		return page;

	}

	protected void close() {
		playwright.close();
	}

}
