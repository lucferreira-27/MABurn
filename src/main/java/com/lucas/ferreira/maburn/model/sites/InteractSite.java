package com.lucas.ferreira.maburn.model.sites;

import com.lucas.ferreira.maburn.util.Timeout;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class InteractSite {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Page page;
	private SiteResult siteResult = new SiteResult();
	private BooleanProperty working = new SimpleBooleanProperty(false);
	private LoadScript loadScript = new LoadScript();
	private SiteValues siteValues;

	public InteractSite(Page page) {
		this.page = page;
		onReceive();
		onClose();
		onConsole();
	}

	public SiteResult get(SiteValues siteValues) {
		this.siteValues = siteValues;
		working.set(true);
		LOGGER.info("Navigate to " + siteValues.getUrl());
		Response response = page.navigate(siteValues.getUrl());
		response.finished();
		processScript(siteValues);
		onLoad();
		checkIfScriptIsAvailable();
		Timeout.waitUntil(working, 30000);
		return siteResult;
	}

	private void checkIfScriptIsAvailable() {
		new Thread(() -> {
			while (working.get()) {
				try {
					Thread.sleep(500);
					if (!loadScript.getExecute().containsMaburnClass(page)) {
						LOGGER.config("Maburn not found in page!");
						loadScript.repeatExecute();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}

		}).start();
	}

	private void processScript(SiteValues siteValues) {
		InitScript initScript = new InitScript();

		try {
			String script = initScript.init(siteValues.getRegisteredSite());

			ExecuteValues executeValues = new ExecuteValues();
			executeValues.setPage(page);
			executeValues.setParameter(siteValues.getTarget());
			executeValues.setScript(script);
			loadScript.execute(executeValues);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void onReceive() {
		ProcessMessage processMessage = new ProcessMessage();

		SelectAction selectAction = new SelectAction(new Actions(this));

		WebServer webServer = LocalServer.getWebServer();
		webServer.getHandles().forEach((handle) -> {
			handle.onPostBody((body) -> {
				try {
					Message message = processMessage.process(body);
					if (message.getMessageType() == MessagesTypes.RESULT) {
						LOGGER.config("Post body length: " + message.getParam().length());
					}
					selectAction.selectAciton(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});

	}

	private void onClose() {
		page.onClose((page) -> {
			LOGGER.config("[PAGE CLOSED!] " + siteValues.getUrl());

		});
	}

	private void onConsole() {
//		page.onConsoleMessage((c) -> {
//			if (c.type().equals("error"))
//				System.out.println(c.text());
//		});
	}

	private void onLoad() {
		page.onLoad(p -> loadScript.repeatExecute());
	}

	public Page getPage() {
		return page;
	}

	public SiteResult getSiteResult() {
		return siteResult;
	}

	public BooleanProperty getWorking() {
		return working;
	}

	public LoadScript getLoadScript() {
		return loadScript;
	}

}
