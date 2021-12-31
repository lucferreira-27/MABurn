package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;
import org.junit.Test;

import java.io.IOException;

public class FfmpegReaderTest {
    private FfmpegExecutor ffmpegExecutor = new FfmpegExecutor();

    @Test
    public void readVideoMetadatas() throws Exception {
       VideoMetadata videoMetadata =  ffmpegExecutor.getVideoInfo("D:\\Teste\\Episode 01\\ts-01.ts");
        System.out.println(videoMetadata);
    }
}
