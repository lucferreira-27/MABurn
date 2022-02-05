package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.exceptions.SearchNotResultException;
import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.webscraping.scraping.SearchScraping;
import com.lucas.ferreira.maburn.model.webscraping.search.SearchScraped;

import java.io.IOException;

public class TitleDownloadSearch {
	private XmlConfigurationOrchestrator xmlConfigurationOrchestrator = new XmlConfigurationOrchestrator();
	private ConfigForm configForm;
	private SearchEngine engine;
	
	public TitleDownloadSearch(SearchEngine engine) throws IOException {
		configForm = xmlConfigurationOrchestrator.read();
		this.engine = engine;
	}
	
	public String searchScraping(String value, RegisteredSite site) throws SearchNotResultException {
		SearchScraping searchScraping = new SearchScraping(
				configForm.getGeralConfigForm().getBrowserHeadless(),
				value,
				engine,
				site);

		SearchScraped searchScraped = searchScraping.search();
		if (searchScraped == null || searchScraped.getResults().size() == 0) {
			throw new SearchNotResultException("Not Result found");
		}

		String bestResult = searchScraped.getResults().get(0);
		return bestResult;
	}
}
