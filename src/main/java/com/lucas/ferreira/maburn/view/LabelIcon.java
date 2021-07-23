package com.lucas.ferreira.maburn.view;

import java.util.function.Consumer;

import com.lucas.ferreira.maburn.model.ClipboardSystem;
import com.lucas.ferreira.maburn.util.Icon;

import javafx.scene.control.Label;

public class LabelIcon {
	private Icon icon;
	private Label label;
	private boolean visible;

	public LabelIcon(Icon icon, Label label) {
		this.icon = icon;
		icon.removeListenerOnHover();
		this.label = label;
	}

	public void setOnMouseClickIcon(Consumer<Icon> onIcon) {
		label.onMouseClickedProperty().addListener((obs, oldvalue, newvalue) -> {
			onIcon.accept(icon);
		});

	}

	public void setOnMouseClickLabel(Consumer<Label> onLabel) {
		label.onMouseClickedProperty().addListener((obs, oldvalue, newvalue) -> {
			onLabel.accept(label);
		});

	}

	public void setOnMousePressedIcon(Consumer<Icon> onIcon) {
		label.setOnMousePressed(event -> {
			onIcon.accept(icon);
		});

	}

	public void setOnMousePressedLabel(Consumer<Label> onLabel) {
		label.setOnMousePressed(event -> {
			onLabel.accept(label);
		});

	}

	public void setOnMouseReleasedIcon(Consumer<Icon> onIcon) {

		label.setOnMouseReleased(event -> {
			onIcon.accept(icon);
		});

	}

	public void setOnMouseReleasedLabel(Consumer<Label> onLabel) {

		label.setOnMouseReleased(event -> {
			onLabel.accept(label);
		});

	}

	public void setOnMouseEnteredIcon(Consumer<Icon> onIcon) {
		label.setOnMouseEntered(event -> {
			onIcon.accept(icon);
		});

	}

	public void setOnMouseEnteredLabel(Consumer<Label> onLabel) {
		label.setOnMouseEntered(event -> {
			onLabel.accept(label);
		});

	}

	public void setOnMouseExitedIcon(Consumer<Icon> onIcon) {
		label.setOnMouseExited(event -> {
			onIcon.accept(icon);
		});

	}

	public void setOnMouseExitedLabel(Consumer<Label> onLabel) {
		label.setOnMouseExited(event -> {
			onLabel.accept(label);
		});

	}

	public void setOnMouseHover(Consumer<Label> onLabelIsHover, Consumer<Label> onLabelIsNotHover) {
		label.hoverProperty().addListener((obs, oldvalue, newvalue) -> {
			if (label.isHover()) {
				onLabelIsHover.accept(label);
			} else {
				onLabelIsNotHover.accept(label);
			}
		});
	}

	public void alterIconColor() {
		icon.swithIconColor();
	}

	public static void underline(Label label, boolean underline) {
		label.setUnderline(underline);
	}

	public static void copyText(Label label) {
		ClipboardSystem.setContent(label.getText());
	}

	public void setTip(String tip) {

	}

	public Icon getIcon() {
		return icon;
	}

	public Label getLabel() {
		return label;
	}

	public void setVisible(boolean visible) {
		icon.setVisible(visible);
		label.setVisible(visible);
	}

	public boolean isVisible() {
		return visible;
	}

}
