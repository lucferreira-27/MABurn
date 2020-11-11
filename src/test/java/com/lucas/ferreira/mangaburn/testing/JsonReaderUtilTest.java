package com.lucas.ferreira.mangaburn.testing;

import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.ConnectionModel;
import com.lucas.ferreira.maburn.util.JsonReaderUtil;

public class JsonReaderUtilTest {
	
	@Test
	public void readTest() {
		//Response response = ConnectionModel.connect("https://kitsu.io/api/edge/anime/?filter[text]=golden-kamuy-3");
		//String json = response.body();
		
		String json = "{\"menu\": {\n" + 
				"    \"id\": \"file\",\n" + 
				"    \"value\": \"File\",\n" + 
				"    \"popup\": {\n" + 
				"      \"menuitem\": [\n" + 
				"        {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" + 
				"        {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" + 
				"        {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" + 
				"      ]\n" + 
				"    }\n" + 
				"  }}";
		
		//String filter = "data[0] > links[0] > self";
//		
//		JSONObject obj = new JSONObject(json);
//		
//		JSONObject menu = obj.getJSONObject("menu");
//		System.out.println(menu);
		
		String test  = JsonReaderUtil.read(json, "menu > id");
	}
}
