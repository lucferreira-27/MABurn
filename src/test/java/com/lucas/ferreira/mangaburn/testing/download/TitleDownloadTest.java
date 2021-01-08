package com.lucas.ferreira.mangaburn.testing.download;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.collections.AnimeCollection;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.queue.AnimeTitleDownload;
import com.lucas.ferreira.maburn.model.download.queue.ItemFetcher;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.loader.MainLoader;
import com.lucas.ferreira.maburn.model.webscraping.sites.AnitubeScraping;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class TitleDownloadTest  {
	private final static String TITLE_URL_TEST = "https://www.anitube.site/897019";
	private final static String EPISODE_URL_TEST = "https://www.anitube.site/897023/";
	private TitleDownload titleDownload;
	private Collections collections = null;
	private ExecutorService exec = Executors.newFixedThreadPool(5);

	@Before
	
	public void setup() {

	}
	
	@Test
	public void downloadTest() {
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
			System.out.println(collections.getActualItem().getId());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		titleDownload = new AnimeTitleDownload(Arrays.asList(episodeWebData), collections);
		
		exec.submit(titleDownload);
		while(true) {
			try {
				CustomLogger.log(titleDownload.getItems().get(0).getDownloader().getCompleted());
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
