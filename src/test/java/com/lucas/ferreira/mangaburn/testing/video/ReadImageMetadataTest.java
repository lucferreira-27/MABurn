package com.lucas.ferreira.mangaburn.testing.video;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ReadImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.ReadVideoMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;

public class ReadImageMetadataTest {
	
	@Test
	public void readImageJpgByPathTest() {
		ReadMetadata metadata = new ReadImageMetadata();
		try {
			ImageMetadata imageMetadata = (ImageMetadata) metadata.readTargetMetada("D:\\Teste\\Page 01.jpg");
			
		} catch (FileMetadataNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
