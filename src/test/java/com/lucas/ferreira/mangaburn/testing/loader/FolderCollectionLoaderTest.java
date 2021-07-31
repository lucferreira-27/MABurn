package com.lucas.ferreira.mangaburn.testing.loader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.loader.folder.FolderCollectionLoader;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.util.StringUtil;

public class FolderCollectionLoaderTest {

	List<File> files = new ArrayList<File>();

	private void getSampleAndCreateFolders(String sampleType) throws IOException, URISyntaxException {
		Resources resources = new Resources();

		Path path = resources.getResourceAsPath("resources//" + sampleType + "//Sample.txt");
		String sample = resources.getResourceAsString("resources//" + sampleType + "//Sample.txt");
		for (String line : sample.split("\n")) {
			line = StringUtil.stringUtilFile(line.trim());
			File folder = new File(path.getParent() + "\\" + line);
			folder.mkdir();
			files.add(folder);
		}
	}

	private void deleteFolders() {
		files.forEach(File::delete);

	}

	@Test
	public void loadAnimeCollectionTest() {
		try {

			int expect = 50;
			int result;

			getSampleAndCreateFolders("anime");
			String destination = files.get(0).getParent();
			FolderCollectionLoader normalCollectionLoader = new FolderCollectionLoader();
			Collections collections = normalCollectionLoader.loadCollection(destination, Category.ANIME);
			result = collections.getItens().size();

			assertThat(expect, is(result));
			deleteFolders();

		} catch (IOException | URISyntaxException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void loadMangaCollectionTest() {
		try {
			getSampleAndCreateFolders("manga");
			int expect = 50;
			int result;
			String destination = files.get(0).getParent();
			FolderCollectionLoader normalCollectionLoader = new FolderCollectionLoader();
			Collections collections = normalCollectionLoader.loadCollection(destination, Category.MANGA);
			result = collections.getItens().size();
			assertThat(expect, is(result));

			deleteFolders();

		} catch (IOException | URISyntaxException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
