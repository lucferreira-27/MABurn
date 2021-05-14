package com.lucas.ferreira.maburn.model.webscraping;

public class RulesProperties {
	private String queryTitle;
	private String queryItem;
	private Boolean waitUntilSelect;
	private Double timeOut;
	private String loadLevel;
	
	public RulesProperties(String queryTitle, String queryItem, Boolean waitUntilSelect, Double timeOut, String loadLevel) {
		this.queryTitle = queryTitle;
		this.queryItem = queryItem;
		this.waitUntilSelect = waitUntilSelect;
		this.timeOut = timeOut;
		this.loadLevel = loadLevel;
	}

	public String getQueryTitle() {
		return queryTitle;
	}

	public String getQueryItem() {
		return queryItem;
	}

	public Boolean getWaitUntilSelect() {
		return waitUntilSelect;
	}

	public Double getTimeOut() {
		return timeOut;
	}
	public String getLoadLevel() {
		return loadLevel;
	}

	@Override
	public String toString() {
		return "RulesProperties [queryTitle=" + queryTitle + ", queryItem=" + queryItem + ", waitUntilSelect="
				+ waitUntilSelect + ", timeOut=" + timeOut + "]";
	}
	
}
