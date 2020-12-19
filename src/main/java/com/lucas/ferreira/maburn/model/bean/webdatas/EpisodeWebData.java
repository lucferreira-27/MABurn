package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lucas.ferreira.maburn.model.bean.downloaded.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.download.Downloader;
import com.lucas.ferreira.maburn.model.download.service.model.DownloadSingleServiceModel;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class EpisodeWebData implements ItemWebData {
	private AnimeWebData animeWebData;
	private Downloader<CollectionSubItem> download = new DownloadSingleServiceModel();
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

		String destination = collections.getDestination() + "\\" + animeWebData.getName() + "\\" + name;
		// System.out.println(destination);
		System.out.println(destination);
		download.initialize(Arrays.asList(downloadLink), new EpisodeDownloaded(), Arrays.asList(new File(destination)),
				animeWebData.getSite());

		// download (downloadLink, new File(destination),new
		// EpisodeDownloaded(),animeWebData.getSite());
		try {
			ExecutorService exec = Executors.newFixedThreadPool(5);
			exec.submit(download);
			return download;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("!!!!!!");
			//e.printStackTrace();
			return download;
		}

	}
}
