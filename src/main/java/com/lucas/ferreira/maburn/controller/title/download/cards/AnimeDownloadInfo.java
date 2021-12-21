package com.lucas.ferreira.maburn.controller.title.download.cards;

import com.lucas.ferreira.maburn.controller.title.download.register.TaggedItems;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.enums.Definition;
import com.lucas.ferreira.maburn.model.items.CollectionTitle;
import com.lucas.ferreira.maburn.model.metadata.HLSMetadata;
import com.lucas.ferreira.maburn.model.metadata.MediaM3u8;
import com.lucas.ferreira.maburn.model.webscraping.scraping.item.EpisodeScraped;
import com.lucas.ferreira.maburn.util.MapKeyValue;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class AnimeDownloadInfo {

    private TaggedItems taggedItems;

    public AnimeDownloadInfo(TaggedItems taggedItems) {
        this.taggedItems = taggedItems;
    }

    public DownloadInfo newEpisodeDownloadInfo(CollectionTitle collectionTitle, EpisodeScraped episodeScraped) throws Exception {
        String directDownload = episodeScraped.getVideoLinks().get(Definition.DEFINITION_1080);
        if (isHLSVideo(directDownload)) {
            return newEpisodeHLSDownloadInfo(collectionTitle, episodeScraped);
        }
        DownloadInfo downloadInfo = baseEpisodeDownloadInfo(collectionTitle, episodeScraped);
        downloadInfo.setUrl(directDownload);
        return downloadInfo;

    }

    private DownloadInfo newEpisodeHLSDownloadInfo(CollectionTitle collectionTitle, EpisodeScraped episodeScraped) throws Exception {
        String directDownload = episodeScraped.getVideoLinks().get(Definition.DEFINITION_1080);
        DownloadInfo downloadInfo = baseEpisodeDownloadInfo(collectionTitle, episodeScraped);
        List<String> partsUrls = getPartsUrls(directDownload);
        downloadInfo.getListUrls().addAll(partsUrls);
        downloadInfo.setUrl(episodeScraped.getSiteResult().getPageInfo().getUrl());

        return downloadInfo;

    }


    private DownloadInfo baseEpisodeDownloadInfo(CollectionTitle collectionTitle, EpisodeScraped episodeScraped) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setFilename(episodeScraped.getName());
        downloadInfo.setRoot(collectionTitle.getDestination());
        downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
        downloadInfo.setReferer(episodeScraped.getRegisteredSite().getSiteConfig().getHomeUrl());
        return downloadInfo;

    }

    private boolean isHLSVideo(String url) throws IOException {
        try {
            HLSMetadata hlsMetadata = new HLSMetadata();
            String baseUrl = url.substring(0, url.lastIndexOf("/") + 1);
            String m3u8Url = url.substring(url.lastIndexOf("/") + 1);
            List<MediaM3u8> mediaPlaylists = hlsMetadata.getMediaPlaylistFromM3u8(baseUrl, m3u8Url);
            if (mediaPlaylists.size() > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<String> getPartsUrls(String directDownload) throws Exception {
        HLSMetadata hlsMetadata = new HLSMetadata();
        String baseUrl = directDownload.substring(0, directDownload.lastIndexOf("/") + 1);
        String m3u8Url = directDownload.substring(directDownload.lastIndexOf("/") + 1);
        List<MediaM3u8> mediaPlaylists = hlsMetadata.getMediaPlaylistFromM3u8(baseUrl, m3u8Url);
        MediaM3u8 mediaM3u8 = mediaPlaylists.get(0);
        List<String> partsUrls = mediaM3u8.getMediaPlaylist()
                .mediaSegments()
                .stream()
                .map(m -> mediaM3u8.getBaseUrl() + m.uri())
                .collect(Collectors.toList());

        return partsUrls;
    }


}
