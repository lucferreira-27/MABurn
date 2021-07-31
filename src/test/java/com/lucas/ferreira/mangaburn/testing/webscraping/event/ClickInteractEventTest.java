package com.lucas.ferreira.mangaburn.testing.webscraping.event;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.webscraping.event.ClickInteractEvent;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;


public class ClickInteractEventTest {
	
	Playwright playwright = Playwright.create();
	
	
	@Test
	public void testClick() {
		BrowserContext context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext();
		Page page = context.newPage();
		page.navigate("https://saikoanimes.net/");
		ClickInteractEvent clickInteractEvent = new ClickInteractEvent(page);
		clickInteractEvent.event("#content");
	}

}
