package com.lucas.ferreira.maburn.model.service.response;

import java.util.logging.Logger;

import com.lucas.ferreira.maburn.model.connection.ConnectionModel;

public interface ServiceResponse {
	
	default String connect(String url) {
		 Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		long start = System.currentTimeMillis();
		String responseBody = ConnectionModel.connect(url, 3);
		long end =System.currentTimeMillis();
		
		int result = (int) (end - start) / 1000;
		logger.info("Response connect: " + url + "\nTime: " + result + " seconds");
		return responseBody;
	}
}
