package com.lucas.ferreira.maburn.controller.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.controller.Controller;
import com.lucas.ferreira.maburn.controller.download.title.DownloadTitleInQueueController;
import com.lucas.ferreira.maburn.controller.download.title.DownloadTitleInQueueModal;
import com.lucas.ferreira.maburn.controller.title.download.title.TitleDownloadController;
import com.lucas.ferreira.maburn.model.states.InterfaceState;
import com.lucas.ferreira.maburn.model.states.RegisteredStates;
import com.lucas.ferreira.maburn.view.FXMLViewLoader;

import javafx.application.Platform;
import javafx.scene.Node;

public class DownloadQueueController implements Controller {

	private DownloadQueueModal downloadQueueModal;
	private List<DownloadTitleInQueueController> downloadTitleInQueueControllers = new ArrayList<>();;
	private List<DownloadTitleInQueueModal> downloadTitleInQueueModals = new ArrayList<>();;

	public DownloadQueueController(DownloadQueueModal downloadQueueModal) {
		this.downloadQueueModal = downloadQueueModal;

	}

	@Override
	public void initialize() {

		RegisteredStates.states.values().stream().filter(filterTitleWorkActive())
				.map(downloadController -> (TitleDownloadController) downloadController.getControllerStateAdapter())
				.collect(Collectors.toList()).forEach(controller -> loadControllerContainer(controller));
		if (downloadTitleInQueueModals.size() > 0) {
			onMouseClick();
			selectFirstContainer();
		}
	}

	private void loadControllerContainer(TitleDownloadController controller) {
		try {
			FXMLViewLoader<Node> fxmlViewLoader = new FXMLViewLoader<Node>();

			DownloadTitleInQueueModal downloadTitleInQueueModal = new DownloadTitleInQueueModal();
			Node node = fxmlViewLoader.loadContainer("DownloadTitleInQueue.fxml", downloadTitleInQueueModal);
			Platform.runLater(() -> downloadQueueModal.getVbTitles().getChildren().add(node));
			createTitleQueueController(controller, downloadTitleInQueueModal);
			downloadTitleInQueueModals.add(downloadTitleInQueueModal);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Predicate<? super InterfaceState> filterTitleWorkActive() {
		return new Predicate<InterfaceState>() {

			@Override
			public boolean test(InterfaceState interfaceState) {
				TitleDownloadController controller = (TitleDownloadController) interfaceState
						.getControllerStateAdapter();
				boolean hasDownloads = controller.getTitleDownloadInitialize().getTitleDownloadListCard()
						.getDownloadList().getContentDownloadList().getDownloadCardFulls().size() > 0;
				boolean hasFetchs = controller.getTitleDownloadInitialize().getTitleDownloadListCard().getDownloadList()
						.getContentFetchList().getFetchCardsCardFulls().size() > 0;
				return hasDownloads | hasFetchs;
			}
		};
	}

	private void createTitleQueueController(TitleDownloadController controller,
			DownloadTitleInQueueModal downloadTitleInQueueModal) {
		DownloadTitleInQueueController downloadTitleInQueueController = new DownloadTitleInQueueController(
				downloadTitleInQueueModal, controller.getTitleDownloadInitialize().getTitleDownloadListCard());
		downloadTitleInQueueController.initialize();
		downloadTitleInQueueControllers.add(downloadTitleInQueueController);
	}

	private void selectFirstContainer() {
		downloadTitleInQueueControllers.get(0).select();
	}

	private void onMouseClick() {
		for (DownloadTitleInQueueController controller : downloadTitleInQueueControllers) {
			controller.getSelect().addListener((obs, oldvalue, newvalue) -> {
				if (newvalue) {
					for (DownloadTitleInQueueController filterController : downloadTitleInQueueControllers) {
						if (!filterController.equals(controller) && filterController.getSelect().get()) {
							filterController.unselect();
						}
					}
				}
			});
		}

	}

}
