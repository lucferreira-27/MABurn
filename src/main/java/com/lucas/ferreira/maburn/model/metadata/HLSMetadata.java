package com.lucas.ferreira.maburn.model.metadata;

import io.lindstrom.m3u8.model.MasterPlaylist;
import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.Variant;
import io.lindstrom.m3u8.parser.MasterPlaylistParser;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;
import io.lindstrom.m3u8.parser.PlaylistParserException;
import jdk.internal.util.xml.impl.Input;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.jetbrains.annotations.NotNull;

import javax.print.attribute.standard.Media;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HLSMetadata {
    private final MediaPlaylistParser mediaParser = new MediaPlaylistParser();
    private final MasterPlaylistParser masterParser = new MasterPlaylistParser();
    private String baseUrl;

    public List<MediaM3u8> getMediaPlaylistFromM3u8(String baseUrl, String m3u8Url) throws Exception {
        this.baseUrl = baseUrl;
        return getMediaPlaylistFromM3u8(m3u8Url);
    }

    private List<MediaM3u8> getMediaPlaylistFromM3u8(String m3u8Url) throws Exception {

        Path tempM3u8 = getTempPatFromUri(m3u8Url);

        if (isMasterPlaylist(tempM3u8)) {
            MasterPlaylist masterPlaylist = masterParser.readPlaylist(tempM3u8);
            List<MediaM3u8> mediaM3u8s = masterPlaylist.variants().stream().map(v -> getMediaM3u8FromVariant(v)).collect(Collectors.toList());
            if(mediaM3u8s.stream().anyMatch(m -> m == null)){
                throw new NullPointerException("MediaM3u8 can't be null");
            }
            return mediaM3u8s;
        }
        MediaPlaylist mediaPlaylist = readTagsFromM3u8(tempM3u8);
        deleteM3u8TempFile(tempM3u8);

        return Arrays.asList(new MediaM3u8(mediaPlaylist));
    }

    private Path getTempPatFromUri(String m3u8Url) throws IOException {
        InputStream in = getM3u8InputStream(baseUrl + m3u8Url);
        Path tempM3u8 = getPathAndCopyInputStream(in);
        return tempM3u8;
    }


    private void deleteM3u8TempFile(Path tempM3u8) {
        File file = tempM3u8.toFile();
        if (file.exists()) {
            file.delete();
        }
    }

    private Path getPathAndCopyInputStream(InputStream in) throws IOException {
        Path tempM3u8 = Files.createTempFile("m3u8-", ".temp");
        tempM3u8.toFile().deleteOnExit();
        Files.copy(in, tempM3u8, StandardCopyOption.REPLACE_EXISTING);
        return tempM3u8;
    }

    private InputStream getM3u8InputStream(String m3u8Url) throws IOException {
        String url = "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/playlist.m3u8?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=";
        System.out.println(m3u8Url);
        System.out.println(url);
        System.out.println(m3u8Url.equals(url)  + (m3u8Url.length() + " " + url.length()));
        HttpURLConnection conn = (HttpURLConnection) new URL(m3u8Url).openConnection();
        conn.setInstanceFollowRedirects(true);  //you still need to handle redirect manully.
        HttpURLConnection.setFollowRedirects(true);
        conn.setRequestProperty("Method", "GET");
        conn.setRequestProperty("Accept","*/*");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
        return conn.getInputStream();
    }

    private boolean isMasterPlaylist(Path m3u8) throws IOException {

        try {
            masterParser.readPlaylist(m3u8);
        } catch (PlaylistParserException e) {
            return false;
        }
        return true;
    }

    private MediaPlaylist readTagsFromM3u8(Path m3u8) throws IOException {

        MediaPlaylist mediaPlaylist = mediaParser.readPlaylist(m3u8);

        return mediaPlaylist;
    }

    private MediaM3u8 getMediaM3u8FromVariant(Variant variant) {
        String variantUri = variant.uri();
        try {
            Path tempM3u8 = getTempPatFromUri(variantUri);
            MediaPlaylist mediaPlaylist = readTagsFromM3u8(tempM3u8);
            MediaM3u8 mediaM3u8 = new MediaM3u8(mediaPlaylist, variant);
            mediaM3u8.setBaseUrl(baseUrl);
            return mediaM3u8;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
