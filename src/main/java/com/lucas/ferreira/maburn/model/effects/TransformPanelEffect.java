package com.lucas.ferreira.maburn.model.effects;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TransformPanelEffect {

	private Pane pane;

	public Pane addEffect(Pane panel, TransformEffects effect) {
		switch (effect) {
		case BORDER_IMAGE:
			this.pane = borderEffect(panel);
			break;
		case BRIGHTNESS:

			break;
		case SHADOW:
			shadowEffect(panel);
			break;
		default:
			break;
		}
		return pane;
	}

	private Pane borderEffect(Pane panel) {
		panel.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

			panel.setStyle("-fx-border-color: red; -fx-border-width: 2px 2px 2px 2px;");

		});

		panel.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			panel.setStyle("-fx-border-color: transparent; -fx-border-width: 2px 2px 2px 2px;");

		});
		return panel;
	}

	private Pane shadowEffect(Pane panel) {
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setOffsetX(0);
		innerShadow.setOffsetY(20);
		innerShadow.setColor(Color.rgb(0, 0, 0, 0.56));

		panel.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			innerShadow.setColor(Color.rgb(0, 0, 0, 0.66));

			innerShadow.setOffsetY(30);
			panel.setEffect(innerShadow);


		});

		panel.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			innerShadow.setColor(Color.rgb(0, 0, 0, 0.56));

			innerShadow.setOffsetY(20);
			panel.setEffect(innerShadow);

		});

		panel.setEffect(innerShadow);
		return panel;
	}
}
