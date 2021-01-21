package com.lucas.ferreira.maburn.exceptions;

public class FetchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FetchException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}

	public FetchException(Throwable e) {
		// TODO Auto-generated constructor stub

		super(e);
	}
}
