package com.lucas.ferreira.maburn.util;

import java.util.Map;

import com.lucas.ferreira.maburn.model.dao.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.dao.webdatas.ItemWebData;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.search.SearchResult;

public class WebScrapingUtil {
	
	public static void renameElementToCustomName(int i, ItemWebData itemWebData) {
		
		
		String title = null;
	
		if(itemWebData instanceof EpisodeWebData) {
			 title = "Episode ";	
		}else {
			title = "Chapter ";
		}
		
		if(i < 9) {
			title += "0";
		}
		title += (i + 1);
		itemWebData.setName(title);
	}
	
	public static void removeTrashFromStringSearch(SearchResult searh) {

		switch (searh.getSite()) {
		case ANITUBE:
			searh.setName(removeAnitubeTrashSearch(searh.getName()));

		default:
			break;
		}

	}

	public static void removeTrashFromStringChapter(ChapterWebData obj, Sites sites) {

		switch (sites) {

		case MANGA_HOST:
			obj.setName(removeMangaHostTrashChapter(obj.getName()));
			obj.setName("Chapter " + obj.getName());

			break;

		case MANGA_YABU:
			obj.setName(removeMangaYabuTrashChapter(obj.getName()));
			obj.setName("Chapter " + obj.getName());

			break;

		default:
			break;
		}

	}

	public static void removeTrashFromStringEpisode(EpisodeWebData obj, Sites sites) {

		switch (sites) {
		case ANITUBE:
			obj.setName(removeAnitubeTrashEpisode(obj.getName()));
			obj.setName("Episode " + obj.getName());
			break;
		case GOYABU:

			obj.setName(removeGoyabuTrashEpisode(obj.getName()));
			obj.setName("Episode " + obj.getName());
			break;
		case SAIKO:

			obj.setName(removeSaikoTrashEpisode(obj.getName()));
			obj.setName("Episode " + obj.getName());
			break;

		default:
			break;
		}

	}

	private static String removeAnitubeTrashEpisode(String string) {
		return string.replaceAll("\\D+", "");

	}

	private static String removeGoyabuTrashEpisode(String string) {

		return string.replaceAll("\\D+", "");

	}
	private static String removeSaikoTrashEpisode(String string) {

		return string.replaceAll("\\D+", "");

	}

	private static String removeAnitubeTrashSearch(String string) {
		try {
			return string.substring(0, string.lastIndexOf(" – Todos")).trim();
		} catch (Exception e) {
			// TODO: handle exception
			return string;
		}
	}

	private static String removeMangaHostTrashChapter(String string) {
		System.out.println(string);
		return string.substring(string.indexOf("#") + 1, string.lastIndexOf(" - ")).trim();
	}

	private static String removeMangaYabuTrashChapter(String string) {
		return string.substring(string.lastIndexOf("#") + 1).trim();
	}

	public static String getBestDefinition(Map<Definition, String> definitions) {
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
}
