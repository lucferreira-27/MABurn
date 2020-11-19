package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.model.ConnectionModel;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.enums.AnimesSites;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;

public class AnitubeScraping implements WebScraping {
	private Scraper scraper = new Scraper();
	private Response response;

	public AnitubeScraping() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public AnimesSites getSite() {
		// TODO Auto-generated method stub
		return AnimesSites.ANITUBE;
	}

	public EpisodeWebData fetchVideoUrlDirectDownload(EpisodeWebData episodeWebData) {
		try {

			Elements elements = scraper.scrapeSnippet(response.parse(), "script");

			String script = elements.stream()
					.filter(element -> element.toString().contains("const playerInstance = jwplayer('playerV').setup"))
					.findFirst().get().toString();
			Map<Definition, String> definitions = findDownloadLinksInScript(script);
			episodeWebData.setDirectDownloadUrl(getBestDefinition(definitions));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Map<Definition, String> findDownloadLinksInScript(String script) {
		Map<Definition, String> links = new HashMap<Definition, String>();

		script = script.substring(script.indexOf("{"));
		script = script.substring(script.indexOf("{"), script.indexOf(");"));

		JSONObject scriptJson = new JSONObject(script);
		JSONArray players = scriptJson.getJSONArray("playlist").getJSONObject(0).getJSONArray("sources");
		for (int i = 0; i < players.length(); i++) {
			JSONObject player = players.getJSONObject(i);
			String label = player.getString("label").replaceAll("[^\\d]", "");
			String file = player.getString("file");
			if (file != null && !file.isEmpty())

				switch (label) {
				case "360":
					links.put(Definition.DEFINITION_360, file);
					break;
				case "480":
					links.put(Definition.DEFINITION_480, file);
					break;
				case "720":
					links.put(Definition.DEFINITION_720, file);
					break;
				case "1080":
					links.put(Definition.DEFINITION_720, file);
					break;
				default:
					links.put(Definition.DEFINITION_UNDEFINED, file);
					break;
				}
		}

		return links;
	}

	private String getBestDefinition(Map<Definition, String> definitions) {

		int best = 0;

		for (Map.Entry<Definition, String> definition : definitions.entrySet()) {
			if (definition.getKey() == Definition.DEFINITION_UNDEFINED) {
				return definition.getValue();
			}

			if (definition.getKey().getSize() > best) {
				best = definition.getKey().getSize();
			}

		}

		for (Map.Entry<Definition, String> definition : definitions.entrySet()) {
			if (definition.getKey().getSize() == best) {
				return definition.getValue();
			}

		}

		return null;

	}

	public List<EpisodeWebData> fetchEpisodesUrl() {
		try {
			List<EpisodeWebData> episodeWebDatas = new ArrayList<>();
			Elements elements = scraper.scrapeSnippet(response.parse(), ".pagAniListaContainer.targetClose > a");
			elements.forEach(element -> {

				EpisodeWebData episodeWebData = new EpisodeWebData();
				episodeWebData.setUrl(element.attr("href"));
				episodeWebDatas.add(episodeWebData);

			});
			return episodeWebDatas;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		// TODO Auto-generated method stub
		AnimeWebData animeWebData = (AnimeWebData) titleWebData;

		response = ConnectionModel.connect(animeWebData.getUrl());

		animeWebData.getWebDatas().addAll(fetchEpisodesUrl());

		return animeWebData;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {
		// TODO Auto-generated method stub

		response = ConnectionModel.connect(itemWebData.getUrl());
		EpisodeWebData episodeWebData = (EpisodeWebData) itemWebData;
		fetchVideoUrlDirectDownload(episodeWebData);
		return episodeWebData;
	}

}
