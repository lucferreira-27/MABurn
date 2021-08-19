package com.lucas.ferreira.maburn.model.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;

public class PlaywrightSettings {

	private static Map<String, String> env = new HashMap<>();
	private static List<String> arguments = new ArrayList<>();

	public static void initConfig() throws IOException {
		XmlConfigurationOrchestrator configOrchestrator = new XmlConfigurationOrchestrator();

		String[] envs = { "PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD","PLAYWRIGHT_BROWSERS_PATH","ARGUMENT_DISABLE-WEB-SECURITY" ,"ARGUMENT_MUTE_AUDIO" };

		PlaywrightProperties playwrightProperties = new PlaywrightProperties();
		Map<String, String> properties = playwrightProperties.load("playwright\\config.properties", envs);
		System.out.println(properties);
		arguments.add(properties.get(envs[2]));
		arguments.add(properties.get(envs[3]));
		properties.remove(envs[2]);
		env = properties;
		
		String browserLocal = properties.get("PLAYWRIGHT_BROWSERS_PATH");
		if (!browserLocal.contains("DEFAULT")) {
			browserLocal = browserLocal.replace("MABURN", Documents.BROWSER_LOCAL).replace("\"", "");
		}
		File browserFile = new File(browserLocal);

		if (!browserFile.exists()) {
			if (!browserFile.mkdir()) {
				throw new FileNotFoundException("Failed in create " + browserLocal);
			}
		}
		ConfigForm form = configOrchestrator.read();
		form.getGeralConfigForm().setBrowserLocal(browserLocal);
		configOrchestrator.write(form);
		env.put("PLAYWRIGHT_BROWSERS_PATH", browserLocal);

	}

	public static Map<String, String> getEnv() {
		return env;
	}

	public static List<String> getArguments() {
		return arguments;
	}

	public void addEnv(String key, String value) {
		env.put(key, value);
	}

	public void addArgument(String arg) {
		arguments.add(arg);
	}
}
