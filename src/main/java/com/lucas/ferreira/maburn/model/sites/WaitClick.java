package com.lucas.ferreira.maburn.model.sites;

import java.util.logging.Logger;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ClickOptions;
import com.microsoft.playwright.PlaywrightException;

public class WaitClick {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Page page;

	public WaitClick(Page page) {
		this.page = page;
	}

	public void click(String selector) {
		LOGGER.config("Click on [" + selector +"]");
	
		try {
			page.click(selector, new ClickOptions().setForce(true));
		} catch (PlaywrightException e) {
			if(e.getMessage().contains("Element is not visible"))
			LOGGER.warning("[PlaywrightException] Error Element is not visible");
		}
	}

}
