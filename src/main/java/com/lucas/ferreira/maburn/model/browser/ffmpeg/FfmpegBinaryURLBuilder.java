package com.lucas.ferreira.maburn.model.browser.ffmpeg;

import com.lucas.ferreira.maburn.model.browser.Platform;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class FfmpegBinaryURLBuilder {

    private static final String API_URL = "https://ffbinaries.com/api/v1/version/";
    public static final String FFMPEG_VERSION = "4.1";
    public static final String FILE_NAME = "ffmpeg-v" + FFMPEG_VERSION;

    public String buildUrl(Platform platform) throws IOException {
        URLConnection urlConnection = connect(API_URL + FFMPEG_VERSION);

        String responseBody = convertStreamToString(urlConnection.getInputStream());
        String ffmpegDownloadUrl = getURLByPlatform(platform, parseJson(responseBody));

        return ffmpegDownloadUrl;
    }
    private String getURLByPlatform(Platform platform, Map<String,String> ffmpegJsons){
        switch (platform) {
            case WINDOWS_32:
                return ffmpegJsons.get("windows-32");

            case WINDOWS_64:
                return ffmpegJsons.get("windows-64");

            case LINUX:
                return ffmpegJsons.get("linux-64");

            case MAC:
                return ffmpegJsons.get("osx-64");

            default :
                return null;
        }
    }

    private URLConnection connect(String url) throws IOException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        return urlConnection;
    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private Map<String,String> parseJson(String body) {
        JSONObject bin = getBinInJson(body);
        Map<String,String> ffmpegJsons = new HashMap<>();
        for (String key : bin.keySet()) {
            FfmpegJson ffmpegJson = new FfmpegJson();
            String name = key;
            ffmpegJson.setPlatformName(name);
            ffmpegJson.getBinaries().put("ffmpeg", bin.getJSONObject(key).get("ffmpeg").toString());
            ffmpegJsons.put(name,bin.getJSONObject(key).get("ffmpeg").toString());
        }
        return ffmpegJsons;

    }

    private JSONObject getBinInJson(String str) {
        JSONObject jsonResponse = new JSONObject(str);
        return jsonResponse.getJSONObject("bin");
    }

}
