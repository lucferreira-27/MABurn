package com.lucas.ferreira.maburn.controller;

import java.util.HashMap;
import java.util.Map;

import com.lucas.ferreira.maburn.model.effects.AlertAnimation;
import com.lucas.ferreira.maburn.model.effects.Animations;
import com.lucas.ferreira.maburn.model.enums.Icons;
import com.lucas.ferreira.maburn.util.Icon;
import com.lucas.ferreira.maburn.util.IconConfig;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ManualSearchAlert implements Alert {

	private final AnchorPane anchorPane;
	private final ImageView imgSearch;
	private final ImageView imgUndo;
	private final TextField txtLink;
	private final Label lblTitle;
	private final Parent parent;
	private String ICON_PATH = "icons/";
	private StringProperty propertyAnswear = new SimpleStringProperty();
	private BooleanProperty propertyResponse = new SimpleBooleanProperty();
	
	private AlertAnimation alertAnimation = new AlertAnimation(this);
	
//	private ObservableList<Node> children;

	public ManualSearchAlert(AnchorPane anchorPane) {
		this.anchorPane = anchorPane;
		this.parent = anchorPane.getParent();

		Map<ManualSearchAlertNodes, Node> mapNodes = disassemblePane();

		this.imgSearch = (ImageView) mapNodes.get(ManualSearchAlertNodes.IMG_SEARCH);
		this.imgUndo = (ImageView) mapNodes.get(ManualSearchAlertNodes.IMG_UNDO);
		this.txtLink = (TextField) mapNodes.get(ManualSearchAlertNodes.TXT_LINK);
		this.lblTitle = (Label) mapNodes.get(ManualSearchAlertNodes.LBL_TITLE);

		initializeIcons();
	}

	@Override
	public void show() {
		
		
		
		if (parent != null) {
			parent.setVisible(true);
		}
		anchorPane.setVisible(true);
		anchorPane.setDisable(false);

		//alertAnimation.moveAlertPaneEntered(startY, endY, duration);
		
		
	}

	@Override
	public void hide() {
		if (parent != null) {
			parent.setVisible(false);
		}
		anchorPane.setVisible(false);
		anchorPane.setDisable(true);

	}

	@Override
	public boolean isVisible() {
		return anchorPane.isVisible();
	}

	private void initializeIcons() {
		Icon iconSearch = new Icon(imgSearch, new IconConfig(ICON_PATH, Icons.MANUAL_ALERT_SEARCH));
		iconSearch.setProperties((event) -> response(txtLink.getText()));
		Icon iconUndo = new Icon(imgUndo, new IconConfig(ICON_PATH, Icons.MANUAL_ALERT_UNDO));
		iconUndo.setProperties((event) -> response(null));

	}

	public void response(String value) {

		propertyAnswear.setValue(value);
		propertyResponse.setValue(true);
	}

	private Map<ManualSearchAlertNodes, Node> disassemblePane() {

		Map<ManualSearchAlertNodes, Node> mapNodes = new HashMap<ManualSearchAlertNodes, Node>();

		anchorPane.getChildren().forEach(child -> {
			for (ManualSearchAlertNodes node : ManualSearchAlertNodes.values()) {
				if (child.getId().equals(node.getId())) {
					mapNodes.put(node, child);
				}
			}
		});
		return mapNodes;
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public ImageView getImageViewSearch() {
		return imgSearch;
	}

	public ImageView getImageViewUndo() {
		return imgUndo;
	}

	public TextField getTextFieldLink() {
		return txtLink;
	}

	public Label getLabelTitle() {
		return lblTitle;
	}


	public String waitAnswear() {
		propertyResponse.set(false);
		while (!propertyResponse.get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return propertyAnswear.get();

	}

	public static enum ManualSearchAlertNodes {
		IMG_SEARCH("imgSearch"), IMG_UNDO("imgUndo"), TXT_LINK("txtLink"), LBL_TITLE("lblTitle");

		private String id;

		private ManualSearchAlertNodes(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

	}

	@Override
	public Pane getPane() {
		// TODO Auto-generated method stub
		return anchorPane;
	}
}
