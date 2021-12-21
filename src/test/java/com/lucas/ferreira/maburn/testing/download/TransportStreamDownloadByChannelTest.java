package com.lucas.ferreira.maburn.testing.download;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;
import com.lucas.ferreira.maburn.controller.title.download.cards.PartDownloadItemsValues;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.channel.TransportStreamDownloadByChannel;
import org.junit.Test;

public class TransportStreamDownloadByChannelTest {
    private ItemDownloadValues itemDownloadValues;

    @Test
    public void downloadTransportStreamTest() throws Exception {
        String baseUrl =  "https://dc2.betterserver.ga/S/Saint_Seiya_The_Lost_Canvas_Meiou_Shinwa/1080p/BA_01.mp4/";
        String tsUrl = "n_0_0_0.ts?wmsAuthSign=c2VydmVyX3RpbWU9MTIvMTcvMjAyMSA3OjA0OjQwIFBNJmhhc2hfdmFsdWU9Mng4VFNGcDJUWHIyeHkwanlZYzFnQT09JnZhbGlkbWludXRlcz0yMTAmc3RybV9sZW49NTg=";
        itemDownloadValues = new PartDownloadItemsValues();
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setFilename("ts-01");
        downloadInfo.setRoot("D:\\Teste\\");
        downloadInfo.setPrefFiletype(FileTypeAccept.TS);
        downloadInfo.setUrl(baseUrl + tsUrl);
        TransportStreamDownloadByChannel transportStreamDownloadByChannel =  new TransportStreamDownloadByChannel(itemDownloadValues);

        transportStreamDownloadByChannel.download(downloadInfo);
    }
}
