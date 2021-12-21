package com.lucas.ferreira.maburn.model.metadata;

import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.Variant;

import java.util.ArrayList;
import java.util.List;

public class MediaM3u8 {
    private Variant variant;
    private MediaPlaylist mediaPlaylist;
    private String baseUrl;

    MediaM3u8(MediaPlaylist mediaPlaylist, Variant variant) {
        this.variant = variant;
        this.mediaPlaylist = mediaPlaylist;
    }

    MediaM3u8(MediaPlaylist mediaPlaylist) {
        this.mediaPlaylist = mediaPlaylist;
    }

    public MediaPlaylist getMediaPlaylist() {
        return mediaPlaylist;
    }

    public Variant getVariant() {
        return variant;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "MediaM3u8{" +
                "variant=" + variant +
                ", mediaPlaylist=" + mediaPlaylist +
                '}';
    }
}
