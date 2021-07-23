package com.lucas.ferreira.maburn.model.browser;

import java.io.ByteArrayInputStream;

import com.lucas.ferreira.maburn.model.MarkTime;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.PageInfo;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;
import com.lucas.ferreira.maburn.util.StringUtil;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

import javafx.scene.image.Image;

public abstract class AutoBrowser {
	private Playwright playwright;
	private BrowserContext context;
	protected MarkTime markTime = new MarkTime();

	public AutoBrowser() {

	}

	protected void launch() {
		System.out.println("LAUNCHING FIREFOX");
		playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));
		context = playwright.firefox().launch().newContext();
	}

	protected void launch(boolean headless) {
		System.out.println("LAUNCHING FIREFOX HEADLESS");
		playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));

		context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)).newContext();
	}

	protected Page newPage() {
		if (playwright == null) {
			return null;
		}
		System.out.println("NEW PAGE");
		Page page = context.newPage();

		return page;

	}

	private Image screenshot(Page page) {
		byte[] buffer = page.screenshot();

		return new Image(new ByteArrayInputStream(buffer));
	}

	private Image screenshotFull(Page page) {
		byte[] buffer = page.screenshot(new ScreenshotOptions().setFullPage(true));

		return new Image(new ByteArrayInputStream(buffer));
	}

	protected PageInfo fillPageInfo(Page page) {
		Long time;
		try {
			time = markTime.end();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = page.title();
		String screenshotPath = System.getProperty("java.io.tmpdir") + StringUtil.stringUtilFile(title) + ".png";
		Image imageSmall = screenshot(page);
		Image imageFull = screenshotFull(page);

		// page.screenshot(new ScreenshotOptions().setPath(Paths.get(screenshotPath)));
		PageInfo pageInfo = new PageInfo(imageSmall, imageFull);
		pageInfo.setTitle(page.title());
		pageInfo.setUrl(page.url());

		return pageInfo;
	}

	protected PageInfo fillMorePageInfo(PageInfo pageInfo,TitleScraped titleScraped) {
		Sites site = titleScraped.getSite();
		Long time = null;
		try {
			time = markTime.end();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageInfo.setTotalItems(titleScraped.getItemsScraped().size());
		pageInfo.setTime(time);
		pageInfo.setSite(site);
		return pageInfo;
	}

	protected void close() {

		if (context != null) {
			context.close();
		}

		if (playwright != null) {
			playwright.close();
		}
	}

}
