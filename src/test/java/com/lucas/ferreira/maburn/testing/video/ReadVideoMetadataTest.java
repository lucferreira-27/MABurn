package com.lucas.ferreira.maburn.testing.video;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
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
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Test
    public void readVideoTsByPathTest() throws ImageProcessingException, IOException {

        BasicFileAttributes attr = Files.readAttributes(Paths.get("D:\\Teste\\Episode 01\\ts-01.ts"), BasicFileAttributes.class);
        System.out.println(attr);
    }
}
