package com.lucas.ferreira.maburn.model.metadata.image;

import java.util.Map;

import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.TargetMetadata;

public class ReadImageMetadata extends ReadMetadata{
	private String keyTagType = "Expected File Name Extension";
	private String keyTagHeight = "Image Height";
	private String keyTagWidth = "Image Width";
	private String keyTagSize = "File Size";


	@Override
	protected TargetMetadata createMetadata(Map<String, String> mapTags) {
		ImageMetadata imageMetadata = new 	ImageMetadata();
		imageMetadata.setHeight(mapTags.get(keyTagHeight));
		imageMetadata.setWidth(mapTags.get(keyTagWidth));
		imageMetadata.setType(mapTags.get(keyTagType));
		imageMetadata.setSize(mapTags.get(keyTagSize));

		return imageMetadata;
	}

}
