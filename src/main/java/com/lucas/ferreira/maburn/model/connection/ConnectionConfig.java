package com.lucas.ferreira.maburn.model.connection;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.enums.ConnectionError;

public class ConnectionConfig {

	private Map<ConnectionError, Integer> retryAtttempt = new HashMap<ConnectionError, Integer>();

	public ConnectionConfig(Map<ConnectionError, Integer> retryAtttempt) {
		// TODO Auto-generated constructor stub
		this.retryAtttempt = retryAtttempt;
	}

	public void putHandledError(ConnectionError error, Integer attempt) {
		retryAtttempt.put(error, attempt);
	}
	
	public Map<ConnectionError, Integer> getRetryAtttempt() {
		return retryAtttempt;
	}
}
