package com.lucas.ferreira.maburn.model.download.channel;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PartDownloadItemsValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.model.FfmpegExecutor;
import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ReadImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.ReadVideoMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TransportStreamDownloadByChannel extends DownloadByChannel {


    public TransportStreamDownloadByChannel(ItemDownloadValues itemDownloadValues) {
        super(itemDownloadValues);
    }

    @Override
    protected void initTransfer() throws IOException {

        super.initTransfer();

    }

    public void findVideoMetadatas() {
        new Thread(() -> {

            VideoMetadata videoMetadata = readVideoMetadatas();

            if (videoMetadata != null && videoMetadata.getHeight() != null && !videoMetadata.getHeight().isEmpty())
                ((PartDownloadItemsValues) itemDownloadValues).getResolution().set((videoMetadata.getHeight()));

        }).start();

    }

    public long readVideoSize() {
        try {
            return Files.size(Paths.get(absolutePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
    public VideoMetadata readVideoMetadatas() {
        try {
            final FfmpegExecutor ffmpegExecutor = new FfmpegExecutor();

            VideoMetadata videoMetadata = ffmpegExecutor.getVideoInfo(absolutePath);
            if(videoMetadata == null){
                videoMetadata = new VideoMetadata();
            }
            videoMetadata.setSize(Files.size(Paths.get(absolutePath)));
            videoMetadata.setType(Files.probeContentType(Paths.get((absolutePath))));
            return videoMetadata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
