package com.lucas.ferreira.maburn.model.webscraping.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.bean.webdatas.AnimeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.TitleWebData;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.SearchResult;
import com.lucas.ferreira.maburn.model.webscraping.Scraper;
import com.lucas.ferreira.maburn.model.webscraping.WebScraping;
import com.lucas.ferreira.maburn.util.WebScrapingUtil;

public class GoyabuScraping extends WebScraping {
	private Scraper scraper = new Scraper();

	private List<Future<String>> futureresponseBodys = new ArrayList<>();
	private AnimeWebData animeWebData;
	private String mainPageUrl;
	private String responseBody;
	private Document document;
	private final ExecutorService exec = Executors.newFixedThreadPool(50, r -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

	@Override
	public TitleWebData fecthTitle(TitleWebData titleWebData) {
		try {
			animeWebData = (AnimeWebData) titleWebData;
			animeWebData.setSite(getSite());

			responseBody = ConnectionModel.connect(animeWebData.getUrl());
			mainPageUrl = animeWebData.getUrl();
			System.out.println("Before: " + animeWebData.getWebDatas().size());
			animeWebData.getWebDatas().addAll(fetchEpisodesForPageUrl());
			System.out.println("After: " + animeWebData.getWebDatas().size());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		// titleWebData.setFetched(true);
		return animeWebData;
	}

	@Override
	public ItemWebData fecthItem(ItemWebData itemWebData) {

		responseBody = ConnectionModel.connect(itemWebData.getUrl());
		EpisodeWebData episodeWebData = (EpisodeWebData) itemWebData;
		episodeWebData = fetchVideoUrlDirectDownload(episodeWebData);

		return episodeWebData;
	}

	@Override
	public List<SearchResult> fetchSearchTitle(String querry) {
		// TODO Auto-generated method stub
		try {
			String result = bingSearch(querry, getSite(), true);
			if (!isTitlePage(result, "https://goyabu.com/assistir/")) {
				SearchResult searchTitleWebData = new SearchResult(getSite());
				searchTitleWebData.setUrl(getTitlePage(result));
				return Arrays.asList(searchTitleWebData);
			//	return insideSearchFetch();
			} else {
				SearchResult searchTitleWebData = new SearchResult(getSite());
				searchTitleWebData.setUrl(result);
				return Arrays.asList(searchTitleWebData);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@Override
	public Sites getSite() {
		// TODO Auto-generated method stub
		return Sites.GOYABU;
	}

	public String getTitlePage(String url) {
		String responseBody = ConnectionModel.connect(url);
		Document document = Jsoup.parse(responseBody);

		Elements elements = scraper.scrapeSnippet(document, ".userav");
		return elements.get(0).attr("href");

	}

	private List<SearchResult> fetchAllItensOnTable(Document document) {
		// TODO Auto-generated method stub
		try {
			List<SearchResult> searchTitleWebDatas = new ArrayList<>();
			Elements elements = scraper.scrapeSnippet(document, ".video-thumb > a > span > img");
			elements.forEach(element -> {

				SearchResult searchTitle = new SearchResult(getSite());
				searchTitle.setName(element.attr("title"));
				searchTitle.setUrl(element.parents().get(1).attr("href"));
				searchTitleWebDatas.add(searchTitle);

			});
			return searchTitleWebDatas;
		} catch (WebScrapingException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}
	}

	public List<SearchResult> insideSearchFetch(String querry) {
		String defaultUrl = getSite().getUrl();
		String prefix = "/?s=";
		String searchUrl = defaultUrl + prefix + querry;

		responseBody = ConnectionModel.connect(searchUrl);
		document = Jsoup.parse(responseBody);

		return fetchAllItensOnTable(document);
	}

	public List<EpisodeWebData> fetchEpisodesForPageUrl() {
		try {
			document = Jsoup.parse(responseBody);
			List<EpisodeWebData> episodeWebDatas = new ArrayList<>();
			futureresponseBodys.clear();
			
			if (titleHasPages(document)) {
				int value = 1;
				try {
					value = pageNumbers(document);
				} catch (WebScrapingException e) {
					value = 1;
				}
				for (int i = 1; i <= value; i++) {
					nextPage(i, episodeWebDatas);
				}
				waitresponseBody(value);
				fetchAllEpisodesPage(futureresponseBodys, episodeWebDatas);

			} else {
				fetchEpisodePageUrl(episodeWebDatas);
			}

			return episodeWebDatas;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private void waitresponseBody(int itensExpect) {
		int itensDone = 0;

		while (itensDone < itensExpect) {
			itensDone = futureresponseBodys.stream().filter(futureItem -> futureItem.isDone())
					.collect(Collectors.toList()).size();
		}
	}

	private void fetchAllEpisodesPage(List<Future<String>> responseBodys, List<EpisodeWebData> episodesWebDatas) {
		System.out.println("ResponseBodys: " + responseBodys.size());
		futureresponseBodys.forEach(futureItem -> {
			try {
				fetchEpisodePageUrl(Jsoup.parse(futureItem.get()), episodesWebDatas);
			} catch (InterruptedException | ExecutionException | IOException e) {
				e.printStackTrace();
			}
		});
	}

	private int pageNumbers(Document doc) {
		Elements elements = scraper.scrapeSnippet(doc, ".page-numbers");
		int pageNumbers = Integer.parseInt(elements.get(elements.size() - 2).text());

		return pageNumbers;
	}

	private void nextPage(int i, List<EpisodeWebData> episodeWebDatas) {

		Future<String> futureresponseBody = exec.submit(new ConnectionModel(mainPageUrl + "/page/" + i));
		System.out.println("futureresponseBodys.add");
		System.out.println(futureresponseBodys.size());
		futureresponseBodys.add(futureresponseBody);

	}

	private void fetchEpisodePageUrl(List<EpisodeWebData> episodeWebDatas) throws IOException {

		Elements elements = scraper.scrapeSnippet(document, ".video-title > a");
		elements.forEach(element -> {

			EpisodeWebData episodeWebData = new EpisodeWebData(animeWebData);
			episodeWebData.setUrl(element.attr("href"));
			episodeWebData.setName(element.attr("title"));
			WebScrapingUtil.removeTrashFromStringEpisode(episodeWebData, getSite());
			episodeWebDatas.add(episodeWebData);

		});
	}

	private void fetchEpisodePageUrl(Document doc, List<EpisodeWebData> episodeWebDatas) throws IOException {

		Elements elements = scraper.scrapeSnippet(doc, ".video-title > a");
		elements.forEach(element -> {

			EpisodeWebData episodeWebData = new EpisodeWebData(animeWebData);
			episodeWebData.setUrl(element.attr("href"));
			episodeWebData.setName(element.attr("title"));
			WebScrapingUtil.removeTrashFromStringEpisode(episodeWebData, getSite());

			episodeWebDatas.add(episodeWebData);

		});
	}

	private boolean titleHasPages(Document doc) {
		try {
			return true;
		} catch (WebScrapingException e) {
			return false;
		}
	}

	private EpisodeWebData fetchVideoUrlDirectDownload(EpisodeWebData episodeWebData) {

		Elements elements = scraper.scrapeSnippet(Jsoup.parse(responseBody), "script");

		String script = elements.stream()
				.filter(element -> element.toString().contains("const playerInstance=jwplayer('player').setup")
						|| element.toString().contains("const playerInstance = jwplayer('player').setup"))
				.findFirst().get().toString();
		Map<Definition, String> definitions = findDownloadLinksInScript(script);

		episodeWebData.setPlayers(definitions);
		episodeWebData.setDownloadLink(WebScrapingUtil.getBestDefinition(definitions));

		return episodeWebData;
	}

	private Map<Definition, String> findDownloadLinksInScript(String script) {
		try {
			script = script.substring(script.indexOf("const playerInstance=jwplayer('player').setup({"));
		} catch (StringIndexOutOfBoundsException e) {
			// TODO: handle exception
			script = script.substring(script.indexOf("const playerInstance = jwplayer('player').setup({"));

		}
		script = script.substring(script.indexOf("{"), script.indexOf(");"));

		JSONObject scriptJson = new JSONObject(script);
		JSONArray players = scriptJson.getJSONArray("playlist").getJSONObject(0).getJSONArray("sources");

		for (int i = 0; i < players.length(); i++) {
			JSONObject player = players.getJSONObject(i);
			String label = player.getString("label");
			String file = player.getString("file");
			if (file != null && !file.isEmpty())
				if (file.contains("https://repackager.wixmp.com")) {
					Map<Definition, String> links = getHideLinks(file);
					return links;
				} else {
					Map<Definition, String> links = predefinedLinks(label, file);
					return links;
				}

		}
		return null;

	}

	private Map<Definition, String> getHideLinks(String link) {
		Map<Definition, String> links = new HashMap<>();

		link = link.substring("https://repackager.wixmp.com".length() + 1);
		String[] definitions = link.substring(link.indexOf(",") + 1, link.lastIndexOf(",")).split(",");

		String file = link.substring(link.lastIndexOf(",") + 1, link.lastIndexOf(".urlset"));
		String linkId = link.substring(0, link.indexOf(","));

		for (String def : definitions) {
			String directDownload = linkId + def + file;
			directDownload = "https://" + directDownload.trim();
			switch (def) {

			case "1080p":

				links.put(Definition.DEFINITION_1080, directDownload);
				break;
			case "720p":

				links.put(Definition.DEFINITION_720, directDownload);
				break;
			case "480p":
				links.put(Definition.DEFINITION_480, directDownload);
				break;
			default:

				links.put(Definition.DEFINITION_UNDEFINED, directDownload);
				break;
			}
		}

		return links;
	}

	private Map<Definition, String> predefinedLinks(String label, String file) {
		Map<Definition, String> links = new HashMap<Definition, String>();
		switch (label) {

		case "SD":

			links.put(Definition.DEFINITION_360, file);
			break;
		case "HD":

			links.put(Definition.DEFINITION_720, file);
			break;

		default:

			links.put(Definition.DEFINITION_UNDEFINED, file);
			break;
		}
		return links;
	}

}
