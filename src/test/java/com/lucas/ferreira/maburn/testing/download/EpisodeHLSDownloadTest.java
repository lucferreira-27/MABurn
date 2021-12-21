package com.lucas.ferreira.maburn.testing.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.GroupDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PageDownloadItemValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PartDownloadItemsValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.chapter.ChapterDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.episode.EpisodeFragmentedDownloadValues;
import com.lucas.ferreira.maburn.exceptions.ChapterDownloadException;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadProgressState;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.item.ChapterDownload;
import com.lucas.ferreira.maburn.model.download.item.EpisodeHLSDownload;
import com.lucas.ferreira.maburn.model.download.item.PartDownload;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class EpisodeHLSDownloadTest {

    @Test
    public void quick() throws IOException {
       String ss = "D:\\AnimeBurn\\Saint Seiya The Lost Canvas - Meiou Shinwa 2\\ Episódio 01\\ha.mp4";
        File file2 = new File(ss);
        file2.createNewFile();
        file2.delete();

    }
    @Test
    public void quick2() throws IOException {
        String s = "D:\\AnimeBurn\\Saint Seiya The Lost Canvas - Meiou Shinwa 2\\ Episódio 01\\ha.mp4";
        File file1 = new File(s);
        file1.createNewFile();
        file1.delete();

    }
    @Test
    public void shouldDownloadAllPartsTest() {
        GroupDownloadValues episode = new EpisodeFragmentedDownloadValues();

        List<ItemDownloadValues> listItemsDownloadValues = new ArrayList<ItemDownloadValues>();

        List<String> links = Arrays.asList(
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_0_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=",
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_1_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=",
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_2_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=",
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_3_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=",
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_4_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=",
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_5_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=",
                "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/n_6_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg="
);
        links.forEach(link -> {
            PartDownloadItemsValues partDownloadItemsValues = new PartDownloadItemsValues();
            partDownloadItemsValues.getDirectLink().set(link);
            listItemsDownloadValues.add(partDownloadItemsValues);
        });

        episode.getListItemsDownloadValues().addAll(listItemsDownloadValues);
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setFilename("Episode 01");
        downloadInfo.setRoot("D:\\Teste\\");
        downloadInfo.setReferer("https://betteranime.net");
        downloadInfo.setPrefFiletype(FileTypeAccept.MP4);
        EpisodeHLSDownload episodeHLSDownload = new EpisodeHLSDownload(episode, downloadInfo);

        try {
            episodeHLSDownload.showDownloadValuesRealTimeInfo();
            new Thread(() ->{
                try {
                    episodeHLSDownload.download();
                } catch (ChapterDownloadException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();;


            Thread.sleep(100000);
            boolean allMath = episode.getListItemsDownloadValues().stream()
                    .allMatch(part -> part.getDownloadProgressState().get() == DownloadProgressState.COMPLETED);

            assertTrue(allMath);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
