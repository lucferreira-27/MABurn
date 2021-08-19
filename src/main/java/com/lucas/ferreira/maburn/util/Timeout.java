package com.lucas.ferreira.maburn.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.microsoft.playwright.TimeoutError;

import javafx.beans.property.BooleanProperty;

public class Timeout {

	public static void waitUntil(BooleanProperty check, long time) {

		long max = time / 100;
		long now = 0;

		while (check.get()) {
			try {
				Thread.sleep(100);
				now++;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			if (now >= max) {
				throw new TimeoutError("Timeout");
			}
		}
	}

	public static void waitUntil(boolean check, long time) {
		long max = time / 100;
		long now = 0;

		while (check) {
			try {
				Thread.sleep(100);
				now++;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			if (now >= max) {
				throw new TimeoutError("Timeout");
			}
		}
	}
}
