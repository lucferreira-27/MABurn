package com.lucas.ferreira.maburn.controller.title.download;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.lucas.ferreira.maburn.exceptions.HttpResponseCodeException;

public class HttpResponseCode {
	public int getCode(HttpURLConnection httpURLConnection) throws IOException, HttpResponseCodeException {
		int code = httpURLConnection.getResponseCode();
		
		if(!isHttpResponseAccept(code)) {
			throw new HttpResponseCodeException("HTTP Response Code: " + code);
		}
		return code;
		
		
	}

	private boolean isHttpResponseAccept(int code) {
		int result = code / 100;
		if(result == 2) {
			return true;
		}else {
			return false;
		}
	}
}
