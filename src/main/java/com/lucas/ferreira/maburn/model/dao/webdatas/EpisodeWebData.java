package com.lucas.ferreira.maburn.model.dao.webdatas;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.dao.downloaded.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.download.DownloadServiceModel;
import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class EpisodeWebData extends ItemWebData {
	private AnimeWebData animeWebData;
	private Downloader<CollectionItem> download;
	private String name;
	private String url;

	public EpisodeWebData(AnimeWebData animeWebData) {
		

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

	@Override
	public Downloader<CollectionItem> download(Collections collections, TitleDownload titleDownload) {
		download = new DownloadServiceModel();
		String itemName = collections.getActualItem().getName();
		String destination = collections.getDestination() + "\\" + itemName + "\\" + name;
		CustomLogger.log(destination);

		CustomLogger.log(destination);
		EpisodeDownloaded episode = new EpisodeDownloaded();
		episode.setName(name);
		download.initialize(Arrays.asList(downloadLink), episode, Arrays.asList(new File(destination)), this,
				titleDownload);

		try {

			titleDownload.getExecutorDownloader().submit(download);
			return download;
		} catch (Exception e) {
			 
			e.printStackTrace();
			return download;
		}

	}

	@Override
	public String toString() {
		return "EpisodeWebData [\n - anime= " + animeWebData.getName() + ",\n - name=" + name + ",\n - url=" + url
				+ ",\n - fetched= " + fetched + "\n]";
	}
}
