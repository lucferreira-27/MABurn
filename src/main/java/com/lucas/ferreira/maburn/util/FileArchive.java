package com.lucas.ferreira.maburn.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileArchive {
	public boolean isArchive(File f) throws IOException {
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "r");
			long n = raf.readInt();

			raf.close();
			if (n == 0x504B0304)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
}
