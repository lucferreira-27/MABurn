package com.lucas.ferreira.maburn.util;

import java.util.Arrays;
import java.util.List;

public class ModelUtil {
	public static boolean isJUnitTest() {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    List<StackTraceElement> list = Arrays.asList(stackTrace);
	    for (StackTraceElement element : list) {
	        if (element.getClassName().startsWith("org.junit.")) {
	            return true;
	        }           
	    }
	    return false;
	}
}
