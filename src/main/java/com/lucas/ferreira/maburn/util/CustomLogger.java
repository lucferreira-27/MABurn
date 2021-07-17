package com.lucas.ferreira.maburn.util;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void log(String msg) {
        LOGGER.log(Level.INFO, msg); 


	}

	public static void logCollection(Object msg) {

		if (msg instanceof Collection<?>) {

			((Collection<?>) msg).forEach(m -> {

			});

			return;
		}

	}

	public static void log(Throwable throwable) {
		System.err.println("> " + throwable.getMessage());
	}

}

class DemoLogger {

}
