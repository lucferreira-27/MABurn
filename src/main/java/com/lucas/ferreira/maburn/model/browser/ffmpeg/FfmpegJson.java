package com.lucas.ferreira.maburn.model.browser.ffmpeg;

import java.util.HashMap;
import java.util.Map;

public class FfmpegJson {
    private String platformName;
    private Map<String, String> binaries = new HashMap<>();


    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Map<String, String> getBinaries() {
        return binaries;
    }
}
