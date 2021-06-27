package com.lucas.ferreira.maburn.model;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ClipboardSystem {
	public static void setContent(String txt) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(txt);
		clipboard.setContent(content);
	}
}
