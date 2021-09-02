package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;

import java.util.logging.Logger;

public class PlaywrightDownload {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final PlaywrightRepository repository = new PlaywrightRepository();
	private final URLBuilder urlBuilder = new URLBuilder();
	private final UserSystem userSytem = new UserSystem();
	private DownloadByChannel downloadByChannel;
	private final DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();

	public FileDownloadValues download(String local, Browsers browser) throws Exception {
		LOGGER.config("Get Browser download values");
		Platform platform = userSytem.getUserPlataform();
		RepositoryBrowserJson repositoryBrowserJson = repository.requestBrowsersInRepository(browser);
		int build = repositoryBrowserJson.getRevision();
		String url = urlBuilder.getBrowserBuildUrl(build, browser, platform);
		FileDownloadValues fileDownloadValues = new FileDownloadValues();
		printBuildInfo(platform, repositoryBrowserJson, url);
		
		LOGGER.config("Starting Browser Download");
		DownloadInfo downloadInfo = defineDownloadInfo(local,
				browser.name().toLowerCase() + "-" + repositoryBrowserJson.getRevision(), url);
		downloadByChannel = new DownloadByChannel(fileDownloadValues);
		new Thread(() -> {
			try {
				downloadByChannel.download(downloadInfo);
			} catch (Exception e) {
				LOGGER.severe("Error on Browser Download " + e.getMessage());
				e.printStackTrace();
			}
		}).start();

		return fileDownloadValues;

	}

	public void stop() {
		
		LOGGER.config("Stoping Browser Download");
		downloadByChannel.stop();
		LOGGER.config("Browser Download Stopped");

	}

	private DownloadInfo defineDownloadInfo(String local, String filename, String url) {
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename(filename);
		downloadInfo.setPrefFiletype(FileTypeAccept.ZIP);
		downloadInfo.setReferer(url);
		downloadInfo.setRoot(local);
		downloadInfo.setUrl(url);
		return downloadInfo;
	}

	private void printBuildInfo(Platform platform, RepositoryBrowserJson repositoryBrowserJson, String url) {
		LOGGER.info("Platform: " + platform + "\nBrowser: " + repositoryBrowserJson.getName() + "\tBuild: "
				+ repositoryBrowserJson.getRevision() + "\nURL: " + url);
	}

	public void showInfo(FileDownloadValues fileDownloadValues, boolean show) {
		if (show)
			downloadRealTimeInfo.showInfoWithProgress(fileDownloadValues);
		else
			downloadRealTimeInfo.stopShowInfo();
	}

	public void showInfo(FileDownloadValues fileDownloadValues, boolean show, long time) {
		if (show)
			downloadRealTimeInfo.showInfoForTime(fileDownloadValues, time);
		else
			downloadRealTimeInfo.stopShowInfo();
	}
}
