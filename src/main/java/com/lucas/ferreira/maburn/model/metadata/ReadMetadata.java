package com.lucas.ferreira.maburn.model.metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;

public abstract class ReadMetadata {
	public TargetMetadata readTargetMetada(String path) throws FileNotFoundException, FileMetadataNotFound {
		return readTargetMetadaInPath(path);
	}

	private TargetMetadata readTargetMetadaInPath(String path) throws FileMetadataNotFound, FileNotFoundException {

		File file = new File(path);
		Metadata metadata = readMetadata(file);
		TargetMetadata targetMetadata = metadataResult(metadata);

		return targetMetadata;
	}

	private TargetMetadata metadataResult(Metadata metadata) {
		Map<String, String> mapTags = getTagsFromMetadata(metadata);
		TargetMetadata targetMetadata = createMetadata(mapTags);
		return targetMetadata;
	}

	protected abstract TargetMetadata createMetadata(Map<String, String> mapTags);

	private Metadata readMetadata(File file) throws FileMetadataNotFound {
		try {
			return ImageMetadataReader.readMetadata(file);
		} catch (Exception e) {
			
			throw new FileMetadataNotFound(e.getMessage());
		}
	}

	private Map<String, String> getTagsFromMetadata(Metadata metadata) {
		Map<String, String> mapTags = new HashMap<String, String>();
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				mapTags.put(tag.getTagName(), tag.getDescription());
			}
		}

		return mapTags;
	}
}
