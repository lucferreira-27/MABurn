package com.lucas.ferreira.maburn.controller.title.download.register;

public class RegisterFetch {
	
	private RegisterFetchSource fetchSource;
	private RegisterFetchChoose fetchChoose;
	
	public RegisterFetch(RegisterFetchSource fetchSource, RegisterFetchChoose fetchChoose) {
		this.fetchSource = fetchSource;
		this.fetchChoose = fetchChoose;
	}
	
	
	
	
	public RegisterFetchChoose getFetchChoose() {
		return fetchChoose;
	}
	
	public RegisterFetchSource getFetchSource() {
		return fetchSource;
	}
	
	
	
}
