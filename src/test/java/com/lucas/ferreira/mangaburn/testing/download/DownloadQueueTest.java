package com.lucas.ferreira.mangaburn.testing.download;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.queue.AnimeTitleDownload;
import com.lucas.ferreira.maburn.model.download.queue.DownloadQueue;
import com.lucas.ferreira.maburn.model.download.queue.ItemFetcher;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class DownloadQueueTest {
	private final static String EPISODE_URL_TEST = "https://www.anitube.site/897023/";

	@Test
	public void addTest() {
		ArrayList<TitleDownload> list = new ArrayList<TitleDownload>();
		DownloadQueue.getInstance().getDownloadList().clear();
		for (int i = 0; i < 10; i++) {
			AnimeTitleDownload anime = new AnimeTitleDownload();
			anime.setId(i);
			list.add(anime);
			DownloadQueue.getInstance().addDownload(list.get(i));
		}

		int expect = list.size();
		int result = DownloadQueue.getInstance().getDownloadList().size();
		assertThat(expect, is(result));

	}

	@Test
	public void removeTest() {
		ArrayList<TitleDownload> list = new ArrayList<TitleDownload>();
		DownloadQueue.getInstance().getDownloadList().clear();
		for (int i = 0; i < 10; i++) {
			AnimeTitleDownload anime = new AnimeTitleDownload();
			anime.setId(i);
			list.add(anime);
			DownloadQueue.getInstance().addDownload(list.get(i));
		}

		DownloadQueue.getInstance().removeDownload(list.get(0));
		int expect = list.size() - 1;
		int result = DownloadQueue.getInstance().getDownloadList().size();
		assertThat(expect, is(result));

	}
	
	@Test
	public void getDownloadTest() {
		Collections collections = null;

		AnimeWebData anime = new AnimeWebData("One Piece");
		anime.setSite(Sites.ANITUBE);
		EpisodeWebData episodeWebData = new EpisodeWebData(anime);
		episodeWebData.setUrl(EPISODE_URL_TEST);
		episodeWebData.setName("01.mp4");
		ItemFetcher fetcher = new ItemFetcher(episodeWebData, new AnitubeScraping());
		fetcher.fetch();
		CustomLogger.log("FETCH!");
		MainInterfaceView mainInterfaceView = new MainInterfaceView();
		mainInterfaceView.setVisibility(false);
		mainInterfaceView.initAndShowGUI();
		MainLoader loader = new MainLoader(new AnimeCollection());
		
		try {
			collections = (Collections) loader.loadCollection("D:\\AnimeBurn").get();
	
			TitleDownload titleDownload = new AnimeTitleDownload(Arrays.asList(episodeWebData), collections);
			DownloadQueue.getInstance().addDownload(titleDownload);
			
			System.out.println(DownloadQueue.getInstance().getDownload(collections.getActualItem().getId()));
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
