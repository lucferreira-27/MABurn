package com.lucas.ferreira.maburn.model.webscraping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.util.Resources;

public class ScrapingRuler {

	public RulesProperties readProperties(Sites site) throws IOException {

		InputStream inputStream = Resources.getResourceAsStream("scraping/" + site.name() + "/rules.properties");
		Properties prop = new Properties();
		if (inputStream != null) {
			prop.load(inputStream);
		}
		String queryTitle = prop.getProperty("RULE_QUERY_TITLE");
		String queryItem = prop.getProperty("RULE_QUERY_ITEM");
		String waitUntilSelect = prop.getProperty("RULE_WAIT_UNTIL_SELECT");
		String timeOut = prop.getProperty("RULE_TIME_OUT");
		String loadLevel = prop.getProperty("RULE_LOAD");
		RulesProperties rulesProperties = new RulesProperties(
				queryTitle, 
				queryItem, 
				Boolean.valueOf(waitUntilSelect),
				Double.valueOf(timeOut),
				loadLevel);

		return rulesProperties;
	};

}
