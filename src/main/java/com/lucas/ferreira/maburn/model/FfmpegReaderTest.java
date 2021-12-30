package com.lucas.ferreira.maburn.model;

import java.io.IOException;

public class FfmpegReader {
    private FfmpegExecutor ffmpegExecutor = new FfmpegExecutor();

    public VideoComposer readVideoMetadatas() throws IOException {
        ffmpegExecutor.getVideoInfo("D:\\Teste\\Episode 01\\ts-01.ts");
        return null;
    }
}
