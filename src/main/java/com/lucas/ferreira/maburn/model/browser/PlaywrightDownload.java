package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.browser.ffmpeg.FfmpegBinaryURLBuilder;
import com.lucas.ferreira.maburn.model.download.DownloadInfo;
import com.lucas.ferreira.maburn.model.download.DownloadRealTimeInfo;
import com.lucas.ferreira.maburn.model.download.FileDownloadValues;
import com.lucas.ferreira.maburn.model.download.FileTypeAccept;
import com.lucas.ferreira.maburn.model.download.channel.DownloadByChannel;

import java.io.IOException;
import java.util.logging.Logger;

public class PlaywrightDownload {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final PlaywrightRepository repository = new PlaywrightRepository();
	private final FfmpegBinaryURLBuilder ffmpegBinaryURLBuilder = new FfmpegBinaryURLBuilder();
	private final URLBuilder urlBuilder = new URLBuilder();
	private final UserSystem userSytem = new UserSystem();
	private DownloadByChannel downloadByChannel;
	private final DownloadRealTimeInfo downloadRealTimeInfo = new DownloadRealTimeInfo();

	public FileDownloadValues download(String local, Binaries browser) throws Exception {
		LOGGER.config("Get binary download values, " + "{"+browser.name().toUpperCase()+"}");
		Platform platform = userSytem.getUserPlataform();

		if(browser == Binaries.FFMPEG_COMPLETE){
			LOGGER.config("Starting Ffmpeg Download");
			FileDownloadValues fileDownloadValues = downloadBinaryFfmpeg(local,platform);
			return fileDownloadValues;
		}

		LOGGER.config("Starting Browser Download");
		FileDownloadValues fileDownloadValues = downloadBinaryBrowser(local,browser,platform);

		return fileDownloadValues;

	}
	private FileDownloadValues downloadBinaryFfmpeg (String local, Platform platform) throws IOException {
		String url = ffmpegBinaryURLBuilder.buildUrl(platform);
		String filename = FfmpegBinaryURLBuilder.FILE_NAME;
		return initDownload(local,filename,url);
	}
	private FileDownloadValues downloadBinaryBrowser (String local,Binaries browser, Platform platform) throws Exception {
		RepositoryBrowserJson repositoryBrowserJson = repository.requestBrowsersInRepository(browser);
		int build = repositoryBrowserJson.getRevision();
		String url = urlBuilder.getBrowserBuildUrl(build, browser, platform);
		printBuildInfo(platform, repositoryBrowserJson, url);
		String filename = browser.name().toLowerCase() + "-" + repositoryBrowserJson.getRevision();
		return initDownload(local,filename,url);
	}

	private void printBuildInfo(Platform platform, RepositoryBrowserJson repositoryBrowserJson, String url) {
		LOGGER.info("Platform: " + platform + "\nBrowser: " + repositoryBrowserJson.getName() + "\tBuild: "
				+ repositoryBrowserJson.getRevision() + "\nURL: " + url);
	}

	private FileDownloadValues initDownload(String local,String filename,String url){
		FileDownloadValues fileDownloadValues = new FileDownloadValues();

		DownloadInfo downloadInfo = defineDownloadInfo(local,filename, url);
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
	private DownloadInfo defineDownloadInfo(String local, String filename, String url) {
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setFilename(filename);
		downloadInfo.setPrefFiletype(FileTypeAccept.ZIP);
		downloadInfo.setReferer(url);
		downloadInfo.setRoot(local);
		downloadInfo.setUrl(url);
		return downloadInfo;
	}
	public void stop() {
		
		LOGGER.config("Stoping Binary Download");
		downloadByChannel.stop();
		LOGGER.config("Binary Download Stopped");

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
