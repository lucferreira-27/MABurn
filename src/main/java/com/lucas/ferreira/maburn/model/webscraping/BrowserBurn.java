package com.lucas.ferreira.maburn.model.webscraping;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserBurn {
	private static Browser browser;
	private static Playwright playwright;
	public static void create(BrowserType browserType) {
		playwright = Playwright.create();
		browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(true));
	}
	public static void stop() {
		
		playwright.close();
	}
	public static Browser getBrowser() {
		return browser;
	}
}
