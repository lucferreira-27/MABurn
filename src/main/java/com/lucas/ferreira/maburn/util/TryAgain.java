package com.lucas.ferreira.maburn.util;


import java.util.function.Predicate;
import java.util.function.Supplier;

public class TryAgain {
	//TODO ![MAKE A THIS A BUILD TYPE]!
	public static <T, C extends Exception> T tryagain(Supplier<T> run, int attempts, Class<C> e)
			throws InstantiationException, IllegalAccessException {
		for (int i = 0; i < attempts; i++) {
			System.err.println("[TRYAGAIN] - [Trying again ...]");
			try {
				return run.get();

			} catch (Exception e2) {
				e2.printStackTrace();
				if (e2.equals(e.newInstance())) 
					continue;

			}
			break;

		}
		return null;
	}


	public static  <T, C extends Exception> T tryagain(Supplier<T> run, Predicate<Exception> check, int attempts,
			Class<C> e) throws InstantiationException, IllegalAccessException {
		for (int i = 0; i < attempts; i++) {
			System.err.println("[TRYAGAIN] - [Trying again ...]");
			try {
				return run.get();

			} catch (Exception e2) {
				if (e2.equals(e.newInstance())) {
					if (check.test(e2)) {
						continue;
					}
				}

			}
			break;

		}
		return null;
	}

}
