package com.lucas.ferreira.maburn.model.webscraping;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserPage {
	
	private Playwright playwright;
	private Page page;
	private BrowserContext context;
	private boolean available = true;
	private boolean alive = false;
	
	public BrowserPage(Playwright playwright) {
		// TODO Auto-generated constructor stub
		this.playwright = playwright;
		alive = true;
	}
	
	public BrowserPage(Page page) {
		// TODO Auto-generated constructor stub
		this.page = page;
		alive = true;
	}
	public BrowserPage(BrowserContext context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		alive = true;
	}
	public Playwright getPlaywright() {
		if(isAlive()) {
			return playwright;
		}
		return null;
	}
	public Page getPage() {
		return page;
	}
	
	public BrowserContext getContext() {
		return context;
	}
	public void killPlaywiright() {
		alive = false;
		playwright.close();
		playwright = null;
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
}
