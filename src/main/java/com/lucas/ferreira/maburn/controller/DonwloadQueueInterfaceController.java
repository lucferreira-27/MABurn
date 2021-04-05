package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.download.queue.DownloadQueue;
import com.lucas.ferreira.maburn.model.download.queue.TitleDownload;
import com.lucas.ferreira.maburn.view.Components;
import com.lucas.ferreira.maburn.view.navigator.Builder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DonwloadQueueInterfaceController implements Initializable {

	private FXMLLoader loader = new FXMLLoader();
	private ObservableList<BorderPane> obsBorder;
	private List<BorderPane> borders = new ArrayList<BorderPane>();

	@FXML
	private BorderPane borderMain;
	@FXML
	private BorderPane borderTittleDownload;
	@FXML
	private BorderPane morePanel;

	@FXML
	private Label lblTitle;
	@FXML
	private Label lblTitlteMore;

	@FXML
	private ScrollPane scrollDownlods;

	@FXML
	private VBox vboxDownloadList;

	@FXML
	private AnchorPane borderDownloadInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadDownloadView();
	}

	private void loadDownloadView() {
		obsBorder = FXCollections.observableArrayList(borders);

		Builder builder = new Builder();
		new Thread(() -> {
			// DownloadInQueueInterfaceView downloadInQueueInterfaceView;
			for (TitleDownload title : DownloadQueue.getInstance().getDownloadList()) {

				System.out.println(title.getCollectionItem().getImageLocal());
				if (obsBorder.stream().filter((b) -> b.getUserData() == title).findAny().isPresent()) {
					continue;
				}
				try {
					Platform.runLater(() -> {
						BorderPane root = new BorderPane();
						root = (BorderPane) builder.build(root, Components.DOWNLOAD_IN_QUEUE, title);
						obsBorder.add(root);
					});
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}).start();

		obsBorder.addListener(new ListChangeListener<BorderPane>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends BorderPane> c) {
				if (c.next()) {
					if (c.wasAdded()) {

						Platform.runLater(() -> {

							vboxDownloadList.getChildren().add(c.getList().get(c.getFrom()));
						});

						return;
					}

				}

			}
		});
	}

}
