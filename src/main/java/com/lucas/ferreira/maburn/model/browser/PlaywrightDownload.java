package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class PlaywrightDownload {
	private PlaywrightRepository repository = new PlaywrightRepository();
	private URLBuilder urlBuilder = new URLBuilder();
	private UserSystem userSytem = new UserSystem();
	private FileDownloadValues fileDownloadValues = new FileDownloadValues();
	private DownloadByChannel downloadByChannel = new DownloadByChannel(fileDownloadValues);
	private DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();

	public FileDownloadValues download(String local, Browsers browser) throws Exception {
		Platform platform = userSytem.getUserPlataform();
		RepositoryBrowserJson repositoryBrowserJson = repository.requestBrowsersInRespository(browser);
		int build = repositoryBrowserJson.getRevision();
		String url = urlBuilder.getBrowserBuildUrl(build, browser, platform);

		printBuildInfo(platform, repositoryBrowserJson, url);

		DownloadInfo downloadInfo = defineDownloadInfo(local, browser.name().toLowerCase() + "-" + repositoryBrowserJson.getRevision(), url);

		new Thread(() -> {
			try {
				downloadByChannel.download(downloadInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return fileDownloadValues;

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
		CustomLogger.log("Platfrom: " + platform);
		CustomLogger
				.log("Browser: " + repositoryBrowserJson.getName() + "\tBuild: " + repositoryBrowserJson.getRevision());
		CustomLogger.log("URL: " + url);
	}

	public void showInfo(boolean show) {
		if (show)
			downloadRealTimeInfo.showInfoWithProgress(fileDownloadValues);
		else
			downloadRealTimeInfo.stopShowInfo();
	}

	public void showInfo(boolean show, long time) {
		if (show)
			downloadRealTimeInfo.showInfoForTime(fileDownloadValues, time);
		else
			downloadRealTimeInfo.stopShowInfo();
	}
}
