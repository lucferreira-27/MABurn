package com.lucas.ferreira.maburn.util;

import java.util.Map;

public class IconsSetter {
	
	
	
	public static <K,V> void setter(Map<K, V> map) {
		map.forEach((k,v) -> System.out.println(k));
	}
}
