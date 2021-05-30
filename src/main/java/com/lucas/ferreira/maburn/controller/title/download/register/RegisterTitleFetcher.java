package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.exceptions.BadScrapingException;
import com.lucas.ferreira.maburn.exceptions.NotSourceSelectException;
import com.lucas.ferreira.maburn.exceptions.NotURLFoundInRecover;
import com.lucas.ferreira.maburn.fetch.FetchInSystem;
import com.lucas.ferreira.maburn.fetch.title.FetchTitle;
import com.lucas.ferreira.maburn.model.alert.ManualSearchAlertController;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;
import com.lucas.ferreira.maburn.model.messages.WarningMessage;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import javafx.scene.control.TextArea;

public class RegisterTitleFetcher {

	private CollectionTitle title;

	private Message errorMessage;
	private Message warningMessage;
	private Message succededMessage;

	private final String errorSourceMsg = "You need select a source before fetch.";
	private final String errorRecoverMsg = "No recover data found for this source.";
	private final String errorScrapingMsg = "Sorry something went wrong. Try again or other source";
	private final String warningMsgFetch = "Fetching please wait ...";
	private final String succededMsg = " fetched!";

	public RegisterTitleFetcher(CollectionTitle title, TextArea txtFetchMsg) {
		errorMessage = new ErrorMessage(txtFetchMsg);
		warningMessage = new WarningMessage(txtFetchMsg);
		succededMessage = new SucceedMessage(txtFetchMsg);
		this.title = title;
	}

	public TitleScraped automaticFetch(Sites sourceSelect, RegisterTitleSearcher titleSearcher) throws Exception {

		String bestResult = titleSearcher.searchNow(title, sourceSelect);
		TitleScraped titleScraped = fetch(sourceSelect, bestResult);
		return titleScraped;

	}

	public TitleScraped manualFetch(Sites sourceSelect, ManualSearchAlertController manualSearchAlertController)
			throws Exception {

		String answear = manualSearchAlertController.invokeAlert();
		if(answear == null) {
			return null;
		}
		TitleScraped titleScraped = fetch(sourceSelect, answear);
		return titleScraped;

	}

	public TitleScraped systemFetch(Sites sourceSelect, FetchInSystem fetchInSystem) throws Exception {

		try {
			String url = fetchInSystem.recover(title, sourceSelect);

			TitleScraped titleScraped = fetch(sourceSelect, url);
			return titleScraped;
		} catch (NotURLFoundInRecover e) {
			errorMessage.showMessage(errorRecoverMsg);
			throw new NotSourceSelectException(errorRecoverMsg + "\n" + e.getMessage());

		}

	}

	private TitleScraped fetch(Sites sourceSelect, String titleUrl) throws Exception {

		TitleScraped titleScraped = null;
		try {

			checkSource(sourceSelect);

			titleScraped = fetchTitleNow(titleUrl, sourceSelect);
			succededMessage.showMessage(title.getTitleDataBase() + succededMsg);

		} catch (NotSourceSelectException e) {
			errorMessage.showMessage(errorSourceMsg);
			throw new NotSourceSelectException(errorSourceMsg + "\n" + e.getMessage());

		} catch (BadScrapingException e) {
			errorMessage.showMessage(errorScrapingMsg);
			throw new BadScrapingException(errorScrapingMsg + "\n" + e.getMessage());

		}
		return titleScraped;

	}

	private TitleScraped fetchTitleNow(String bestResult, Sites sourceSelect) throws BadScrapingException {
		FetchTitle fetchTitle = new FetchTitle();
		warningMessage.showMessage(warningMsgFetch);

		TitleScraped titleScraped = fetchTitle.fetch(new AnimeScraping(sourceSelect), bestResult);

		if (titleScraped == null) {
			throw new BadScrapingException();
		}
		return titleScraped;
	}

	private void checkSource(Sites sourceSelect) throws NotSourceSelectException {
		if (sourceSelect == null) {
			throw new NotSourceSelectException();
		}
		if (errorMessage.isShowing()) {
			errorMessage.hideAndClearMessage();
		}

	}

}