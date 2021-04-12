package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.model.download.queue.Downloader;
import com.lucas.ferreira.maburn.model.enums.DownloadState;
import com.lucas.ferreira.maburn.model.items.CollectionSubItem;
import com.lucas.ferreira.maburn.util.CustomLogger;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.datas.DataStorageUtil;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TableConfig {
	private TableView<Downloader<CollectionSubItem>> tableItens;

	private TableColumn<Downloader<CollectionSubItem>, String> clName;

	private TableColumn<Downloader<CollectionSubItem>, Double> clSize;

	private TableColumn<Downloader<CollectionSubItem>, Double> clSpeed;

	private TableColumn<Downloader<CollectionSubItem>, Double> clCompleted;
	private TableColumn<Downloader<CollectionSubItem>, Double> clProgress;

	private TableColumn<Downloader<CollectionSubItem>, DownloadState> clState;

	private TableColumn<Downloader<CollectionSubItem>, String> clActionPause;

	private TableColumn<Downloader<CollectionSubItem>, String> clActionCancel;

	public TableConfig(TableView<Downloader<CollectionSubItem>> tableItens,
			TableColumn<Downloader<CollectionSubItem>, String> clName,
			TableColumn<Downloader<CollectionSubItem>, Double> clSize,
			TableColumn<Downloader<CollectionSubItem>, Double> clSpeed,
			TableColumn<Downloader<CollectionSubItem>, Double> clCompleted,
			TableColumn<Downloader<CollectionSubItem>, Double> clProgress,
			TableColumn<Downloader<CollectionSubItem>, DownloadState> clState,
			TableColumn<Downloader<CollectionSubItem>, String> clActionPause,
			TableColumn<Downloader<CollectionSubItem>, String> clActionCancel) {
		this.tableItens = tableItens;
		this.clName = clName;
		this.clSize = clSize;
		this.clSpeed = clSpeed;
		this.clCompleted = clCompleted;
		this.clProgress = clProgress;
		this.clState = clState;
		this.clActionPause = clActionPause;
		this.clActionCancel = clActionCancel;
	}

	public void initTable(ObservableList<Downloader<CollectionSubItem>> items) {
		tableItens.setItems(items);
		clName.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("name"));
		clName.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(item);

				}

			};
		});
		clSize.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("size"));
		clSize.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(DataStorageUtil.converterUnit(item));

				}

			};
		});
		clSpeed.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("speed"));
		clSpeed.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(DataStorageUtil.converterSpeedUnit(item));

				}

			};
		});
		clCompleted.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("completed"));
		clCompleted.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(DataStorageUtil.converterUnit(item));

				}

			};
		});
		clProgress.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, Double>("progress"));
		clProgress.setCellFactory(ProgressBarTableCell.<Downloader<CollectionSubItem>>forTableColumn());
		clState.setCellValueFactory(
				new PropertyValueFactory<Downloader<CollectionSubItem>, DownloadState>("downloadState"));
		clState.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, DownloadState>() {
			@Override
			protected void updateItem(DownloadState item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item));

				}

			};
		});
		clActionPause
				.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("actionPause"));
		clActionPause.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {

					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					final Button btn = downloader.getBtnPause();

					setGraphic(btn);

				}

			}
		});
		clActionCancel
				.setCellValueFactory(new PropertyValueFactory<Downloader<CollectionSubItem>, String>("actionCancel"));
		clActionCancel.setCellFactory(tc -> new TableCell<Downloader<CollectionSubItem>, String>() {

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {

					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					final Button btn = downloader.getBtnCancel();

					setGraphic(btn);
					return;
				}

				final Button btn = new Button("CANCEL");

				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					Downloader<CollectionSubItem> downloader = getTableView().getItems().get(getIndex());

					downloader.downloadStateProperty().addListener((obs, oldvalue, newvalue) -> {
						if (newvalue.equals(String.valueOf(DownloadState.FINISH))) {
							Platform.runLater(() -> btn.setDisable(true));

						}
					});

					downloader.getCancelProperty().addListener((ops, oldValue, newValue) -> {
						CustomLogger.log("CANCEL ACTION FOR: " + downloader.getItemWebData().getName());
						CustomLogger.log("CancelProperty: " + newValue);
						CustomLogger.log("DownlaodState: " + downloader.getDownloadState());
						if (!newValue) {
							btn.setText("CANCEL");
						} else {

							btn.setText("REMOVE");
						}
					});

					btn.setOnAction(event -> {
						if (!downloader.getDownloadState().equals(String.valueOf(DownloadState.FINISH))) {
							if (!downloader.getCancelProperty().get()) {
								CustomLogger.log("btnAction : cancel");
								downloader.setDownloadState(DownloadState.CANCELING);
								downloader.kill();

							} else {
								CustomLogger.log("btnAction : remove");

								tableItens.getItems().remove(downloader);

							}

						}

					});
					setGraphic(btn);
					setText(null);
				}
				;

			}
		});

		preventColumnReordering(tableItens);

	}

	private static <T> void preventColumnReordering(TableView<T> tableView) {
		System.out.println((LanguageReader.read("LABEL_TABLE_EMPTY")));
		tableView.setPlaceholder(new Label(LanguageReader.read("LABEL_TABLE_EMPTY")));
		Platform.runLater(() -> {
			for (Node header : tableView.lookupAll(".column-header")) {
				header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
			}
		});
	}
	public void changeTableItems(ObservableList<Downloader<CollectionSubItem>> items) {
		tableItens.setItems(items);
	}

}
