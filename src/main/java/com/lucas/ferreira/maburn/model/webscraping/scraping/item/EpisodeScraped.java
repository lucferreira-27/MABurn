package com.lucas.ferreira.maburn.model.webscraping.scraping.item;

import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteResult;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraped;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import java.util.HashMap;
import java.util.Map;

public class EpisodeScraped implements ItemScraped {

	private final Map<Definition, String> videoLinks = new HashMap<Definition, String>();
	private String name;
	private AnimeScraped animeScraped;
	private Exception exception;
	private RegisteredSite registeredSite;
	private SiteResult siteResult;

	
	public EpisodeScraped(SiteResult siteResult) {
		String link = siteResult.getItemsValues().get(0).getUrls().get(0);
		this.name = siteResult.getItemsValues().get(0).getName();
		videoLinks.put(Definition.DEFINITION_1080, link);
	}

	public EpisodeScraped(Exception exception) {

		this.exception = exception;
	}

	@Override
	public TitleScraped getTitle() {

		return animeScraped;
	}

	public Map<Definition, String> getVideoLinks() {
		return videoLinks;
	}

	@Override
	public String toString() {
		return "EpisodeScraped [videoLinks=" + videoLinks + ", animeScraped=" + animeScraped + "]";
	}

	@Override
	public Exception getException() {

		return exception;
	}

	public void setTitle(TitleScraped titleScraped) {
		animeScraped = (AnimeScraped) titleScraped;
	};

	@Override
	public RegisteredSite getRegisteredSite() {
		return registeredSite;
	}

	@Override
	public void setRegisteredSite(RegisteredSite registeredSite) {
		this.registeredSite = registeredSite;
	}

	@Override
	public void setSiteResult(SiteResult siteResult) {
		this.siteResult = siteResult;

	}

	@Override
	public SiteResult getSiteResult() {
		// TODO Auto-generated method stub
		return siteResult;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
