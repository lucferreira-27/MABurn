package com.lucas.ferreira.maburn.util;

import java.util.Map;

import com.lucas.ferreira.maburn.model.bean.webdatas.ChapterWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.EpisodeWebData;
import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.enums.Sites;

public class WebScrapingUtil {
	public static void removeTrashFromStringSearch(SearchTitleWebData searh) {

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

			break;

		case MANGA_YABU:
			obj.setName(removeMangaYabuTrashChapter(obj.getName()));

			break;

		default:
			break;
		}

	}

	public static void removeTrashFromStringEpisode(EpisodeWebData obj, Sites sites) {

		switch (sites) {
		case ANITUBE:
			obj.setName(removeAnitubeTrashEpisode(obj.getName()));
			break;
		case GOYABU:

			obj.setName(removeGoyabuTrashEpisode(obj.getName()));

			break;

		default:
			break;
		}

	}

	private static String removeAnitubeTrashEpisode(String string) {
		return string.substring(string.lastIndexOf(" ")).trim();

	}

	private static String removeGoyabuTrashEpisode(String string) {
		if(string.contains("– Episódio ")) {
			string = string.substring(string.indexOf("– Episódio "));
		}
		else if(string.contains("– Episodio ")) {
			string = string.substring(string.indexOf("– Episodio "));
		}

		return string;

	}

	private static String removeAnitubeTrashSearch(String string) {

		return string.substring(0, string.lastIndexOf(" – Todos")).trim();
	}

	private static String removeMangaHostTrashChapter(String string) {
		return string.substring(string.lastIndexOf("#") + 1, string.lastIndexOf(" - ")).trim();
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
