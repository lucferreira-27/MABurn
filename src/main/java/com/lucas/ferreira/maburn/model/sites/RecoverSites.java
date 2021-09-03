package com.lucas.ferreira.maburn.model.sites;

import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.util.ResourcesFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecoverSites {
    public List<RegisteredSite> recoverAll() throws Exception {
        FindSites findLocalSites = new FindLocalSites();
        List<Path> paths;
        Path path = findLocalSites.findAll();
        paths = new ArrayList<>(ResourcesFile.listAll(path));

        RegisterSite registerSite = new RegisterSite();

        List<RegisteredSite> sites = registerSite
                .registerAll(paths)
                .stream()
                .filter(site -> site.getSiteConfig().isEnable())
                .collect(Collectors.toList());

        if (path.getFileSystem().isOpen() && Resources.getResourceFileSystem() != null)
            path.getFileSystem().close();

        return sites;
    }
}
