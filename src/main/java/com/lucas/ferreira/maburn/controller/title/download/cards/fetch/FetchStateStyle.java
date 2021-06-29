package com.lucas.ferreira.maburn.controller.title.download.cards.fetch;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;

public class FetchStateStyle {
	private static final String STATE_CLASS  = "fetchcard-state";
	private static final String STYLE_READY  = STATE_CLASS + "-ready";
	private static final String STYLE_IN_QUEUE  = STATE_CLASS + "-queue";
	private static final String STYLE_WORKING  = STATE_CLASS + "-working";
	private static final String STYLE_ERROR = STATE_CLASS + "-error";
	private Map<FetchCardState, String> styles = new HashMap<>();
	
	{
		styles.put(FetchCardState.READY, STYLE_READY);
		styles.put(FetchCardState.IN_QUEUE, STYLE_IN_QUEUE);
		styles.put(FetchCardState.WORKING, STYLE_WORKING);
		styles.put(FetchCardState.ERROR, STYLE_ERROR);

	}
	
	
	public void setNodeStyleByState(FetchCardState state, Node node) {
		
		String style = styles.get(state);
		setNodeStyle(style, node);
	}

	private void setNodeStyle(String style,Node node) {
		node.getStyleClass().setAll(style);

	}

}
