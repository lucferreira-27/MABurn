package com.lucas.ferreira.maburn.model.bean.webdatas;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lucas.ferreira.maburn.model.enums.Definition;

public class EpisodeWebData implements ItemWebData {
	private String url;
	
	private String bestPlayerDownloadLink;
	private Map<Definition, String> players;
	

	public String getPlayers(Definition key) {
		return players.get(key);
	}

	public String getUrl() {
		return url;
	}
	public Set<Entry<Definition, String>> getAvaiblePlayersDefinitions() {
		return players.entrySet();
	}
	public String getBestPlayerDownloadLink() {
		return bestPlayerDownloadLink;
	}
	public void setBestPlayerDownloadLink(String bestPlayerDownloadLink) {
		this.bestPlayerDownloadLink = bestPlayerDownloadLink;
	}
	public void setPlayers(Map<Definition, String> players) {
		this.players = players;
	}


	public void setUrl(String url) {
		this.url = url;
	}
}
