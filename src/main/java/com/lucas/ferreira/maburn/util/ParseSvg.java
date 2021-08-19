package com.lucas.ferreira.maburn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.batik.transcoder.TranscoderInput;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ParseSvg {
	public Image parse(InputStream file) throws Exception {
		
		BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
		TranscoderInput transIn = new TranscoderInput(file);
		transcoder.transcode(transIn, null);
		Image img = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);

		return img;
	}

}
