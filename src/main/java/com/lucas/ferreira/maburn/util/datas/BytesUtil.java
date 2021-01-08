package com.lucas.ferreira.maburn.util.datas;

import java.text.DecimalFormat;

public class BytesUtil {
	private final static int BYTES_MEGASBYTES = 1048576;
	public static double convertBytesToMegasBytes(long bytes) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		
		return Double.parseDouble(df.format(((double) bytes / BYTES_MEGASBYTES)).replace(",", "."));
	}
	
	public static long convertMegasBytesToBytes(double megabytes) {
		
		return (long) (megabytes * BYTES_MEGASBYTES);
	}
}
