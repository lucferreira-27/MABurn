package com.lucas.ferreira.maburn.util;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapKeyValue {
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    return map.entrySet()
	              .stream()
	              .filter(entry -> Objects.equals(entry.getValue(), value))
	              .map(Map.Entry::getKey)
	              .collect(Collectors.toList()).get(0);
	}
}
