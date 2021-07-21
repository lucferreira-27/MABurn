package com.lucas.ferreira.maburn.model.webscraping;

import com.lucas.ferreira.maburn.model.webscraping.navigate.CustomNavigateOptions;

public class TitleNavigateOptions implements CustomNavigateOptions {
	
	
	private RulesProperties rulesProperties;
	
	public TitleNavigateOptions(RulesProperties rulesProperties) {
		this.rulesProperties = rulesProperties;
	}
	
	
	@Override
	public Options getOptions() {
		
		return getOptionsFromRules();
	}
	
	private Options getOptionsFromRules(){
		String selectQuery = rulesProperties.getQueryTitle().replace("\"", "");;
		Double timeout = rulesProperties.getTimeOut();
		Boolean waitForSelect = rulesProperties.getWaitUntilSelect();	
		String loadLevel = rulesProperties.getLoadLevel();
		Options options = new Options(selectQuery, timeout, waitForSelect, loadLevel);
		
		return options;
	}

}
