package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;

public class PlaywrightSettingsTest {
	@Test
	public void testInitConfigEnvs() throws IOException {
		Map<String, String> expect = new HashMap<>();
		expect.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
		PlaywrightSettings.initConfig();
		Map<String,String> result = PlaywrightSettings.getEnv();
		assertTrue(expect.equals(result));
	}
	@Test
	public void testInitConfigArguments() throws IOException {
		List<String> expect = new ArrayList<>();
		expect.add("--mute-audio");
		PlaywrightSettings.initConfig();
		List<String> result = PlaywrightSettings.getArguments();
		assertTrue(expect.equals(result));
	}
}
