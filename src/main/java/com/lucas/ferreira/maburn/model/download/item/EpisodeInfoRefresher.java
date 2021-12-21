package com.lucas.ferreira.maburn.model.download.item;

import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeFragmentedDownloadValues;

public class EpisodeInfoRefresher implements InfoRefresher {
    private EpisodeFragmentedDownloadValues episodeDownloadItemValues;

    public EpisodeInfoRefresher(EpisodeFragmentedDownloadValues episodeDownloadItemValues) {
        this.episodeDownloadItemValues = episodeDownloadItemValues;
    }

    @Override
    public void increaseAmount() {
        int total = episodeDownloadItemValues.getTotalItemsDownloaded().get();
        int incresedTotal = total + 1;
        episodeDownloadItemValues.getTotalItemsDownloaded().set(incresedTotal);
        refreshEpisodeTotalParts(incresedTotal);
    }

    @Override
    public void decreaseAmount() {
        int total = episodeDownloadItemValues.getTotalItemsDownloaded().get();
        if (total > 0) {
            episodeDownloadItemValues.getTotalItemsDownloaded().set(total - 1);
            refreshEpisodeTotalParts(total);
        }
    }

    @Override
    public void increaseFinalSize(double size) {
        double currentSize = episodeDownloadItemValues.getEpisodeSize().get();
        double newSize = currentSize + size;
        refreshEpisodeSize(newSize);
    }

    @Override
    public void refreshProgress() {
        double numberOfPartsDownloaded = episodeDownloadItemValues.getTotalItemsDownloaded().doubleValue();
        int totalParts = episodeDownloadItemValues.getListItemsDownloadValues().size();

        episodeDownloadItemValues.getDownloadProgress().set(numberOfPartsDownloaded / totalParts);
    }

    private void refreshEpisodeSize(double newSize) {
        episodeDownloadItemValues.getEpisodeSize().set(newSize);
    }

    private void refreshEpisodeTotalParts(int newTotal) {
        episodeDownloadItemValues.getTotalItemsDownloaded().set(newTotal);

    }
}
