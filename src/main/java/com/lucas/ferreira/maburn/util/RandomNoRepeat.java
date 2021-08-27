package com.lucas.ferreira.maburn.util;

import java.util.Arrays;
import java.util.Collections;

public class RandomNoRepeat {

	private static int lastIndex = 0;
	private static Integer[] arr = new Integer[10000];

	public static int random() {
		if (lastIndex == 0) {
			for (int i = 0; i < arr.length; i++) {
				arr[i] = i;
			}
			Collections.shuffle(Arrays.asList(arr));
		}
		if (lastIndex == arr.length - 1) {
			lastIndex = 0;
		}
		System.out.println("Random INDEX[" + lastIndex + "]");
		System.out.println("Random: "+ arr[lastIndex]);
		return Integer.valueOf(arr[lastIndex++]);
	}
}
