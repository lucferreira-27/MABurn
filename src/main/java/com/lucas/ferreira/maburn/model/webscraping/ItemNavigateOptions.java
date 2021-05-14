package com.lucas.ferreira.maburn.model.webscraping;

public class ItemNavigateOptions implements CustomNavigateOptions {

	
	private RulesProperties rulesProperties;
	
	public ItemNavigateOptions(RulesProperties rulesProperties) {
		this.rulesProperties = rulesProperties;
	}
	
	
	@Override
	public Options getOptions() {
		// TODO Auto-generated method stub
		return getOptionsFromRules();
	}
	
	private Options getOptionsFromRules(){
		String selectQuery = rulesProperties.getQueryItem().replace("\"", "");;
		Double timeout = rulesProperties.getTimeOut();
		Boolean waitForSelect = rulesProperties.getWaitUntilSelect();
		String loadLevel = rulesProperties.getLoadLevel();

		Options options = new Options(selectQuery, timeout, waitForSelect, loadLevel);
		
		return options;
	}

}
