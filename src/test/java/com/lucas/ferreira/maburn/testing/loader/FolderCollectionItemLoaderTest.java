package com.lucas.ferreira.maburn.testing.loader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.loader.folder.FolderCollectionItemLoader;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.util.StringUtil;

public class FolderCollectionItemLoaderTest {
	private File folder;
	private List<File> subFolders = new ArrayList<File>();
	private List<File> files = new ArrayList<File>();

	private void getSampleAndCreateFolder(String sampleType) throws Exception {
		Resources resources = new Resources();

		Path path = Resources.getResourcePath("resources//" + sampleType + "//Sample.txt");
		String sample = resources.getResourceAsString("resources//" + sampleType + "//Sample.txt");
		String name = sample.split("\n")[0];
		name = StringUtil.stringUtilFile(name).trim();
		folder = new File(path.getParent() + "\\" + name);
		folder.mkdir();
	}

	private void createSubFolders() {
		String parent = folder.getAbsolutePath();
		for (int i = 0; i < 10; i++) {
			File file = new File(parent + "\\" + i);
			file.mkdir();
			subFolders.add(file);
		}
	}

	private void createFiles() throws IOException {
		String parent = folder.getAbsolutePath();
		for (int i = 0; i < 10; i++) {
			File file = new File(parent + "\\" + i + ".mp4");
			file.createNewFile();
			files.add(file);
		}
	}

	private void cleanFolders() {
		files.forEach(File::delete);
		subFolders.forEach(File::delete);
		deleteFolder();
	}

	private void deleteFolder() {
		folder.delete();

	}

	@Test
	public void loadAnimeDownloadedTest() throws Exception {
		try {

			int expect = 10;
			int result;

			getSampleAndCreateFolder("anime");
			createFiles();

			String destination = folder.getAbsolutePath();

			FolderCollectionItemLoader normalCollectionLoader = new FolderCollectionItemLoader();
			CollectionTitle collectionItem =  normalCollectionLoader.loadCollectionItems(destination, Category.ANIME);
			

			result = collectionItem.getListSubItens().size();
			cleanFolders();
			assertThat(expect, is(result));
		

		} catch (IOException | URISyntaxException e) {
			// TODO: handle exception
			e.printStackTrace();
			cleanFolders();

		}
	}

	@Test
	public void loadMangaDownloadedTest() throws Exception {
		try {
			int expect = 10;
			int result;
			
			getSampleAndCreateFolder("manga");
			createSubFolders();


			String destination = folder.getAbsolutePath();
			FolderCollectionItemLoader normalCollectionLoader = new FolderCollectionItemLoader();
			CollectionTitle collectionItem =  normalCollectionLoader.loadCollectionItems(destination, Category.MANGA);
			result = collectionItem.getListSubItens().size();
			cleanFolders();
			assertThat(expect, is(result));

			

		} catch (IOException | URISyntaxException e) {
			// TODO: handle exception
			e.printStackTrace();
			cleanFolders();

		}
	}
}
