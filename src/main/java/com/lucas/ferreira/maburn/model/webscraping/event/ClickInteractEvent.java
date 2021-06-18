package com.lucas.ferreira.maburn.model.webscraping.event;

import com.microsoft.playwright.Page;

public class ClickInteractEvent implements InteractEvent {

	private long waitTime;
	private Page page;
	
	public ClickInteractEvent(Page page) {
		this.page = page;
	}
	
	@Override
	public void event(String selector) {
		page.click(selector);
	}
	
	public long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
	
	
}
