package com.lucas.ferreira.maburn.model.webscraping;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.NavigateOptions;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;

public class PageNavigate {

	private Page page;
	private Options options;

	public PageNavigate(Page page, Options options) {
		// TODO Auto-generated constructor stub
		this.page = page;
		this.options = options;
	}

	public void navigate(String url) throws Exception{


		String query = options.getSelectQuery();
		//Double timeout = options.getTimeout();
		System.out.println(query);
		NavigateOptions navigateOptions = new NavigateOptions();

		if (options.getLoadLevel().equals("DOM_COMPLETE"))
			navigateOptions.setWaitUntil(WaitUntilState.DOMCONTENTLOADED);
		else if (options.getLoadLevel().equals("FULL_LOAD"))
			navigateOptions.setWaitUntil(WaitUntilState.LOAD);

		//navigateOptions.setTimeout(timeout);
		page.navigate(url, navigateOptions);

		if (options.getWaitForSelect()) {
		//	System.out.println("Query: " + query);
			page.waitForSelector(query, new WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED));
		}

	}

}
