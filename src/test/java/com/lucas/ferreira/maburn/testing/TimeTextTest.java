package com.lucas.ferreira.maburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.util.TimeText;

public class TimeTextTest {
	@Test
	public void testTimeToText() {
		String r0 = TimeText.secondsToText(0);
		String r1 = TimeText.secondsToText(30);
		String r2 = TimeText.secondsToText(60);
		String r3 = TimeText.secondsToText(120);
		String r4 = TimeText.secondsToText(3600);
		String r5 = TimeText.secondsToText(7200);
		assertThat(r0, equalTo("0 Seconds"));
		assertThat(r1, equalTo("30 Seconds"));
		assertThat(r2, equalTo("1 Minute"));
		assertThat(r3, equalTo("2 Minutes"));
		assertThat(r4, equalTo("1 Hour"));
		assertThat(r5, equalTo("2 Hours"));


	}
}
