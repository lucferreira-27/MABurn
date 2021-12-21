package com.lucas.ferreira.maburn.testing.ffmpeg;

import com.lucas.ferreira.maburn.exceptions.VideoComposerException;
import com.lucas.ferreira.maburn.model.VideoComposer;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class VideoComposerTest {
    @Test
    public void testComposer() throws VideoComposerException, IOException {
        VideoComposer videoComposer = new VideoComposer();
        File[] files = new File[]{
                new File("C:\\Users\\lucfe\\AppData\\Local\\Temp\\TS-FILES-6683669167614583362\\ts-0.ts"),
                new File("C:\\Users\\lucfe\\AppData\\Local\\Temp\\TS-FILES-6683669167614583362\\ts-1.ts"),
                new File("C:\\Users\\lucfe\\AppData\\Local\\Temp\\TS-FILES-6683669167614583362\\ts-2.ts"),
        };

        File file = videoComposer.compose(Arrays.asList(files), "output.mp4");
        file.deleteOnExit();

       assertTrue(file.exists());

    }
}
