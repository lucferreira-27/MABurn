package com.lucas.ferreira.maburn.model;

import java.util.logging.Logger;

import com.lucas.ferreira.maburn.model.browser.Platform;

public class UserSystem {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static String OS = System.getProperty("os.name").toLowerCase();
	private final static String MODEL = System.getProperty("sun.arch.data.model");
	private String testOs = null;
	private String testModel = null;

	public UserSystem() {

	}

	public UserSystem(String testOs, String testModel) {
		this.testOs = testOs;
		this.testModel = testModel;
	}
	public UserSystem(String testOs) {
		this.testOs = testOs;
	}
	public Platform getUserPlataform() {

		if (isWindows()) {
			if (is64Bits()) {
				return Platform.WINDOWS_64;
			} else if (is32Bits()) {
				return Platform.WINDOWS_32;
			}
			LOGGER.warning("Platform Windowns, but unknow model. Returning default");
			return Platform.WINDOWS_32;
		}
		if (isMac()) {
			return Platform.MAC;
		}
		if (isLinux()) {
			return Platform.LINUX;
		}
		LOGGER.severe("Platform not found");

		return null;
	}

	public boolean isWindows() {
		if (testOs == null)
			return OS.contains("win");
		else
			return testOs.contains("win");
	}

	public boolean isMac() {
		if (testOs == null)
			return OS.contains("mac");
		else
			return testOs.contains("mac");
	}

	public boolean isLinux() {
		if (testOs == null)
			return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
		else
			return (testOs.contains("nix") || testOs.contains("nux") || testOs.contains("aix"));
	}

	private boolean is32Bits() {
		if (testModel == null)
			if (MODEL.equals("32")) {
				return true;
			} else {
				if (testModel.equals("32")) {
					return true;
				}
			}
		return false;
	}

	private boolean is64Bits() {
		if (testModel == null) {
			if (MODEL.equals("64")) {
				return true;
			} 
		}else {
				if (testModel.equals("64")) {
					return true;
				}
			}
		return false;
	}

}
