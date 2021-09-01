package com.lucas.ferreira.maburn.webserver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebServer extends LocalServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private LocalServer localServer;
	private URL url;
	private List<Handle> handles = new ArrayList<>();

	public WebServer(LocalServer localServer, String host) throws MalformedURLException {
		url = new URL(host);
		this.localServer = localServer;
	}

	public boolean isOpen() {
		try (Socket s = new Socket(LocalServer.HOST, LocalServer.PORT)) {
			return true;
		} catch (IOException ex) {

		}
		return false;
	}

	public boolean start() {
		LOGGER.info("[SERVER LOCAL] - STARTING");
		return localServer.start();
	}

	public boolean stop() {
		LOGGER.info("[SERVER LOCAL] - STOPPING");
		localServer.stop();
		return true;
	}

	public URL getUrl() {
		return url;
	}
	public List<Handle> getHandles() {
		return handles;
	}
	

}
