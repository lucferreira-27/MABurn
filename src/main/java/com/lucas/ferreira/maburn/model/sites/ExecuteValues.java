package com.lucas.ferreira.maburn.model.sites;

import com.microsoft.playwright.Page;

public class ExecuteValues {
	private Page page;
	private String script;
	private String parameter;
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	
	
}
