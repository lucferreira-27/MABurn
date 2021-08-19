package com.lucas.ferreira.maburn.model.sites;

import java.io.File;

public class RegisteredSite {

	private SiteConfig siteConfig = new SiteConfig();
	private final File folder;

	public RegisteredSite(File folder) {
		this.folder = folder;
	}

	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public File getFolder() {
		return folder;
	}

	@Override
	public String toString() {
		return siteConfig.getName();
	}

}
