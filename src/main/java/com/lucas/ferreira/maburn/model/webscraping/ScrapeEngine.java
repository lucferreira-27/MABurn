package com.lucas.ferreira.maburn.model.webscraping;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lucas.ferreira.maburn.util.CustomLogger;


public class ScrapeEngine {
	private WebClient webClient;
	private HtmlPage page;
	
	
	
	
	public ScrapeEngine(String url, String referer) throws ConnectException {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		webClient = new WebClient(BrowserVersion.FIREFOX_78);
		webClient.getOptions().setRedirectEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		if (referer != null)
			webClient.addRequestHeader("Referer", referer);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScript(10000);

		connect(url);
	}

	public ScrapeEngine(String url) throws ConnectException {
		// TODO Auto-generated constructor stub
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.ALL);
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.ALL);
		webClient = new WebClient(BrowserVersion.FIREFOX_78);
		
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.waitForBackgroundJavaScript(10000);
		webClient.waitForBackgroundJavaScriptStartingBefore(10000);

		connect(url);
	}


	public void connect(String url) throws ConnectException {
		try {
			page = webClient.getPage(url);
		} catch (Exception e) {
			e.printStackTrace();

			throw new ConnectException("Erro ao se conectar a " + url + "");
		}
	}

	public Page click(HtmlElement a) {
		try {
			Page clickPage = a.click();
			webClient.waitForBackgroundJavaScript(10000);
			System.out.println(clickPage.getEnclosingWindow());
			CustomLogger.log(clickPage.getWebResponse().getStatusCode());
			CustomLogger.log(clickPage.getUrl());
			CustomLogger.log(clickPage.getEnclosingWindow().getName());
			Page pageEnclosed = webClient.getCurrentWindow().getEnclosedPage();
			
			if (pageEnclosed.isHtmlPage()) {
				page = (HtmlPage) pageEnclosed;
			} else {
				CustomLogger.log(pageEnclosed.getWebResponse().getStatusCode());
				// location = location.substring(location.lastIndexOf("?"), location.length());
				// page = page.getWebClient().getPage(page.getBaseURL() + location);
			}
			

			return clickPage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public HtmlPage getEnclosedPage(int i) {
		return (HtmlPage) page.getWebClient().getWebWindows().get(i).getEnclosedPage();
	}
	public Page getEnclosedPageByName(String name) {
	return	page.getWebClient().getWebWindowByName(name).getEnclosedPage();
	
	}
	public List<WebWindow> getEnclosedPages() {
	return	page.getWebClient().getWebWindows();
	
	}

	public void fillTextField(HtmlElement a) {
		try {
			a.click();
			webClient.waitForBackgroundJavaScript(10000);

			page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public HtmlPage getPage() {
		return page;
	}

	public void setPage(HtmlPage page) {
		this.page = page;
	}
	
	public void setRedirectedEnable(boolean enable) {
		webClient.getOptions().setRedirectEnabled(enable);

	}
}
