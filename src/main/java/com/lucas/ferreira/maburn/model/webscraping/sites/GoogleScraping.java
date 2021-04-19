package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lucas.ferreira.maburn.util.CustomLogger;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

public class GoogleScraping {

	private final static String FIRST_RESULT_LINK = "document.querySelector('.yuRUbf > a').href";
	private final static String ALL_RESULT_LINK = "document.querySelectorAll('#rso > .g')";
	
	private WebEngine engine;
	private List<String> links = new ArrayList<String>();
	private BooleanProperty loadWorkerDone = new SimpleBooleanProperty(false);
	private BooleanProperty scriptExcuted = new SimpleBooleanProperty(false);

	private StringProperty result = new SimpleStringProperty();
	public GoogleScraping(WebEngine engine) {
		// TODO Auto-generated constructor stub
		this.engine = engine;
	}

	public String getFirstMathResult() {
		waitLoadWorker();
		
		result.addListener((obs, oldvalue, newvalue) ->{
			scriptExcuted.set(true);
		});
		
		Platform.runLater(() -> { result.set((String) engine.executeScript(FIRST_RESULT_LINK)); });
		while(!scriptExcuted.get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return result.get();
	}

	private void waitLoadWorker() {
		Platform.runLater(() -> {
			engine.getLoadWorker().stateProperty().addListener((obs, oldvalue, newvalue) -> {
				if (engine.getLoadWorker().stateProperty().get() == Worker.State.SUCCEEDED) {
					loadWorkerDone.set(true);
				}
			});
		});

		while (!loadWorkerDone.get()) {
			try {
				CustomLogger.log("LoadWorker ... ");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<String> getAllResult() {
		NodeList result = (NodeList) engine.executeScript(ALL_RESULT_LINK);
		for (int i = 0; i < result.getLength(); i++) {
			Node node = result.item(i);
			System.out.println(node);
		}
		return null;
	}

	public WebEngine getEngine() {
		return engine;
	}

}
