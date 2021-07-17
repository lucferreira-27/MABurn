package com.lucas.ferreira.maburn.model.download;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;

public class FileTypeDetection {
	
	private String directoryName = "File Type";
	private String tagName = "Expected File Name Extension";

	public static boolean isAcceptType(String type) {
		return anyMathFound(type);
	}

	private static boolean anyMathFound(String type) {
		return Arrays.asList(FileTypeAccept.values()).stream()
				.anyMatch((fileTypeAccept) -> fileTypeAccept.getName().equals(type));
	}
	public String getFileType(String path) throws Exception {
		
		Metadata metadata = getMetadata(path);
		return findTypeFormat(metadata);
		
	}

	private String findTypeFormat(Metadata metadata) {
		for (Directory directory : metadata.getDirectories()) {
			
		    for (Tag tag : directory.getTags()) {
		    	if(tag.getDirectoryName().equals(directoryName) && tag.getTagName().contains(tagName)) {
		        	return tag.getDescription();
		    	}
		    }
		}
		return null;
	}

	private Metadata getMetadata(String path) throws FileMetadataNotFound {
		try {
		File file = new File(path);
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		return metadata;
		}catch (Exception e) {
			
			throw new FileMetadataNotFound(e.getMessage());
		}
	}
	
	


}
