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
		if (DownloadQueue.getInstance().getDownloadList().size() == 0) {

		} else {

			loadDownloadView();
		}
	}

	private void loadDownloadView() {

		Builder builder = new Builder();

		// DownloadInQueueInterfaceView downloadInQueueInterfaceView;
		Platform.runLater(() -> {
			for (TitleDownload title : DownloadQueue.getInstance().getDownloadList()) {

				System.out.println(title.getCollectionItem().getImageLocal());
//				if (obsBorder.stream().filter((b) -> b.getUserData() == title).findAny().isPresent()) {
//					continue;
//				}
				try {

					DownloadInQueueController controller = new DownloadInQueueController();
					BorderPane root = new BorderPane();
					root = (BorderPane) builder.build(root, Components.DOWNLOAD_IN_QUEUE.getFxml(), controller, title);
					System.out.println(title.getCollectionItem().getTitleDataBase());
					// obsBorder.add(root);
					vboxDownloadList.getChildren().add(root);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});


	}

}
