package com.lucas.ferreira.maburn.model.sites;

import com.lucas.ferreira.maburn.model.documents.Documents;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FindLocalSites implements  FindSites{
    @Override
    public Path findAll() {
        String scriptLocal = Documents.SCRIPT_LOCAL;
        System.out.println(Paths.get(scriptLocal));
        return Paths.get(scriptLocal);
    }
}
