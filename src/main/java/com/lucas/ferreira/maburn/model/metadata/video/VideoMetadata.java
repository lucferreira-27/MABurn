package com.lucas.ferreira.maburn.model.metadata.video;

import com.lucas.ferreira.maburn.model.metadata.TargetMetadata;

public class VideoMetadata  implements TargetMetadata{
	
	private String height;
	private String width;
	private String duration;
	private String type;
	private String size;

	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "VideoMetadata [height=" + height + ", width=" + width + ", duration=" + duration + ", type=" + type
				+ "]";
	}
	private long sizeToLong() {
		String onlyDigitSize = size.replaceAll("\\D", "");
		long longSize = Long.valueOf(onlyDigitSize);
		return longSize;
	}

	public void setSize(String size) {
		this.size = size;
	}
	public void setSize(Long size) {
		this.size = String.valueOf(size);
	}
	@Override
	public long getLongSize() {
		
		return sizeToLong();
	}

	
	

	
	
}
