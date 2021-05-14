package com.lucas.ferreira.maburn.model.webscraping;

public class Options {
	private String selectQuery;
	private Double timeout;
	private Boolean waitForSelect;
	private String loadLevel;

	
	public Options(String selectQuery, Double timeout, Boolean waitForSelect, String loadLevel) {
		this.selectQuery = selectQuery;
		this.timeout = timeout;
		this.waitForSelect = waitForSelect;
		this.loadLevel = loadLevel;
	}
	
	public String getSelectQuery() {
		return selectQuery;
	}
	public Double getTimeout() {
		return timeout;
	}
	public Boolean getWaitForSelect() {
		return waitForSelect;
	}
	public String getLoadLevel() {
		return loadLevel;
	}
	
}



