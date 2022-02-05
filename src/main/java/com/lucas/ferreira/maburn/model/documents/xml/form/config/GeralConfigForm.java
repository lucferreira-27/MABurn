package com.lucas.ferreira.maburn.model.documents.xml.form.config;

public class GeralConfigForm {
    private Integer parallelDownloads = new Integer(3);
    private String browserLocal = "DEFAULT";
    private Boolean browserHeadless = true;
    private Long scrapingTimeout = 30000L;

    public Integer getParallelDownloads() {
        return parallelDownloads;
    }

    public void setParallelDownloads(Integer parallelDownloads) {
        this.parallelDownloads = parallelDownloads;
    }

    public String getBrowserLocal() {
        return browserLocal;
    }

    public void setBrowserLocal(String browserLocal) {
        this.browserLocal = browserLocal;
    }

    public Boolean getBrowserHeadless() {
        return browserHeadless;
    }

    public void setBrowserHeadless(Boolean browserHeadless) {
        this.browserHeadless = browserHeadless;
    }

    public Long getScrapingTimeout() {
        return scrapingTimeout;
    }
    public void setScrapingTimeout(Long scrapingTimeout) {
        this.scrapingTimeout = scrapingTimeout;
    }
}
