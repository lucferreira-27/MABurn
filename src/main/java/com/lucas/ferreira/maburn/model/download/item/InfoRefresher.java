package com.lucas.ferreira.maburn.model.download.item;

public interface InfoRefresher {
     void increaseAmount();

     void decreaseAmount();

     void increaseFinalSize(double size);

     void refreshProgress();
}

