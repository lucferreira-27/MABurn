package com.lucas.ferreira.maburn.model.download.channel;

import com.lucas.ferreira.maburn.controller.title.download.cards.ItemDownloadValues;

import java.io.IOException;

public class TransportStreamDownloadByChannel extends DownloadByChannel{

    public TransportStreamDownloadByChannel(ItemDownloadValues itemDownloadValues) {
        super(itemDownloadValues);
    }
    @Override
    protected void initTransfer() throws IOException {

        super.initTransfer();


    }

}
