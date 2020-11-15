package com.lucas.ferreira.maburn.model.databases.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection.Response;

import com.lucas.ferreira.maburn.exceptions.ConnectionException;
import com.lucas.ferreira.maburn.model.ConnectionModel;
import com.lucas.ferreira.maburn.model.bean.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;

public class KitsuResponseAPI implements DatabaseResponse {

	private static final String DATABASE_URL = "https://kitsu.io/";
	private Response response;

	public KitsuResponseAPI(String url) {
		// TODO Auto-generated constructor stub
		try {
			response = ConnectionModel.connect(url);
		} catch (ConnectionException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ConnectionException(e.getMessage());
		}
	}

	public CollectDatas fetchAll() {
		CollectDatas datas = new CollectDatas();
		System.out.println(response.body());
		JSONObject jsonResponse = new JSONObject(response.body());
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

		String title = fetchTitle(attributes);
		Integer id = fetchId(firstData);
		Category category = fetchType(firstData);
		String[] images = fetchPostImage(attributes);
		String synopsis = fetchSynopsis(attributes);
		String itemDataBaseUrl = getItemDataBaseUrl(id, category);
		String status = fetchStatus(attributes);
		String date = fetchPublishedDate(attributes);
		Double rating = fetchAvaregeRating(attributes);
		if(rating == null) {
			rating = 0.00;
		}
		datas.setTitle(title);
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

	private String fetchTitle(JSONObject attributes) {

		String title = attributes.getString("canonicalTitle");

		return title;
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
		String synopsis = attributes.getString("synopsis");
		return synopsis;
	}
	private String fetchStatus(JSONObject attributes) {
		String status = attributes.getString("status");
		return status;
	}
	private String fetchPublishedDate(JSONObject attributes) {
		String date = attributes.getString("startDate");
		return date;
	}
	private Double fetchAvaregeRating(JSONObject attributes) {
		try {
		Double rating = Double.parseDouble(attributes.getString("averageRating").trim());
		return rating;
		}catch (JSONException e) {
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

	public Response getResponse() {
		return response;
	}

}
