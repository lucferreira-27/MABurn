package com.lucas.ferreira.maburn.model.metadata.video;

import java.util.Map;

import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.TargetMetadata;

public class ReadVideoMetadata  extends ReadMetadata{
	private String keyTagType = "Expected File Name Extension";
	private String keyTagHeight = "Height";
	private String keyTagWidth = "Width";
	private String keyTagDuration = "Duration";



	@Override
	protected TargetMetadata createMetadata(Map<String, String> mapTags) {
		// TODO Auto-generated method stub
		VideoMetadata videoMetadata = new VideoMetadata();
		videoMetadata.setDuration(mapTags.get(keyTagDuration));
		videoMetadata.setHeight(mapTags.get(keyTagHeight));
		videoMetadata.setWidth(mapTags.get(keyTagWidth));
		videoMetadata.setType(mapTags.get(keyTagType));
		return videoMetadata;
	}

}
