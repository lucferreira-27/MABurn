package com.lucas.ferreira.maburn.model.download;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.exceptions.FileTypeNotSupportException;

import java.io.File;
import java.util.Arrays;

public class FileTypeDetection {

	private static final String DIRECTORY_NAME = "File Type";
	private static final String TAG_NAME = "Expected File Name Extension";

	public static boolean isAcceptType(String type) throws FileTypeNotSupportException {
		if (!anySupportTypeMathFound(type)) {
			if (isNotSupportType(type)) {
				throw new FileTypeNotSupportException("File type " + type + " not supported!");
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	private static boolean anySupportTypeMathFound(String type) {
		return Arrays.stream(FileTypeAccept.values())
				.anyMatch((fileTypeAccept) -> fileTypeAccept.getName().equals(type));
	}

	private static boolean isNotSupportType(String type) {
		return Arrays.stream(FileTypeNotSupport.values())
				.anyMatch((fileTypeAccept) -> fileTypeAccept.getName().equals(type));
	}

	public String getFileType(String path) throws Exception {

		Metadata metadata = getMetadata(path);
		return findTypeFormat(metadata);

	}

	private String findTypeFormat(Metadata metadata) {
		for (Directory directory : metadata.getDirectories()) {

			for (Tag tag : directory.getTags()) {
				if (tag.getDirectoryName().equals(DIRECTORY_NAME) && tag.getTagName().contains(TAG_NAME)) {
					return tag.getDescription();
				}
			}
		}
		return null;
	}

	private Metadata getMetadata(String path) throws FileMetadataNotFound {
		try {
			File file = new File(path);
			return ImageMetadataReader.readMetadata(file);
		} catch (Exception e) {

			throw new FileMetadataNotFound(e.getMessage());
		}
	}

}
