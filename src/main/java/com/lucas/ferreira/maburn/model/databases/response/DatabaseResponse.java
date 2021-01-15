package com.lucas.ferreira.maburn.model.databases.response;

import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.util.CustomLogger;

public interface DatabaseResponse {
	default String connect(String url) {
		long start = System.currentTimeMillis();
		String responseBody = ConnectionModel.connect(url, 3);
		long end =System.currentTimeMillis();
		
		int result = (int) (end - start) / 1000;
		CustomLogger.log("Response connect: " + url + "\nTime: " + result + " seconds");
		return responseBody;
	}
}
