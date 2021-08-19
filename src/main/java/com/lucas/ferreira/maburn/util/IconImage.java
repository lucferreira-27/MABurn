package com.lucas.ferreira.maburn.util;

import java.io.InputStream;

import javafx.scene.image.Image;

public class IconImage {
	
	
	
	private static Image parseSvg(InputStream in) throws Exception {
		ParseSvg parseSvg = new ParseSvg();
		return parseSvg.parse(in);
	}
	private static Image parsePng(InputStream in) throws Exception  {
		Image image = new Image(in);
		return image;
	}

	
	public static Image parseImage(InputStream in, ImageType imageType) throws Exception {

		if(imageType == ImageType.SVG) {
			return parseSvg(in);
		}else
			return parsePng(in);
		
	}
	
	public enum ImageType{
		SVG,
		PNG,
	}

}
