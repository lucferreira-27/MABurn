package com.lucas.ferreira.maburn.fetch;

import com.lucas.ferreira.maburn.controller.TitleDownloadSearch;
import com.lucas.ferreira.maburn.exceptions.SearchNotResultException;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.WarningMessage;

import javafx.scene.control.TextArea;

public class TitleSearcher {
	private Message warningMessage;
	private Message errorMessage;

	private final String warningMsgSearch = "Searching ...";
	private final String errorSearchMsg = "No results found.";

	public TitleSearcher(TextArea txtFetchMsg) {
		warningMessage = new WarningMessage(txtFetchMsg);
		errorMessage = new ErrorMessage(txtFetchMsg);

	}

	public String searchNow(CollectionTitle item, Sites sourceSelect) throws SearchNotResultException {
		try {
			TitleDownloadSearch titleDownloadSearch = new TitleDownloadSearch(SearchEngine.GOOGLE);
			warningMessage.showMessage(warningMsgSearch);

			String bestResult = titleDownloadSearch.searchScraping(item.getTitleDataBase(), sourceSelect);
			return bestResult;
		} catch (SearchNotResultException e) {
			errorMessage.showMessage(errorSearchMsg);
			throw new SearchNotResultException(errorSearchMsg + "\n" + e.getMessage());

		}

	}
}
