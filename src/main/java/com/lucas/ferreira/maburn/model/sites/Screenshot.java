package com.lucas.ferreira.maburn.model.sites;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.microsoft.playwright.Page;

public class Screenshot {
	public InputStream take(Page page, int attempts) {
		try {
			return takeScreenshot(page);
		} catch (RuntimeException e) {
			try {
				return retry(page, attempts);
			} catch (RuntimeException e2) {
				System.err.println("[SCREENSHOT FAILED] \n ERRO: " + e.getMessage());
				return null;
			}
		}
	}

	private InputStream retry(Page page, int attempts) throws RuntimeException {
		for (int i = 0; i < attempts; i++) {
			try {
				System.err.println("[SCREENSHOT FAILED] \n TRYING AGAIN ....");
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

		System.out.println("[SCREENSHOT TAKED]");
		InputStream is = new ByteArrayInputStream(bytes);
		return is;

	}
}
