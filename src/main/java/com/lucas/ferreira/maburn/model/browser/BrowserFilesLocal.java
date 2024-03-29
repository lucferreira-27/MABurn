package com.lucas.ferreira.maburn.model.browser;

import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;

import java.io.File;

public class BrowserFilesLocal {

	private final static String LOCAL_DEFAULT = "DEFAULT\\";
	private final static String WINDOWS_LOCAL = "%USERPROFILE%\\AppData\\Local\\ms-playwright\\";
	private final static String LINUX_LOCAL = "~/Library/Caches/ms-playwright/";
	private final static String MAC_LOCAL = "~/.cache/ms-playwright/";


	
	public String getLocal(Platform platform) {
		String local = customLocal();
		if (!local.equals(LOCAL_DEFAULT)) {
			return local;
		} else {
			local = defaultLocal(platform);
			 File fileLocal =new File(local);
			if (!fileLocal.exists()) {
				fileLocal.mkdirs();
			}
			return defaultLocal(platform);
		}

	}

	private String defaultLocal(Platform platform) {

		if (platform == Platform.WINDOWS_64 || platform == Platform.WINDOWS_32) {
			return WINDOWS_LOCAL.replace("%USERPROFILE%", System.getProperty("user.home"));
		}
		if (platform == Platform.LINUX) {
			return LINUX_LOCAL;
		}
		if (platform == Platform.MAC) {
			return MAC_LOCAL;
		}
		return null;
	}

	private String customLocal() {
		XmlConfigurationOrchestrator configurationOrchestrator = new XmlConfigurationOrchestrator();
		
		try {
			String local = configurationOrchestrator.read().getGeralConfigForm().getBrowserLocal() + "\\";
			return local;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
