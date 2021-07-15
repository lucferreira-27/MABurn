package com.lucas.ferreira.maburn.model.collections.management;

import com.lucas.ferreira.maburn.model.loader.DataFetcher;
import com.lucas.ferreira.maburn.util.MathUtil;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CollectionLoadArea {
	private DataFetcher dataFetcher;

	private final Pane loadArea;
	private final Label lblLoadDataBase;
	private final Label lblLoadFolderItemRead;
	private final Label lblPath;
	private final Label lblPorcentage;
	private final ImageView loadImageLoadArea;
	private final ProgressIndicator sortCollectionLoad;
	private final ProgressIndicator pbReadProgress;

	public CollectionLoadArea(Pane loadArea, Label lblLoadDataBase, Label lblLoadFolderItemRead,
			ImageView loadImageLoadArea, ProgressIndicator sortCollectionLoad, Label lblPath, Label lblPorcentage,
			ProgressIndicator pbReadProgress, ObjectProperty<DataFetcher> propertyDataFetcher) {

		this.loadArea = loadArea;
		this.lblLoadDataBase = lblLoadDataBase;
		this.lblLoadFolderItemRead = lblLoadFolderItemRead;
		this.lblPorcentage = lblPorcentage;
		this.loadImageLoadArea = loadImageLoadArea;
		this.sortCollectionLoad = sortCollectionLoad;
		this.pbReadProgress = pbReadProgress;
		this.lblPath = lblPath;

		propertyDataFetcher.addListener((obs, oldvalue, newvalue) -> {
			this.dataFetcher = newvalue;
		});

		this.dataFetcher = propertyDataFetcher.get();

	}

	public void bindLoadInfo() {
		lblLoadDataBase.textProperty().bind(dataFetcher.getLblLoadDataBase());
		lblPath.textProperty().bind(dataFetcher.getLblLoadFolderCollectionRead());
		lblLoadFolderItemRead.textProperty().bind(dataFetcher.getLblLoadFolderItemRead());
		pbReadProgress.progressProperty().bind(dataFetcher.getReadProgressProperty());
		pbReadProgress.progressProperty().addListener((obs, oldvalue, newvalue) -> {
			lblPorcentage.setText(String.valueOf(MathUtil.roundDouble(newvalue.doubleValue(), 1) * 100));
		});

	}

	public void showQuickLoad() {
		sortCollectionLoad.setVisible(true);
	}

	public void hideQuickLoad() {
		sortCollectionLoad.setVisible(false);

	}

	public void hideArea() {
		loadArea.setVisible(false);
		lblLoadDataBase.setVisible(false);
		// lblLoadFolderCollectionRead.setVisible(false);
		lblLoadFolderItemRead.setVisible(false);
		loadImageLoadArea.setVisible(false);
		// txtSearchBar.setEditable(true);
	}

	public void showArea() {
		loadArea.setVisible(true);
		lblLoadDataBase.setVisible(true);
		// lblLoadFolderCollectionRead.setVisible(false);
		lblLoadFolderItemRead.setVisible(true);
		loadImageLoadArea.setVisible(true);
	}

}
