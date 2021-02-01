package com.lucas.ferreira.maburn.util.datas;

import com.lucas.ferreira.maburn.util.MathUtil;

public class BytesUtil {
	private final static int BYTES_MEGASBYTES = 1048576;
	public static double convertBytesToMegasBytes(long bytes) {
		
		
		return MathUtil.roundDouble(((double) bytes / BYTES_MEGASBYTES),2);
	}
	
	public static long convertMegasBytesToBytes(double megabytes) {
		
		return (long) (megabytes * BYTES_MEGASBYTES);
	}
}
