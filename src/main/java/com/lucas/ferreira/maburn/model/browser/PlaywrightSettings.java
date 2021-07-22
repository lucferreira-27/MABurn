package com.lucas.ferreira.maburn.model.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaywrightSettings {

	private static Map<String, String> env = new HashMap<>();
	private static List<String> arguments = new ArrayList<>();
	

	
	public static void initConfig() throws IOException {

		String[] envs = { "PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "ARGUMENT_MUTE_AUDIO" };

		PlaywrightProperties playwrightProperties = new PlaywrightProperties();
		Map<String, String> properties = playwrightProperties.load("config.properties", envs);
		arguments.add(properties.get(envs[1]));
		properties.remove(envs[1]);
		env = properties;
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
