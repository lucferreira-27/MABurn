package com.lucas.ferreira.maburn.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapKeyValue {

	public static <T, E> List<T> getKeys(Map<T, E> map) {
		return map.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
	}

}
