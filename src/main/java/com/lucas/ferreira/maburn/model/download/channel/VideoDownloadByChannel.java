package com.lucas.ferreira.maburn.model.download.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeDownloadItemValues;
import com.lucas.ferreira.maburn.exceptions.FileMetadataNotFound;
import com.lucas.ferreira.maburn.model.metadata.ReadMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.image.ReadImageMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.ReadVideoMetadata;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;

public class VideoDownloadByChannel extends DownloadByChannel {

	private EpisodeDownloadItemValues episodeDownloadItemValues;
	private VideoMetadata videoMetadata;

	public VideoDownloadByChannel(EpisodeDownloadItemValues episodeDownloadItemValues) {
		super(episodeDownloadItemValues);
		this.episodeDownloadItemValues = episodeDownloadItemValues;
	}

	@Override
	protected void initTransfer() throws IOException {

		findVideoMetadatas();

		super.initTransfer();
	}

	public void findVideoMetadatas() {
		new Thread(() -> {
			waitUntilVideoFileExist();

			readVideoMetadatas();
			
			if(videoMetadata == null || videoMetadata.getHeight() == null) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			readVideoMetadatas();

			if (videoMetadata != null && videoMetadata.getHeight() != null && !videoMetadata.getHeight().isEmpty())
				episodeDownloadItemValues.getResolution().set((videoMetadata.getHeight()));

		}).start();

	}

	public VideoMetadata readVideoMetadatas() {
		ReadMetadata readVideoMetadata = new ReadVideoMetadata();
		try {
			return videoMetadata = (VideoMetadata) readVideoMetadata.readTargetMetada(absolutePath);

		} catch (FileNotFoundException | FileMetadataNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void waitUntilVideoFileExist() {
		File file;
		do {
			file = new File(absolutePath);
		} while (!file.exists());
	}

}
