package com.lucas.ferreira.maburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.browser.Browsers;
import com.lucas.ferreira.maburn.model.browser.PlaywrightRepository;
import com.lucas.ferreira.maburn.model.browser.RepositoryBrowserJson;

public class PlaywrightRespositoryTest {
	
	private String currentVersion = "1.12.0";
	
	@Test
	public void testLoadUrlFromConfigResultShouldBeEqual() {
		String urlExpected = "https://unpkg.com/playwright@"+currentVersion+"/browsers.json";
		PlaywrightRepository playwrightRepository = new PlaywrightRepository();

		String result = playwrightRepository.loadUrlFromConfig();
		assertThat(urlExpected, is(result));
	}

	@Test
	public void testLoadUrlFromConfigResultShouldNotBeEqual() {
		
		String urlExpected = "https://unpkg.com/playwright@1.09.0/browsers.json";
		PlaywrightRepository playwrightRepository = new PlaywrightRepository();

		String result = playwrightRepository.loadUrlFromConfig();
		assertThat(urlExpected, is(not(result)));
	}

	@Test
	public void testRequestFirefoxInRepository() throws Exception {
		PlaywrightRepository playwrightRepository = new PlaywrightRepository();
		RepositoryBrowserJson expect = new RepositoryBrowserJson();
		expect.setInstallByDefault(true);
		expect.setRevision(1269);
		expect.setName("firefox");

		RepositoryBrowserJson result = playwrightRepository.requestBrowsersInRespository(Browsers.FIREFOX);
		assertThat(expect, is(result));
	}
	@Test
	public void testRequestChromiumInRepository() throws Exception {
		PlaywrightRepository playwrightRepository = new PlaywrightRepository();
		RepositoryBrowserJson expect = new RepositoryBrowserJson();
		expect.setInstallByDefault(true);
		expect.setRevision(888113);
		expect.setName("chromium");

		RepositoryBrowserJson result = playwrightRepository.requestBrowsersInRespository(Browsers.CHROMIUM);
		assertThat(expect, is(result));
	}
	@Test
	public void testRequestWebKitInRepository() throws Exception {
		PlaywrightRepository playwrightRepository = new PlaywrightRepository();
		RepositoryBrowserJson expect = new RepositoryBrowserJson();
		expect.setInstallByDefault(true);
		expect.setRevision(1492);
		expect.setName("webkit");

		RepositoryBrowserJson result = playwrightRepository.requestBrowsersInRespository(Browsers.WEBKIT);
		assertThat(expect, is(result));
	}


}
