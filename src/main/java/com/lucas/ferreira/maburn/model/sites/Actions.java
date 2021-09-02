package com.lucas.ferreira.maburn.model.sites;

import com.lucas.ferreira.maburn.util.Timeout;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.Response;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.logging.Logger;

public class Actions {

	private final InteractSite interactSite;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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

		LOGGER.config("[Javascript - Maburn OFF]");
		interactSite.getWorking().set(false);
		Timeout.waitUntil(interactSite.getLoadScript().getExecute().isWorking(), 30000);
		try {
			interactSite.getPage().close();
		}catch (PlaywrightException e) {
			if(e.getMessage().contains("Target closed.undefined")) {
				LOGGER.warning("[ON CLOSE] Target closed.undefined");
			}
		}
	}



	public void actionGoto(String url) {
		LOGGER.config("Select Action Goto");
		Page page = interactSite.getPage();
		LOGGER.config("Navigate to " + url);
		Response response = page.navigate(url);
		response.finished();
	}

	public void actionScreenshot() {
		LOGGER.config("[SCREENSHOT]");
		Page page = interactSite.getPage();
		SiteResult siteResult = interactSite.getSiteResult();
		if (page.isClosed()) {
			LOGGER.config("[SCREENSHOT NOT TAKED, PAGE IS CLOSED]");
			return;
		}
		if (siteResult.getPageInfo().getImageSmall() != null) {
			LOGGER.config("[SCREENSHOT ALREADY TAKED]");
			return;
		}
		Screenshot screenshot = new Screenshot();
		InputStream image = screenshot.take(page, 3);
		siteResult.getPageInfo().setImageSmall(new Image(image));
		siteResult.getPageInfo().setTitle(page.title());
		siteResult.getPageInfo().setUrl(page.url());
	
	}

}
