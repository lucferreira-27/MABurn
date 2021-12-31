package com.lucas.ferreira.maburn.testing.ffmpeg;

import com.lucas.ferreira.maburn.model.FfmpegExecutor;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;
import org.junit.Test;

public class FfmpegReadTest {
    @Test
    public void test() throws Exception {
        FfmpegExecutor execute = new FfmpegExecutor();
        VideoMetadata videoMetadata = execute.getVideoInfo("D:\\AnimeBurn\\Boruto Naruto Next Generations\\Episode 1 Boruto Uzumaki!\\ts-02.ts");
        System.out.println(videoMetadata);
    }
}
