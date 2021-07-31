package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.Platform;
import com.lucas.ferreira.maburn.model.browser.URLBuilder;

public class URLBuilderTest {
	
	
	@Test
	public void getBrowserBuildUrlFromWindowns64() {
		URLBuilder urlBuilder = new URLBuilder();
		String firefoxBuildUrl = urlBuilder.getBrowserBuildUrl(1238, Browsers.FIREFOX, Platform.WINDOWS_64);
		String firefoxBuildUrlExpected = "https://playwright.azureedge.net/builds/firefox/1238/firefox-win64.zip";
		String chromiumBuildUrl = urlBuilder.getBrowserBuildUrl(857950, Browsers.CHROMIUM, Platform.WINDOWS_64);
		String chromiumBuildUrlExpected = "https://playwright.azureedge.net/builds/chromium/857950/chromium-win64.zip";
		String webkitBuildUrl = urlBuilder.getBrowserBuildUrl(1446, Browsers.WEBKIT, Platform.WINDOWS_64);
		String webKitBuildUrlExpected = "https://playwright.azureedge.net/builds/webkit/1446/webkit-win64.zip";
		String ffmpegBuildUrl = urlBuilder.getBrowserBuildUrl(1005, Browsers.FFMPEG, Platform.WINDOWS_64);
		String ffmpegBuildUrlExpected = "https://playwright.azureedge.net/builds/ffmpeg/1005/ffmpeg-win64.zip";
		
		
		assertThat(firefoxBuildUrl, is(firefoxBuildUrlExpected));
		assertThat(chromiumBuildUrl, is(chromiumBuildUrlExpected));
		assertThat(webkitBuildUrl, is(webKitBuildUrlExpected));
		assertThat(ffmpegBuildUrl, is(ffmpegBuildUrlExpected));
		
	}
	@Test
	public void getBrowserBuildUrlFromWindowns32() {
		URLBuilder urlBuilder = new URLBuilder();
		String firefoxBuildUrl = urlBuilder.getBrowserBuildUrl(1238, Browsers.FIREFOX, Platform.WINDOWS_32);
		String firefoxBuildUrlExpected = "https://playwright.azureedge.net/builds/firefox/1238/firefox-win32.zip";
		String chromiumBuildUrl = urlBuilder.getBrowserBuildUrl(857950, Browsers.CHROMIUM, Platform.WINDOWS_32);
		String chromiumBuildUrlExpected = "https://playwright.azureedge.net/builds/chromium/857950/chromium-win32.zip";
		String webkitBuildUrl = urlBuilder.getBrowserBuildUrl(1446, Browsers.WEBKIT, Platform.WINDOWS_32);
		String webKitBuildUrlExpected = "https://playwright.azureedge.net/builds/webkit/1446/webkit-win32.zip";
		String ffmpegBuildUrl = urlBuilder.getBrowserBuildUrl(1005, Browsers.FFMPEG, Platform.WINDOWS_32);
		String ffmpegBuildUrlExpected = "https://playwright.azureedge.net/builds/ffmpeg/1005/ffmpeg-win32.zip";
		
		
		assertThat(firefoxBuildUrl, is(firefoxBuildUrlExpected));
		assertThat(chromiumBuildUrl, is(chromiumBuildUrlExpected));
		assertThat(webkitBuildUrl, is(webKitBuildUrlExpected));
		assertThat(ffmpegBuildUrl, is(ffmpegBuildUrlExpected));
		
	}
	@Test
	public void getBrowserBuildUrlFromLinux() {
		URLBuilder urlBuilder = new URLBuilder();
		String firefoxBuildUrl = urlBuilder.getBrowserBuildUrl(1238, Browsers.FIREFOX, Platform.LINUX);
		String firefoxBuildUrlExpected = "https://playwright.azureedge.net/builds/firefox/1238/firefox-linux.zip";
		String chromiumBuildUrl = urlBuilder.getBrowserBuildUrl(857950, Browsers.CHROMIUM, Platform.LINUX);
		String chromiumBuildUrlExpected = "https://playwright.azureedge.net/builds/chromium/857950/chromium-linux.zip";
		String webkitBuildUrl = urlBuilder.getBrowserBuildUrl(1446, Browsers.WEBKIT, Platform.LINUX);
		String webKitBuildUrlExpected = "https://playwright.azureedge.net/builds/webkit/1446/webkit-linux.zip";
		String ffmpegBuildUrl = urlBuilder.getBrowserBuildUrl(1005, Browsers.FFMPEG, Platform.LINUX);
		String ffmpegBuildUrlExpected = "https://playwright.azureedge.net/builds/ffmpeg/1005/ffmpeg-linux.zip";
		
		
		assertThat(firefoxBuildUrl, is(firefoxBuildUrlExpected));
		assertThat(chromiumBuildUrl, is(chromiumBuildUrlExpected));
		assertThat(webkitBuildUrl, is(webKitBuildUrlExpected));
		assertThat(ffmpegBuildUrl, is(ffmpegBuildUrlExpected));

	}
	@Test
	public void getBrowserBuildUrlFromMac() {
		URLBuilder urlBuilder = new URLBuilder();
		String firefoxBuildUrl = urlBuilder.getBrowserBuildUrl(1238, Browsers.FIREFOX, Platform.MAC);
		String firefoxBuildUrlExpected = "https://playwright.azureedge.net/builds/firefox/1238/firefox-mac.zip";
		String chromiumBuildUrl = urlBuilder.getBrowserBuildUrl(857950, Browsers.CHROMIUM, Platform.MAC);
		String chromiumBuildUrlExpected = "https://playwright.azureedge.net/builds/chromium/857950/chromium-mac.zip";
		String webkitBuildUrl = urlBuilder.getBrowserBuildUrl(1446, Browsers.WEBKIT, Platform.MAC);
		String webKitBuildUrlExpected = "https://playwright.azureedge.net/builds/webkit/1446/webkit-mac.zip";
		String ffmpegBuildUrl = urlBuilder.getBrowserBuildUrl(1005, Browsers.FFMPEG, Platform.MAC);
		String ffmpegBuildUrlExpected = "https://playwright.azureedge.net/builds/ffmpeg/1005/ffmpeg-mac.zip";
		
		
		assertThat(firefoxBuildUrl, is(firefoxBuildUrlExpected));
		assertThat(chromiumBuildUrl, is(chromiumBuildUrlExpected));
		assertThat(webkitBuildUrl, is(webKitBuildUrlExpected));
		assertThat(ffmpegBuildUrl, is(ffmpegBuildUrlExpected));
		
	}
}
