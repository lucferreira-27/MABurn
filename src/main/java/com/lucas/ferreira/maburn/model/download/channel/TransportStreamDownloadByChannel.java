package com.lucas.ferreira.maburn.model.download.channel;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ReadImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.ReadVideoMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TransportStreamDownloadByChannel extends DownloadByChannel{

    public TransportStreamDownloadByChannel(ItemDownloadValues itemDownloadValues) {
        super(itemDownloadValues);
    }
    @Override
    protected void initTransfer() throws IOException {

        super.initTransfer();

    }
    public VideoMetadata readVideoMetadatas()  {
        try {
            VideoMetadata videoMetadata = new VideoMetadata();
            videoMetadata.setSize(Files.size(Paths.get(absolutePath)));
            return videoMetadata;
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return null;
    }

}
