package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lucas.ferreira.maburn.model.DownloadServiceModel;
import com.lucas.ferreira.maburn.model.bean.ChapterDownloaded;
import com.lucas.ferreira.maburn.model.bean.EpisodeDownloaded;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;
import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class EpisodeWebData implements ItemWebData {
	private AnimeWebData animeWebData;

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

	@Override
	public CollectionSubItem download(Collections collections) {
	
			EpisodeDownloaded episodeDownloaded = new EpisodeDownloaded();
			String destination = collections.getDestination() + "\\" + animeWebData.getName() + "\\" + name;
			System.out.println(destination);
			DownloadServiceModel download = new DownloadServiceModel(downloadLink, new File(destination));
			try {
				System.out.println(downloadLink);
				File file = download.download(downloadLink,animeWebData.getSite());

				episodeDownloaded.setDestination(file.getAbsolutePath());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

		return  null;
	}
}
