package com.lucas.ferreira.maburn.util;

import com.lucas.ferreira.maburn.model.bean.webdatas.SearchTitleWebData;

public class WebScrapingUtil {
	public static void removeTrashFromSearh(SearchTitleWebData searh) {

		switch (searh.getSite()) {
		case ANITUBE:
			searh.setName(removeAnitubeTrash(searh.getName()));

		default:
			break;
		}

	}

	private static String removeAnitubeTrash(String string) {

		return string.substring(0, string.lastIndexOf(" – Todos"));
	}
}
