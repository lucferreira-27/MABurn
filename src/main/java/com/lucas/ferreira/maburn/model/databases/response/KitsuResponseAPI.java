package com.lucas.ferreira.maburn.model.databases.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection.Response;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.connection.ConnectionModel;
import com.lucas.ferreira.maburn.model.enums.Category;

public class KitsuResponseAPI implements DatabaseResponse {

	private static final String DATABASE_URL = "https://kitsu.io/";
	private String responseBody;

	public KitsuResponseAPI(String url) {
		// TODO Auto-generated constructor stub
		try {
			responseBody = ConnectionModel.connect(url, 3);
		} catch (ConnectionException e) {
			// TODO: handle exception
			throw new ConnectionException(e.getMessage());
		}
	}

	public CollectDatas fetchAll() {
		CollectDatas datas = new CollectDatas();
		System.out.println(responseBody);
		JSONObject jsonResponse = new JSONObject(responseBody);
		JSONObject firstData;
		JSONArray allDatas;
		try {
			allDatas = jsonResponse.getJSONArray("data");
			firstData = allDatas.getJSONObject(0);

		} catch (Exception e) {
			// TODO: handle exception
			firstData = jsonResponse.getJSONObject("data");

		}

		JSONObject attributes = firstData.getJSONObject("attributes");

		String[] titles = fetchTitles(attributes);
		Integer id = fetchId(firstData);
		Category category = fetchType(firstData);
		String[] images = fetchPostImage(attributes);
		String synopsis = fetchSynopsis(attributes);
		String itemDataBaseUrl = getItemDataBaseUrl(id, category);
		String status = fetchStatus(attributes);
		String date = fetchPublishedDate(attributes);
		Double rating = fetchAvaregeRating(attributes);

		datas.setCanonicalTitle(titles[0]);
		
		datas.addTitle("en", titles[1]);
		datas.addTitle("en_jp", titles[2]);
		datas.addTitle("ja_jp", titles[3]);

		datas.setCategory(category);
		datas.addPosterImageLink("tiny", images[0]);
		datas.addPosterImageLink("small", images[1]);
		datas.addPosterImageLink("medium", images[2]);
		datas.addPosterImageLink("large", images[3]);
		datas.addPosterImageLink("original", images[4]);
		datas.setId(id);
		datas.setSynopsis(synopsis);
		datas.setStatus(status);
		datas.setPublishedDate(date);
		datas.setItemDataBaseUrl(itemDataBaseUrl);
		datas.setAvaregeRating(rating);
		return datas;
	}

	private String[] fetchTitles(JSONObject attributes) {
		String enTitle = "";
		String enJpTitle = "";
		String jaJpTitle = "";
		String canonicalTitle = "";
		try {
			enTitle = attributes.getJSONObject("titles").getString("en");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			enJpTitle = attributes.getJSONObject("titles").getString("en_jp");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			jaJpTitle = attributes.getJSONObject("titles").getString("ja_jp");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			canonicalTitle = attributes.getString("canonicalTitle");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String[] titles = { canonicalTitle, enTitle, enJpTitle, jaJpTitle };

		return titles;
	}

	private Category fetchType(JSONObject firstData) {
		String type = firstData.getString("type");

		switch (type) {

		case "anime":
			return Category.ANIME;

		case "manga":
			return Category.MANGA;
		default:
			return Category.UNDEFINED;
		}

	}

	private String fetchSynopsis(JSONObject attributes) {
		try {
			String synopsis = attributes.getString("synopsis");
			synopsis = (synopsis == null ? "" : synopsis);
			return synopsis;
		} catch (JSONException e) {
			// TODO: handle exception
			return null;
		}
	}

	private String fetchStatus(JSONObject attributes) {
		try {
			String status = attributes.getString("status");
			status = (status == null ? "" : status);
			return status;
		} catch (JSONException e) {
			// TODO: handle exception
			return null;
		}
	}

	private String fetchPublishedDate(JSONObject attributes) {
		try {
			String date = attributes.getString("startDate");
			date = (date == null ? "" : date);
			return date;
		} catch (JSONException e) {
			// TODO: handle exception
			return null;
		}
	}

	private Double fetchAvaregeRating(JSONObject attributes) {
		try {
			String ratingString = attributes.getString("averageRating");
			Double rating = (ratingString == null ? 0.00 : Double.parseDouble(ratingString.trim()));

			return rating;
		} catch (JSONException e) {
			// TODO: handle exception
			return null;
		}
	}

	private String[] fetchPostImage(JSONObject attributes) {

		String tinyImage = attributes.getJSONObject("posterImage").getString("tiny");
		String smallImage = attributes.getJSONObject("posterImage").getString("small");
		String mediumImage = attributes.getJSONObject("posterImage").getString("medium");
		String largeImage = attributes.getJSONObject("posterImage").getString("large");
		String originalImage = attributes.getJSONObject("posterImage").getString("original");

		String[] images = { tinyImage, smallImage, mediumImage, largeImage, originalImage };

		return images;

	}

	private Integer fetchId(JSONObject firstData) {
		Integer id = firstData.getInt("id");
		return id;
	}

	private String getItemDataBaseUrl(Integer id, Category category) {
		switch (category) {
		case ANIME:
			return DATABASE_URL + category.name().toLowerCase() + "/" + id;

		case MANGA:
			return DATABASE_URL + category.name().toLowerCase() + "/" + id;
		default:
			break;
		}
		return null;
	}

}
