package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.bean.downloaded.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadServiceModel;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class EpisodeWebData extends ItemWebData {
	private AnimeWebData animeWebData;
	private Downloader<CollectionSubItem> download = new DownloadServiceModel();
	private String name;
	private String url;

	public EpisodeWebData(AnimeWebData animeWebData) {
		// TODO Auto-generated constructor stub
		this.animeWebData = animeWebData;
	}

	private String downloadLink;
	private Map<Definition, String> players;

	public String getPlayers(Definition key) {
		return players.get(key);
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Definition, String> getAvaiblePlayersDefinitions() {
		return players;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return animeWebData.getSite();
	}

	public void setDownloadLink(String bestPlayerDownloadLink) {
		this.downloadLink = bestPlayerDownloadLink;
	}

	public void setPlayers(Map<Definition, String> players) {
		this.players = players;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Downloader<CollectionSubItem> getDownloader() {
		return download;
	}

	@Override
	public Downloader<CollectionSubItem> download(Collections collections) {
		String itemName = collections.getActualItem().getName();
		String destination = collections.getDestination() + "\\" + itemName + "\\" + name;
		 CustomLogger.log(destination);

		CustomLogger.log(destination);
		EpisodeDownloaded episode = new EpisodeDownloaded();
		episode.setName(name);
		download.initialize(Arrays.asList(downloadLink), episode, Arrays.asList(new File(destination)), this);


		try {
			ExecutorService exec = Executors.newFixedThreadPool(5);
			exec.submit(download);
			return download;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CustomLogger.log("!!!!!!");
			e.printStackTrace();
			return download;
		}

	}



}
