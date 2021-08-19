package com.lucas.ferreira.maburn.model.sites;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ClickOptions;
import com.microsoft.playwright.PlaywrightException;

public class WaitClick {

	private Page page;

	public WaitClick(Page page) {
		this.page = page;
	}

	public void click(String selector) {
		System.out.println(selector);


		try {
			page.click(selector, new ClickOptions().setForce(true));
		} catch (PlaywrightException e) {
			if(e.getMessage().contains("Element is not visible"))
			System.err.println("[PlaywrightException] Error Element is not visible");
		}
	}

}
