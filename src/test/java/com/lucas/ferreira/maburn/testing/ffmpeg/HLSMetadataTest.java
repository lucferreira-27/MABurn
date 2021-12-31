package com.lucas.ferreira.maburn.testing.ffmpeg;

import com.lucas.ferreira.maburn.controller.title.download.cards.AnimeDownloadInfo;
import com.lucas.ferreira.maburn.model.metadata.HLSMetadata;
import com.lucas.ferreira.maburn.model.metadata.MediaM3u8;
import io.lindstrom.m3u8.model.MediaPlaylist;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    @Test
    public void testParseHLSMetadata2() throws Exception {
        HLSMetadata hlsMetadata = new HLSMetadata();
        String baseUrl = "https://v.vrv.co/evs1/8aff4f7a263e9b82b1c6b5d7f71d4bb7/assets/ccfdd70c2b7d16b31d356c4c34aa29fb_,3188073.mp4,3188077.mp4,3188069.mp4,3188061.mp4,3188065.mp4,.urlset/";
        String playlistM3u8 = "master.m3u8?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cCo6Ly92LnZydi5jby9ldnMxLzhhZmY0ZjdhMjYzZTliODJiMWM2YjVkN2Y3MWQ0YmI3L2Fzc2V0cy9jY2ZkZDcwYzJiN2QxNmIzMWQzNTZjNGMzNGFhMjlmYl8sMzE4ODA3My5tcDQsMzE4ODA3Ny5tcDQsMzE4ODA2OS5tcDQsMzE4ODA2MS5tcDQsMzE4ODA2NS5tcDQsLnVybHNldC9tYXN0ZXIubTN1OCIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTY0MDg0MzI1NH19fV19&Signature=KHbNt7O7lhKHOhNFZI0xpVYRi3O16vCO8niRihRtkK~5wgYw4~FAjU~C6SxvfhXmxTbml9XahnTBTFPk1I1AVimmbU7bQpJJtiueUUDMwtCR67m8pYvzlvDIpXH1vR0~snqRW-DYhpU-6Aglsa89E3maSsqPoLJNlr3SSELDLZ6vlIvbbQ61OifmG2rDmxsLQ7KvhbJsyYfXlPftKit2ujQ2eNpewahwvxLHK1HcDyIJfesSMO32kJd9amYA62KoE5emZpwMyCOw0~3SiEgLjGsWsmBLQ9wptqK-oGRdjhLg-3llfg4VR4s2eys69A4uK6S~YowZf2FBwQl2CeoHpg__&Key-Pair-Id=APKAJMWSQ5S7ZB3MF5VA";
        List<MediaM3u8> mediaM3u8s = hlsMetadata.getMediaPlaylistFromM3u8(baseUrl, playlistM3u8);
        mediaM3u8s.get(0).getMediaPlaylist().mediaSegments().forEach(m -> System.out.println( m.uri()));
        System.out.println();
        assertNotNull(mediaM3u8s);
    }
    @Test
    public void testIsHLS() throws IOException {
        String baseUrl = "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/";
        String playlistM3u8 = "playlist.m3u8?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=";
        boolean result = HLSMetadata.isHLSVideo(baseUrl + playlistM3u8);
        assertTrue(result);
    }
    @Test
    public void testIsHLS2() throws IOException {
        String baseUrl = "https://v.vrv.co/evs1/8aff4f7a263e9b82b1c6b5d7f71d4bb7/assets/ccfdd70c2b7d16b31d356c4c34aa29fb_,3188073.mp4,3188077.mp4,3188069.mp4,3188061.mp4,3188065.mp4,.urlset/";
        String playlistM3u8 = "master.m3u8?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cCo6Ly92LnZydi5jby9ldnMxLzhhZmY0ZjdhMjYzZTliODJiMWM2YjVkN2Y3MWQ0YmI3L2Fzc2V0cy9jY2ZkZDcwYzJiN2QxNmIzMWQzNTZjNGMzNGFhMjlmYl8sMzE4ODA3My5tcDQsMzE4ODA3Ny5tcDQsMzE4ODA2OS5tcDQsMzE4ODA2MS5tcDQsMzE4ODA2NS5tcDQsLnVybHNldC9tYXN0ZXIubTN1OCIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTY0MDg0MzI1NH19fV19&Signature=KHbNt7O7lhKHOhNFZI0xpVYRi3O16vCO8niRihRtkK~5wgYw4~FAjU~C6SxvfhXmxTbml9XahnTBTFPk1I1AVimmbU7bQpJJtiueUUDMwtCR67m8pYvzlvDIpXH1vR0~snqRW-DYhpU-6Aglsa89E3maSsqPoLJNlr3SSELDLZ6vlIvbbQ61OifmG2rDmxsLQ7KvhbJsyYfXlPftKit2ujQ2eNpewahwvxLHK1HcDyIJfesSMO32kJd9amYA62KoE5emZpwMyCOw0~3SiEgLjGsWsmBLQ9wptqK-oGRdjhLg-3llfg4VR4s2eys69A4uK6S~YowZf2FBwQl2CeoHpg__&Key-Pair-Id=APKAJMWSQ5S7ZB3MF5VA";
        boolean result = HLSMetadata.isHLSVideo(baseUrl + playlistM3u8);
        assertTrue(result);
    }
}
