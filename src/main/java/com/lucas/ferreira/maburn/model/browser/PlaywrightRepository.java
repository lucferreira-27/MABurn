package com.lucas.ferreira.maburn.model.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lucas.ferreira.maburn.util.Resources;

public class PlaywrightRepository {
	private URLConnection urlConnection;
	public List<RepositoryBrowserJson> requestAllBrowsersInRespository() throws Exception {
		
			String responseBody = downloadFileContent();
			return parseJson(responseBody);

	}

	public RepositoryBrowserJson requestBrowsersInRespository(Browsers browser) throws Exception {
		String responseBody = downloadFileContent();
		RepositoryBrowserJson resBrowserJson = parseJson(responseBody).stream()
				.filter(browserJson -> browserJson.getName().equals(browser.name().toLowerCase())).findFirst().orElse(null);
	
		return resBrowserJson;

	}

	private String downloadFileContent() throws Exception {
		String url = loadUrlFromConfig();
		connect(url);
		String json = convertStreamToString(urlConnection.getInputStream());
		return json;
	}

	private List<RepositoryBrowserJson> parseJson(String body) {
		JSONArray browsers = getBrowsersInJson(body);
		List<RepositoryBrowserJson> repositoryBrowserJsons = new ArrayList<>();
		for (int i = 0; i < browsers.length(); i++) {

			RepositoryBrowserJson resBrowserJson = new RepositoryBrowserJson();
			String name = browsers.getJSONObject(i).getString("name");
			Integer revision = browsers.getJSONObject(i).getInt("revision");
			boolean installByDefault = browsers.getJSONObject(i).getBoolean("installByDefault");
			resBrowserJson.setInstallByDefault(installByDefault);
			resBrowserJson.setRevision(revision);
			resBrowserJson.setName(name);
			repositoryBrowserJsons.add(resBrowserJson);
		}
		return repositoryBrowserJsons;

	}

	private JSONArray getBrowsersInJson(String str) {
		JSONObject jsonResponse = new JSONObject(str);
		return jsonResponse.getJSONArray("browsers");
	}

	public String loadUrlFromConfig() {
		try {
			PlaywrightProperties playwrightProperties = new PlaywrightProperties();
			Map<String, String> properties = playwrightProperties.load("playwright\\config.properties", "VERSION", "REPOSITORY_URL");
			String version =  properties.get("VERSION");
			String repositoryUrl = properties.get("REPOSITORY_URL");
			version = repositoryUrl.replace("${version}", version);
			return version;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private void connect(String url) throws MalformedURLException, IOException {
		urlConnection = new URL(url).openConnection();
	}

	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}




}
