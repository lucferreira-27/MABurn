package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.exceptions.SearchNotResultException;
import com.lucas.ferreira.maburn.model.enums.SearchEngine;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.WarningMessage;

import javafx.scene.control.TextArea;

public class RegisterTitleSearcher {
	private Message warningMessage;
	private Message errorMessage;

	private final String warningMsgSearch = "Searching ...";
	private final String errorSearchMsg = "No results found.";

	public RegisterTitleSearcher(TextArea txtFetchMsg) {
		warningMessage = new WarningMessage(txtFetchMsg);
		errorMessage = new ErrorMessage(txtFetchMsg);

	}

	public FetchableTittle searchNow(FetchableTittle  fetchableTittle) throws SearchNotResultException {
		try {
			CollectionTitle collectionTitle = fetchableTittle.getCollectionTitle();
			TitleDownloadSearch titleDownloadSearch = new TitleDownloadSearch(SearchEngine.GOOGLE);
			warningMessage.showMessage(warningMsgSearch);

			String bestResult = titleDownloadSearch.searchScraping(collectionTitle.getTitleDataBase(), fetchableTittle.getSourceSelect());
			fetchableTittle.setTitleUrl(bestResult);
			return fetchableTittle;
		} catch (Exception e) {
			errorMessage.showMessage(errorSearchMsg);
			throw new SearchNotResultException(errorSearchMsg + "\n" + e.getMessage());

		}

	}


}
