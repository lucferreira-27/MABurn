package com.lucas.ferreira.maburn.controller.title.download.cards;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.download.DownloadProgressState;

import javafx.scene.Node;

public class DownloadStateStyle {
	private static final String STATE_CLASS  = "downloadcard-state";
	private static final String STYLE_DOWNLOADING  = STATE_CLASS + "-downloading";
	private static final String STYLE_COMPLETED  = STATE_CLASS + "-completed";
	private static final String STYLE_FAILED  = STATE_CLASS + "-failed";
	private static final String STYLE_WAITING = STATE_CLASS + "-waiting";
	private static final String STYLE_PAUSE = STATE_CLASS + "-pause";
	private static final String STYLE_CANCELED = STATE_CLASS + "-canceled";

	private Map<DownloadProgressState, String> styles = new HashMap<>();
	
	{
		styles.put(DownloadProgressState.DOWNLOADING, STYLE_DOWNLOADING);
		styles.put(DownloadProgressState.COMPLETED, STYLE_COMPLETED);
		styles.put(DownloadProgressState.FAILED, STYLE_FAILED);
		styles.put(DownloadProgressState.WAITING, STYLE_WAITING);
		styles.put(DownloadProgressState.PAUSE, STYLE_PAUSE);
		styles.put(DownloadProgressState.CANCELED, STYLE_CANCELED);
		styles.put(DownloadProgressState.EXTRACTING, STYLE_WAITING);


	}
	
	
	public void setNodeStyleByState(DownloadProgressState state, Node node) {
		
		String style = styles.get(state);
		
		setNodeStyle(style, node);
	}

	private void setNodeStyle(String style,Node node) {
		node.getStyleClass().setAll(style);

	}


}
