package com.lucas.ferreira.maburn.model.sites;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import com.microsoft.playwright.Page;

public class Screenshot {
		private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public InputStream take(Page page, int attempts) {
		try {
			return takeScreenshot(page);
		} catch (RuntimeException e) {
			try {
				return retry(page, attempts);
			} catch (RuntimeException e2) {
				LOGGER.warning("[SCREENSHOT FAILED] \n ERRO: " + e.getMessage());
				return null;
			}
		}
	}

	private InputStream retry(Page page, int attempts) throws RuntimeException {
		for (int i = 0; i < attempts; i++) {
			try {
				LOGGER.warning("[SCREENSHOT FAILED] \n TRYING AGAIN ....");
				return takeScreenshot(page);
			} catch (RuntimeException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
		}
		return null;
	}

	private InputStream takeScreenshot(Page page) {
		byte[] bytes = page.screenshot();

		LOGGER.info("[SCREENSHOT TAKED]");
		InputStream is = new ByteArrayInputStream(bytes);
		return is;

	}
}
