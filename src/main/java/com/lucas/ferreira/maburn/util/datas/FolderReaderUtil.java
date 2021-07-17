package com.lucas.ferreira.maburn.util.datas;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FolderReaderUtil {
	public static boolean isImage(File file) {
		
		try {
		
			if (ImageIO.read(file) != null) {
				return true;
			} else {
				return false;
			}
		
		} catch (IOException e) {
			
			return false;

		}

	}
}
