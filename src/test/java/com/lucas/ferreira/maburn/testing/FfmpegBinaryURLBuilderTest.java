package com.lucas.ferreira.maburn.testing;
import com.lucas.ferreira.maburn.model.browser.Platform;
import com.lucas.ferreira.maburn.model.browser.ffmpeg.FfmpegBinaryURLBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FfmpegBinaryURLBuilderTest {

    @Test
    public void testBuildUrlWindows64() throws IOException {
        FfmpegBinaryURLBuilder ffmpegBinaryDownloadTest = new FfmpegBinaryURLBuilder();
        String expect = "https://github.com/ffbinaries/ffbinaries-prebuilt/releases/download/v4.1/ffmpeg-4.1-win-64.zip";
        String result = ffmpegBinaryDownloadTest.buildUrl(Platform.WINDOWS_64);
        assertEquals(expect, result);

    }
    @Test
    public void testBuildUrlWindows32() throws IOException {
        FfmpegBinaryURLBuilder ffmpegBinaryDownloadTest = new FfmpegBinaryURLBuilder();
        String expect = "https://github.com/ffbinaries/ffbinaries-prebuilt/releases/download/v4.1/ffmpeg-4.1-win-32.zip";
        String result = ffmpegBinaryDownloadTest.buildUrl(Platform.WINDOWS_32);
        assertEquals(expect, result);

    }
    @Test
    public void testBuildUrlLinux() throws IOException {
        FfmpegBinaryURLBuilder ffmpegBinaryDownloadTest = new FfmpegBinaryURLBuilder();
        String expect = "https://github.com/ffbinaries/ffbinaries-prebuilt/releases/download/v4.1/ffmpeg-4.1-linux-64.zip";
        String result = ffmpegBinaryDownloadTest.buildUrl(Platform.LINUX);
        assertEquals(expect, result);

    }
    @Test
    public void testBuildUrlMac() throws IOException {
        FfmpegBinaryURLBuilder ffmpegBinaryDownloadTest = new FfmpegBinaryURLBuilder();
        String expect = "https://github.com/ffbinaries/ffbinaries-prebuilt/releases/download/v4.1/ffmpeg-4.1.7-osx-64.zip";
        String result = ffmpegBinaryDownloadTest.buildUrl(Platform.MAC);
        assertEquals(expect, result);

    }
}
