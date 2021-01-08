package com.lucas.ferreira.maburn.util;

import java.util.Collection;
import java.util.List;

import com.lucas.ferreira.maburn.model.itens.CollectionSubItem;

public class CustomLogger {

	public static void log(Object msg) {

		if (msg instanceof String && ((String) msg).contains("> ")) {
			System.out.println(msg);
			return;
		}

		System.out.println(msg);
	}
	public static void logCollection(Object msg) {


		if (msg instanceof Collection<?>) {
			System.out.println(msg);
			((Collection<?>) msg).forEach(m -> {
				
				
				System.out.println("> - " + m);
			});
			System.out.println("Collection size: " + ((Collection<?>) msg).size());
			return;
		}
		System.out.println(msg);
	}



	public static void log(Throwable throwable) {
		System.err.println("> " + throwable.getMessage());
	}





}
