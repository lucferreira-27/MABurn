package com.lucas.ferreira.maburn.model.sites;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.Timeout;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.CloseOptions;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.Response;

import javafx.scene.image.Image;

public class Actions {

	private InteractSite interactSite;

	public Actions(InteractSite interactSite) {
		this.interactSite = interactSite;
	}

	public void click(String selector) {
		WaitClick waitClick = new WaitClick(interactSite.getPage());
		waitClick.click(selector);
	}

	public void getResult(String json) {
		try {
			SiteResult siteResult = interactSite.getSiteResult();
			JSONArray jsonResponse = new JSONArray(json);
			siteResult.getItemsValues().clear();
			System.out.println("getResult: " + json.length());
			for (int i = 0; i < jsonResponse.length(); i++) {
				JSONObject jsonObject = jsonResponse.getJSONObject(i);
				ItemValue item = new ItemValue(jsonObject.getString("name"));

				if (jsonObject.has("type")) {
					if (jsonObject.getString("type").equals("manga")) {
						for (int j = 0; j < jsonObject.getJSONArray("url").length(); j++) {
							item.addUrl(jsonObject.getJSONArray("url").getJSONObject(j).getString("image"));
						}
					} else {
						item.addUrl(jsonObject.getString("url"));
					}
				} else {
					item.addUrl(jsonObject.getString("url"));

				}
				siteResult.getItemsValues().add(item);
			}
			javascriptTurnOff();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void javascriptTurnOff() {

		CustomLogger.log("[Javascript - Maburn OFF]");
		interactSite.getWorking().set(false);
		Timeout.waitUntil(interactSite.getLoadScript().getExecute().isWorking(), 30000);
		try {
			interactSite.getPage().close();
		}catch (PlaywrightException e) {
			if(e.getMessage().contains("Target closed.undefined")) {
				System.err.println("[ON CLOSE] Target closed.undefined");
			}
		}
	}

	public void javascriptIs(String on) {
		boolean isOn = Boolean.valueOf(on);
//		if (isOn)
//			CustomLogger.log("[Javascript - Maburn ON]");
//		else {
//			CustomLogger.log("[Javascript - Maburn EXIT]");
//
//		}
	}

	public void actionGoto(String url) {
		Page page = interactSite.getPage();
		Response response = page.navigate(url);
		response.finished();
	}

	public void actionScreenshot() {
		System.out.println("[SCREENSHOT]");
		Page page = interactSite.getPage();
		SiteResult siteResult = interactSite.getSiteResult();
		if (page.isClosed()) {
			System.out.println("[SCREENSHOT NOT TAKED, PAGE IS CLOSED]");
			return;
		}
		if (siteResult.getPageInfo().getImageSmall() != null) {
			System.out.println("[SCREENSHOT ALREADY TAKED]");
			return;
		}
		Screenshot screenshot = new Screenshot();
		InputStream image = screenshot.take(page, 3);
		siteResult.getPageInfo().setImageSmall(new Image(image));
		siteResult.getPageInfo().setTitle(page.title());
		siteResult.getPageInfo().setUrl(page.url());
	
	}

}
