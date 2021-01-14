package com.lucas.ferreira.maburn.util;

import java.io.InputStream;

public class Resources {
	
	public static InputStream getResourceAsStream(String resource) {
		
	    InputStream input = Resources.class.getResourceAsStream("/resources/" + resource);
        if (input == null) {
            // if we are load file within IDE
            input = Resources.class.getClassLoader().getResourceAsStream(resource);
        }
		
		return input;
	}
	
}
