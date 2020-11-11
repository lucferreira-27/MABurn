package com.lucas.ferreira.maburn.exceptions;

public class WebScrapingException extends RuntimeException {

	private static final long serialVersionUID = -2960248747505389385L;
	
	public WebScrapingException(String msg) {
		super(msg);
	}
}
