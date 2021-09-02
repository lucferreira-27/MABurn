package com.lucas.ferreira.maburn.model.sites;
import com.lucas.ferreira.maburn.util.Resources;
import java.nio.file.Path;

public class FindLocalSites {
	public Path findAll() throws Exception {
		String local = "scraping/sites";

		return Resources.getResourcePath(local);

	}


}
