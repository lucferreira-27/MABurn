package com.lucas.ferreira.maburn.model.metadata;

import io.lindstrom.m3u8.model.MasterPlaylist;
import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.Resolution;
import io.lindstrom.m3u8.model.Variant;
import io.lindstrom.m3u8.parser.MasterPlaylistParser;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;
import io.lindstrom.m3u8.parser.PlaylistParserException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HLSMetadata {

    private static final String M3U8_MIME_TYPE = "application/vnd.apple.mpegurl";

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
            if (mediaM3u8s.stream().anyMatch(m -> m == null)) {
                throw new NullPointerException("MediaM3u8 can't be null");
            }
            Collections.sort(mediaM3u8s,(m1, m2) -> {
                Resolution r1 = m1.getVariant().resolution().get();
                Resolution r2 = m2.getVariant().resolution().get();
                return r2.height() - r1.height();
            });

            return mediaM3u8s;
        }
        MediaPlaylist mediaPlaylist = readTagsFromM3u8(tempM3u8);
        deleteM3u8TempFile(tempM3u8);

        return Arrays.asList(new MediaM3u8(mediaPlaylist));
    }

    private Path getTempPatFromUri(String m3u8Url) throws IOException {
        String url = m3u8Url.startsWith("https:") ? m3u8Url : baseUrl + m3u8Url;
        InputStream in = getM3u8InputStream(url);
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
        HttpURLConnection conn = (HttpURLConnection) new URL(m3u8Url).openConnection();
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

    public static boolean isHLSVideo(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        String contentType = conn.getContentType();
        System.out.println(contentType);
        return contentType.equals(M3U8_MIME_TYPE);
    }

}
