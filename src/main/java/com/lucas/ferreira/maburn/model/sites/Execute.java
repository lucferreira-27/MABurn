package com.lucas.ferreira.maburn.model.sites;

import java.util.function.Supplier;
import java.util.logging.Logger;

import com.lucas.ferreira.maburn.util.TryAgain;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Execute {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private BooleanProperty working = new SimpleBooleanProperty(false);
	private AddElement addScript = new AddElement();
	private final String expectErrorMsg = "Execution context was destroyed, most likely because of a navigation.";

	public void execute(ExecuteValues executeValues) {

		String script = executeValues.getScript();
		Page page = executeValues.getPage();
		String cmd = executeValues.getParameter() == null ? "execute()" : "target => execute(target)";

		working.set(true);
		boolean added = addScript.addScript(script, page);
		if (added) {
			try {
				evaluate(executeValues, cmd);
			} catch (Exception e) {
				if (e.getMessage().contains(expectErrorMsg)) {
					try {
						if (containsMaburnClass(page)) {
							evaluate(executeValues, cmd);
						} else {
							addScript.addScript(script, page);
							Thread.sleep(1000);
							evaluate(executeValues, cmd);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} else {
					e.printStackTrace();
				}
			} finally {
				working.set(false);
			}

		} else {
			working.set(false);
		}
	}

	private void evaluate(ExecuteValues executeValues, String cmd) {
		if (!executeValues.getPage().isClosed()) {
			try {
				if (executeValues.getParameter() == null) {
					executeValues.getPage().evaluate(cmd);
				} else {
					executeValues.getPage().evaluate(cmd, executeValues.getParameter());

				}
			} catch (RuntimeException e) {
				retryEvalutate(executeValues, cmd, 3);
			}
		} else {
			LOGGER.warning("[Evaluate not possible Page is closed.]");
			return;
		}
	}

	private void retryEvalutate(ExecuteValues executeValues, String cmd, int attempts) {
		for (int i = 0; i < attempts; i++) {
			LOGGER.warning("[Evaluate not possible.] \n [Trying again ...]");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				evaluate(executeValues, cmd);
				break;
			} catch (RuntimeException e) {
				continue;
			}
		}
		LOGGER.severe("[Evaluate Failed]");

	}

	private void addApp(Page page) {
		page.evaluate("() => {" + "var app = document.createElement('app')\n" + "app.id = 'maburn'\n"
				+ "document.children[0].appendChild(app)" + "}");
	}

	public boolean containsMaburnClass(Page page) throws PlaywrightException {

		try {

			return TryAgain.tryagain(
					() -> page.content().contains("class Maburn"),
					e -> e.getMessage().equals(expectErrorMsg), 
					3, 
					PlaywrightException.class);
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public <T, C extends Exception> T tryagain(Supplier<T> run, int attempts, Class<C> e)
			throws InstantiationException, IllegalAccessException {
		for (int i = 0; i < attempts; i++) {
			LOGGER.warning("[TRYAGAIN] - [Trying again ...]");
			try {
				return run.get();

			} catch (Exception e2) {
				if (e2.equals(e.newInstance())) {
					if (e2.getMessage().equals(expectErrorMsg)) {
						continue;
					}
				}

			}
			break;

		}
		return null;
	}

	public BooleanProperty isWorking() {
		return working;
	}

}
