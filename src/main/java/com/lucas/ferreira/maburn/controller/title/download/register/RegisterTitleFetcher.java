package com.lucas.ferreira.maburn.controller.title.download.register;

import com.lucas.ferreira.maburn.controller.title.download.FetchAction;
import com.lucas.ferreira.maburn.exceptions.BadScrapingException;
import com.lucas.ferreira.maburn.exceptions.NotSourceSelectException;
import com.lucas.ferreira.maburn.exceptions.NotURLFoundInRecover;
import com.lucas.ferreira.maburn.model.enums.Sites;
import com.lucas.ferreira.maburn.model.fetch.title.FetchTitle;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.messages.ErrorMessage;
import com.lucas.ferreira.maburn.model.messages.Message;
import com.lucas.ferreira.maburn.model.messages.SucceedMessage;
import com.lucas.ferreira.maburn.model.messages.WarningMessage;
import com.lucas.ferreira.maburn.model.sites.RegisteredSite;
import com.lucas.ferreira.maburn.model.sites.SiteValues;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.AnimeScraping;
import com.lucas.ferreira.maburn.model.webscraping.scraping.title.TitleScraped;

import javafx.scene.control.TextArea;

public class RegisterTitleFetcher {

	private Message errorMessage;
	private Message warningMessage;
	private Message succededMessage;

	private final String errorSourceMsg = "You need select a source before fetch.";
	private final String errorRecoverMsg = "No recover data found for this source.";
	private final String errorScrapingMsg = "Sorry something went wrong. Try again or other source";
	private final String warningMsgFetch = "Fetching please wait ...";
	private final String succededMsg = " fetched!";

	public RegisterTitleFetcher(TextArea txtFetchMsg) {
		errorMessage = new ErrorMessage(txtFetchMsg);
		warningMessage = new WarningMessage(txtFetchMsg);
		succededMessage = new SucceedMessage(txtFetchMsg);

	}

	public TitleScraped fetch(FetchAction fetchAction, FetchableTittle fetchableTittle) throws Exception {

		TitleScraped titleScraped = null;
		try {

			checkSource(fetchableTittle.getSourceSelect());
			fetchAction.action(fetchableTittle);

			if (fetchableTittle.getTitleUrl() == null || fetchableTittle.getTitleUrl().isEmpty()) {
				return null;
			}
			CollectionTitle collectionTitle = fetchableTittle.getCollectionTitle();
			titleScraped = fetchTitleNow(fetchableTittle.getTitleUrl(), fetchableTittle.getSourceSelect());
			succededMessage.showMessage(collectionTitle.getTitleDataBase() + succededMsg);

		} catch (NotSourceSelectException e) {
			errorMessage.showMessage(errorSourceMsg);
			throw new NotSourceSelectException(errorSourceMsg + "\n" + e.getMessage());

		} catch (BadScrapingException e) {
			errorMessage.showMessage(errorScrapingMsg);
			throw new BadScrapingException(errorScrapingMsg + "\n" + e.getMessage());

		} catch (NotURLFoundInRecover e) {
			errorMessage.showMessage(errorRecoverMsg);
			throw new NotURLFoundInRecover(errorRecoverMsg + "\n" + e.getMessage());

		}
		return titleScraped;

	}

	private TitleScraped fetchTitleNow(String bestResult, RegisteredSite sourceSelect) throws BadScrapingException {
		FetchTitle fetchTitle = new FetchTitle();
		warningMessage.showMessage(warningMsgFetch);
		
		SiteValues siteValues = new SiteValues();
		siteValues.setRegisteredSite(sourceSelect);
		siteValues.setUrl(bestResult);
		
		TitleScraped titleScraped = fetchTitle.fetch(new AnimeScraping(), siteValues);

		if (titleScraped == null) {
			throw new BadScrapingException();
		}
		return titleScraped;
	}

	private void checkSource(RegisteredSite sourceSelect) throws NotSourceSelectException {

		if (sourceSelect == null) {
			throw new NotSourceSelectException();
		}
		if (errorMessage.isShowing()) {
			errorMessage.hideAndClearMessage();
		}

	}

}