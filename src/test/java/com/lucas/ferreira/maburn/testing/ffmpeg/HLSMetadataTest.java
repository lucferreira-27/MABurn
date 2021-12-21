package com.lucas.ferreira.maburn.testing.ffmpeg;

import com.lucas.ferreira.maburn.model.metadata.HLSMetadata;
import com.lucas.ferreira.maburn.model.metadata.MediaM3u8;
import io.lindstrom.m3u8.model.MediaPlaylist;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class HLSMetadataTest {
    @Test
    public void testParseHLSMetadata() throws Exception {
        HLSMetadata hlsMetadata = new HLSMetadata();
        String baseUrl = "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/";
        String playlistM3u8 = "playlist.m3u8?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=";
        List<MediaM3u8> mediaM3u8s = hlsMetadata.getMediaPlaylistFromM3u8(baseUrl, playlistM3u8);
        mediaM3u8s.get(0).getMediaPlaylist().mediaSegments().forEach(m -> System.out.println(baseUrl + m.uri()));
        assertNotNull(mediaM3u8s);

    }
}
