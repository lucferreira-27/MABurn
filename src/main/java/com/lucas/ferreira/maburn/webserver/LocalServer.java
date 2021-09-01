package com.lucas.ferreira.maburn.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class LocalServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static HttpServer server;
	public static final String HOST = "localhost";
	public static final int PORT = 8001;
	private static WebServer webServer;
	private ThreadPoolExecutor threadPoolExecutor;
	
	public  WebServer create() throws IOException {
		 threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		if(server != null) {
			throw new IllegalAccessError("Server already created! \n Stop the server first!");
		}
		LOGGER.info("[SERVER LOCAL] - CREATING");
		server = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);
		HttpContext contextInit =  server.createContext(InitHandle.PATH, new InitHandle());
		HttpContext contextEnd = server.createContext(EndHandle.PATH, new EndHandle());
		
		server.setExecutor(threadPoolExecutor);
		webServer = new WebServer(this,"http://" + HOST + ":" + PORT);
		webServer.getHandles().add((Handle) contextInit.getHandler());
		webServer.getHandles().add((Handle) contextEnd.getHandler());

		return  webServer;
	}
	
	
	
	protected boolean start() {
		try {
			server.start();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	protected  boolean stop() {
		try {
			
			server.stop(0);
			threadPoolExecutor.shutdownNow();
			server = null;
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	protected  boolean stop(int code) {
		try {

			server.stop(code);
			threadPoolExecutor.shutdownNow();
			server = null;
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	public static WebServer getWebServer() {
		return webServer;
	}
}
