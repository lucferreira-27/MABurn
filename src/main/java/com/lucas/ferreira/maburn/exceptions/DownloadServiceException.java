package com.lucas.ferreira.maburn.exceptions;

public class DownloadServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DownloadServiceException(String msg) {
		super(msg);
	}
}
