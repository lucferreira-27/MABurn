package com.lucas.ferreira.mangaburn.testing.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.download.service.model.ReadableConsumerByteChannel;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.view.MainInterfaceView;

public class ReadableConsumerByteChannelTest {
	private File file = new File("D:\\One Piece Anitube\\Anime");
	private String url = "https://video.wixstatic.com/video/4f75e1_c816b7423c2b4a6197ae4a8c08c54862/1080p/mp4/file.mp4";

	@Test
	public void download() throws MalformedURLException, IOException {
		MainInterfaceView mainInterfaceView = new MainInterfaceView();
		mainInterfaceView.setVisibility(true);
		mainInterfaceView.initAndShowGUI();
		URLConnection con = new URL(url).openConnection();
		int fileSize = con.getContentLength();
		ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
		ReadableConsumerByteChannel rcbc = new ReadableConsumerByteChannel(rbc, (b) ->{
			CustomLogger.log(b);
		});
		


		FileOutputStream fos = new FileOutputStream(file);

		fos.getChannel().transferFrom(rcbc, 0, Long.MAX_VALUE);
	}
}
