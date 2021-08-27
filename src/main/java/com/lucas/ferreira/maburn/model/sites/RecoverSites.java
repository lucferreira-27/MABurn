package com.lucas.ferreira.maburn.model.sites;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.util.ResourcesFile;

public class RecoverSites {
	public List<RegisteredSite> recoverAll() throws Exception {
		FindLocalSites findLocalSites = new FindLocalSites();
		List<Path> paths = new ArrayList<>();
		Path path = findLocalSites.findAll();
		paths = ResourcesFile.listAll(path).stream().filter((f) -> {
			return f.toString().contains("sites\\") || f.toString().contains("sites/");
		}).collect(Collectors.toList());

		RegisterSite registerSite = new RegisterSite();

		List<RegisteredSite> sites = registerSite.registerAll(paths);

		if (path.getFileSystem().isOpen() && Resources.getResourceFileSystem() != null)
			path.getFileSystem().close();

		return sites;
	}
}
