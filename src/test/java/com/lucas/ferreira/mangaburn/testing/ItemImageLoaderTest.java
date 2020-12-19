package com.lucas.ferreira.mangaburn.testing;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.GridPaneCell;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.images.ItemThumbnailLoader;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.util.FutureResponseUtil;
import com.lucas.ferreira.maburn.util.ResponseUtil;
import com.lucas.ferreira.maburn.util.StringUtil;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class ItemImageLoaderTest {
	

	
	
	
	
	
	@Test
	public void addImageViewInImageGridTest() throws MalformedURLException, IOException {
		MainInterfaceView mainInterfaceView = new MainInterfaceView();
		mainInterfaceView.setVisibility(false);
		mainInterfaceView.initAndShowGUI();
		MainLoader loader = new MainLoader(new AnimeCollection());
		Collections collections = null;
		try {
			collections = (Collections) loader.loadCollection("D:\\AnimeBurn").get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			long sTime = System.currentTimeMillis();
			ExecutorService exec = Executors.newFixedThreadPool(1);

			for(CollectionItem item : collections.getItens()) {
				item.setImageLocal("D:\\Projeto\\"+ StringUtil.stringUtilFile(item.getTitleDataBase()) +".jpg");
				ItemThumbnailLoader thumbnailLoader = new ItemThumbnailLoader(item);
				try {
				GridPaneCell cell = exec.submit(thumbnailLoader).get();
				System.out.println(cell.getUserData());
				}catch (Exception e) {
					// TODO: handle exception
					System.err.println(e.getMessage());
					continue;
				}
			}

			long eTime = System.currentTimeMillis();

			System.out.println("Exercute Service Time:" + (eTime - sTime) / 1000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
