package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.MarkTime;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

import java.util.logging.Logger;

public abstract class AutoBrowser {
	protected boolean headless = true;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Playwright playwright;
	private BrowserContext context;
	protected MarkTime markTime = new MarkTime();


	protected void launch() {
		LOGGER.info("Launching AutoBrowser on (headless=" + headless+ ")");
		playwright = Playwright.create(new CreateOptions().setEnv(PlaywrightSettings.getEnv()));
		LOGGER.info("Playwright created");
		context = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless).setArgs(PlaywrightSettings.getArguments())).newContext();
		LOGGER.info("Firefox launched");
	}

	protected Page newPage() {
		LOGGER.info("Creating new Context Page");
		if (playwright == null) {
			return null;
		}

		return context.newPage();

	}





	protected void close() {

		if (context != null) {
			context.close();
		}
		LOGGER.info("Closed Context");


		if (playwright != null) {
			playwright.close();
		}
		LOGGER.info("Closed Playwright");

	}

}
