package com.lucas.ferreira.maburn.model.download;

public class FileTypeSelect {
    public String selectType(String contentType, String defaultReturn){

        if(contentType.equals("octet-stream")) {
            return "zip";
        }

        return defaultReturn;
    }
}
