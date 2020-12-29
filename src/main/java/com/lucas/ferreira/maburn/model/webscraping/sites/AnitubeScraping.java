package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.WebScrapingUtil;

public class AnitubeScraping implements WebScraping {
	private Scraper scraper = new Scraper();
	private AnimeWebData animeWebData;
	private String responseBody;
	private Document document;

	public AnitubeScraping() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		// TODO Auto-generated method stub
		animeWebData = (AnimeWebData) titleWebData;
		animeWebData.setSite(getSite());
		responseBody = ConnectionModel.connect(animeWebData.getUrl());

		animeWebData.getWebDatas().addAll(fetchEpisodesUrl());

		return animeWebData;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {
		// TODO Auto-generated method stub

		responseBody = ConnectionModel.connect(itemWebData.getUrl());
		EpisodeWebData episodeWebData = (EpisodeWebData) itemWebData;
		fetchVideoUrlDirectDownload(episodeWebData);
		return episodeWebData;
	}

	@Override
	public List<SearchTitleWebData> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub

		String defaultUrl = getSite().getUrl();
		String prefix = "/?s=";
		String searchUrl = defaultUrl + prefix + querry;

		responseBody = ConnectionModel.connect(searchUrl);
		document = Jsoup.parse(responseBody);

		return fetchAllItensOnTable(document);
	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return Sites.ANITUBE;
	}

	private List<SearchTitleWebData> fetchAllItensOnTable(Document document2) {
		// TODO Auto-generated method stub
		List<SearchTitleWebData> searchTitleWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(document, ".aniItem > a");
		elements.forEach(element -> {

			SearchTitleWebData searchTitle = new SearchTitleWebData(getSite());
			searchTitle.setUrl(element.attr("href"));
			searchTitle.setName(element.attr("title"));

			WebScrapingUtil.removeTrashFromStringSearch(searchTitle);

			searchTitleWebDatas.add(searchTitle);

		});
		return searchTitleWebDatas;
	}

	private EpisodeWebData fetchVideoUrlDirectDownload(EpisodeWebData episodeWebData) {

		Elements elements = scraper.scrapeSnippet(Jsoup.parse(responseBody), "script");

		String script = elements.stream()
				.filter(element -> element.toString().contains("const playerInstance = jwplayer('playerV').setup"))
				.findFirst().get().toString();
		Map<Definition, String> definitions = findDownloadLinksInScript(script);
		episodeWebData.setPlayers(definitions);
		episodeWebData.setDownloadLink(WebScrapingUtil.getBestDefinition(definitions));

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

	private List<EpisodeWebData> fetchEpisodesUrl() {

		List<EpisodeWebData> episodeWebDatas = new ArrayList<>();
		Elements elements = scraper.scrapeSnippet(Jsoup.parse(responseBody), ".pagAniListaContainer.targetClose > a");
		elements.forEach(element -> {

			EpisodeWebData episodeWebData = new EpisodeWebData(animeWebData);
			episodeWebData.setUrl(element.attr("href"));
			episodeWebData.setName(element.attr("title"));
			WebScrapingUtil.removeTrashFromStringEpisode(episodeWebData, getSite());
			episodeWebDatas.add(episodeWebData);

		});
		return episodeWebDatas;

	}

}
