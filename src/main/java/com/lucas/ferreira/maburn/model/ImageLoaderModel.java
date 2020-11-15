package com.lucas.ferreira.maburn.model;

import java.net.URL;
import java.net.URLConnection;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoaderModel {

	public Image loadImageByUrl(String url) {
		try {
		URLConnection conn = new URL(url).openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0");
		
		Image image = new Image(conn.getInputStream());
		return image;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public ImageView loadImageViewByUrl(String url) {
		try {
			


		URLConnection conn = new URL(url).openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0");
		
		Image image = new Image(conn.getInputStream());
		

		ImageView imageView = new ImageView(image);
		
		return imageView;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
