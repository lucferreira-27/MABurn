package com.lucas.ferreira.maburn.model.sites;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.util.ReadProperties;

public class RegisterSite {

	public RegisterSite() {
		// TODO Auto-generated constructor stub
	}

	public List<RegisteredSite> registerAll(List<File> files) {

		List<RegisteredSite> registerSites = new ArrayList<>();
		files.forEach((f) -> {
			try {
				if (f.exists())
					registerSites.add(register(f));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return registerSites;

	}

	public RegisteredSite register(File file) throws FileNotFoundException, IOException {
		File config = readConfig(file);

		ReadProperties readProperties = new ReadProperties();
		Properties properties = readProperties.load(new FileInputStream(config));
		RegisteredSite registeredSite = new RegisteredSite(file);
		registeredSite.getSiteConfig().setName(properties.getProperty("name"));
		registeredSite.getSiteConfig().setHomeUrl(properties.getProperty("homeUrl"));
		registeredSite.getSiteConfig().setScriptPath(properties.getProperty("script"));
		registeredSite.getSiteConfig()
				.setCategory((properties.getProperty("category").equals(Category.ANIME.name()))
						? Category.ANIME
						: Category.MANGA);
		return registeredSite;

	}

	private File readConfig(File file) throws FileNotFoundException {

		for (File f : file.listFiles()) {
			if (f.getName().equals("config.properties")) {
				File config = new File(f.getAbsolutePath());
				return config;
			}
		}
		System.out.println(file.getAbsolutePath());
		throw new FileNotFoundException();

	}

}
