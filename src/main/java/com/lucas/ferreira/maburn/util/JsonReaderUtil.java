package com.lucas.ferreira.maburn.util;

import java.util.ArrayList;

import org.json.JSONObject;

public class JsonReaderUtil {
	private static final String CHILDREN = ">";
	private static final String PARENT = "<";
	private static final String ARRAY = "[]";
	private static ArrayList<JSONObject> jsonArray = new ArrayList<>();

//	public static String read(String json, String filter) {
//		JSONObject obj = new JSONObject(json);
//
//		if (filter.contains(CHILDREN)) {
//
//			
//			obj = getChildren(filter, obj);
//			filter = filter.substring(filter.indexOf(">") + 1);
//			filter = read(json, filter);
//
//		}
//		return filter;
//
//	}

	private static JSONObject getChildren(String key, JSONObject obj) {
		if (key.contains(CHILDREN)) {
			key = key.substring(0, key.indexOf(">")).trim();
		}
		System.out.println(obj);
		System.out.println(key);
		return obj.getJSONObject(key);

	}

	public static String read(String json, String filter) {
		JSONObject obj = new JSONObject(json);

//		if (filter.contains(CHILDREN)) {
//
//			
//			obj = getChildren(filter, obj);
//			filter = filter.substring(filter.indexOf(">") + 1);
//			filter = read(json, filter);
//
//		}

		readFilters(obj, filter);
		return filter;

	}

	private static void readFilters(JSONObject obj, String filter) {

		String childres[] = filter.split(">");
		for (String children : childres) {
			children = children.trim();
			try {
				jsonArray.add(obj.getJSONObject(children));
			} catch (Exception e) {
				// TODO: handle exception
				jsonArray.get(0).getJSONObject(children);
				continue;
			}
		}
		jsonArray.forEach(json -> System.out.println("Json: " + json));
	}

}
