package com.lucas.ferreira.maburn.util;

public class TimeText {
	
	
	
	public static String secondsToText(Integer time) {
		String timeType = "";

		return cal(time);
	}
	public static String secondsToText(Long time) {
		String timeType = "";

		return cal(time);
	}
	private static String cal(Number number) {
		String timeType;
		int time = number.intValue();
		if (time >= 3600) {
			time = time / 3600;
			if (time == 1)
				timeType = " Hour";
			else {
				timeType = " Hours";
			}
		} else if (time >= 60) {
			time = time / 60;
			if (time == 1)
				timeType = " Minute";
			else {
				timeType = " Minutes";
			}

		} else {
			timeType = " Seconds";
		}
		String timeText = time + timeType;
		return timeText;
	}
}
