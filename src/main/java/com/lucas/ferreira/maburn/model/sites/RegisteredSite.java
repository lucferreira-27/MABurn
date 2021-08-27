package com.lucas.ferreira.maburn.model.sites;

import java.io.File;
import java.nio.file.Path;

public class RegisteredSite {

	private SiteConfig siteConfig = new SiteConfig();
	private final Path folder;

	public RegisteredSite(Path folder) {
		this.folder = folder;
	}

	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public Path getFolder() {
		return folder;
	}

	@Override
	public String toString() {
		return siteConfig.getName();
	}

}
