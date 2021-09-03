package com.lucas.ferreira.maburn.model.sites;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.ReadProperties;
import com.lucas.ferreira.maburn.util.ResourcesFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RegisterSite {

	public RegisterSite() {
		// TODO Auto-generated constructor stub
	}

	public List<RegisteredSite> registerAll(List<Path> paths) {

		List<RegisteredSite> registerSites = new ArrayList<>();
		paths.forEach((f) -> {
			try {
				
					ResourcesFile.listFiles(f).forEach((a) ->{
						if(a.getFileName().toString().contains("config")) {
							try {
								registerSites.add(register(a));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return registerSites;

	}

	public RegisteredSite register(Path path) throws IOException {

		ReadProperties readProperties = new ReadProperties();
		Properties properties = readProperties.load(new FileInputStream(path.toFile()));
		RegisteredSite registeredSite = new RegisteredSite(path.getParent());
		registeredSite.getSiteConfig().setName(properties.getProperty("name"));
		registeredSite.getSiteConfig().setHomeUrl(properties.getProperty("homeUrl"));
		registeredSite.getSiteConfig().setScriptPath(properties.getProperty("script"));
		registeredSite.getSiteConfig().setCategory(
				(properties.getProperty("category").equals(Category.ANIME.name())) ? Category.ANIME : Category.MANGA);
		registeredSite.getSiteConfig().setSlug(properties.getProperty("slug"));
		registeredSite.getSiteConfig().setLanguage(properties.getProperty("language"));
		return registeredSite;

	}


}
