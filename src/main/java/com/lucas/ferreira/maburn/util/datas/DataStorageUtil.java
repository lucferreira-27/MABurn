package com.lucas.ferreira.maburn.util.datas;

import java.text.DecimalFormat;

import com.lucas.ferreira.maburn.model.enums.DownloadUnit;

public class DataStorageUtil {
	public static String converterUnit(Double megabytes) {
		DecimalFormat df = new DecimalFormat("###,##0.00");

		if (megabytes > 1024)
			return df.format(megabytes / 1024).replace(",", ".") + " " + DownloadUnit.GIGABYTES_UNIT.getValue();

		if (megabytes < 1)
			return df.format(megabytes * 1024).replace(",", ".") + " " + DownloadUnit.KILOBYTES_UNIT.getValue();

		return megabytes + " " + DownloadUnit.MEGABYTES_UNIT.getValue();
	}

	public static String converterSpeedUnit(Double megabytes) {
		DecimalFormat df = new DecimalFormat("###,##0.00");

		if (megabytes > 1024)
			return df.format(megabytes / 1024).replace(",", ".") + " " + DownloadUnit.GIGABYTES_UNIT.getValue() + "\\s";

		if (megabytes < 1)
			return df.format(megabytes * 1024).replace(",", ".") + " " + DownloadUnit.KILOBYTES_UNIT.getValue() + "\\s";

		return df.format(megabytes).replace(",", ".") + " " + DownloadUnit.MEGABYTES_UNIT.getValue() + "\\s";
	}
}
