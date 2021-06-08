package com.lucas.ferreira.maburn.model.metadata.image;

import com.lucas.ferreira.maburn.model.metadata.TargetMetadata;
import com.sun.prism.impl.Disposer.Target;

public class ImageMetadata implements TargetMetadata{
	private String height;
	private String width;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "ImageMetadata [height=" + height + ", width=" + width + ", type=" + type + ", size=" + size + "]";
	}
	@Override
	public long getLongSize() {
		// TODO Auto-generated method stub
		return sizeToLong();
	}
	private long sizeToLong() {
		String onlyDigitSize = size.replaceAll("\\D", "");
		long longSize = Long.valueOf(onlyDigitSize);
		return longSize;
	}
	
	
}
