package com.lucas.ferreira.mangaburn.testing.video;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ReadImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.ReadVideoMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;

public class ReadVideoMetadataTest {
	
	@Test
	public void readVideoMp4ByPathTest() {
		ReadMetadata metadata = new ReadVideoMetadata();
		try {
			VideoMetadata videoMetadata = (VideoMetadata) metadata.readTargetMetada("D:\\Teste\\Episode 01.mp4");
			
		} catch (FileMetadataNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
