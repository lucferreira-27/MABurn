package com.lucas.ferreira.maburn.model.webscraping;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lucas.ferreira.maburn.util.StringUtil;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ScreenshotType;

import javafx.scene.image.Image;

public abstract class AutoBrowser {
	private Playwright playwright;
	private BrowserContext context;

	public AutoBrowser() {
		// TODO Auto-generated constructor stub

	}

	protected void launch() {
		playwright = Playwright.create();
		context = playwright.firefox().launch().newContext();
	}

	protected void launch(boolean headless) {
		playwright = Playwright.create();

		context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)).newContext();
	}

	protected Page newPage() {
		if (playwright == null) {
			return null;
		}

		Page page = context.newPage();

		return page;

	}

	protected Image screenshot(Page page) {
		byte[] buffer = page.screenshot();

		return new Image(new ByteArrayInputStream(buffer));
	}

	protected PageInfo fillPageInfo(Page page) {
		String title = page.title();
		String screenshotPath = System.getProperty("java.io.tmpdir") + StringUtil.stringUtilFile(title) + ".png";
		Image image = screenshot(page);
		System.out.println(screenshotPath);
		// page.screenshot(new ScreenshotOptions().setPath(Paths.get(screenshotPath)));
		PageInfo pageInfo = new PageInfo(title, image);
		return pageInfo;
	}

	protected void close() {
		if (playwright != null) {
			context.close();
			playwright.close();
		}
	}

}
