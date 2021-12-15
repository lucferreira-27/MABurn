package com.lucas.ferreira.maburn.model.browser;

public class URLBuilder {
	final static String PLAYWRIGHT_BUILD_URL = "https://playwright.azureedge.net/builds";
	final static String WINDOWS_VERSION_32 = "-win32.zip";
	final static String WINDOWS_VERSION_64 = "-win64.zip";
	final static String LINUX_VERSION = "-linux.zip";
	final static String MAC_VERSION = "-mac.zip";

	public String getBrowserBuildUrl(int build, Binaries browsers, Platform platform) {

		switch (platform) {
		case WINDOWS_32:
			return PLAYWRIGHT_BUILD_URL + "/" + browsers.name().toLowerCase() + "/" + build + "/"
					+ browsers.name().toLowerCase() + WINDOWS_VERSION_32;

		case WINDOWS_64:
			return PLAYWRIGHT_BUILD_URL + "/" + browsers.name().toLowerCase() + "/" + build + "/"
					+ browsers.name().toLowerCase() + WINDOWS_VERSION_64;

		case LINUX:
			return PLAYWRIGHT_BUILD_URL + "/" + browsers.name().toLowerCase() + "/" + build + "/"
					+ browsers.name().toLowerCase() + LINUX_VERSION;

		case MAC:

			return PLAYWRIGHT_BUILD_URL + "/" + browsers.name().toLowerCase() + "/" + build + "/"
					+ browsers.name().toLowerCase() + MAC_VERSION;

		}

		return null;
	}
}
