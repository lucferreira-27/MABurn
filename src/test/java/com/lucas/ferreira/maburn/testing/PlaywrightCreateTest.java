package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

public class PlaywrightCreateTest {
	@Test
	public void shouldSupportEnvBrowsersPath() throws IOException {
		
		Map<String, String> env =  new HashMap<String,String>();
		env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
		
		Playwright playwright =Playwright.create(new CreateOptions().setEnv(env));
		
		Browser b =  playwright.firefox().launch();
	  
		assertNotNull(b);
	}


	}

