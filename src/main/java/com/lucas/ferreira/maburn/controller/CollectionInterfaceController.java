package com.lucas.ferreira.maburn.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.GridPaneTable;
import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.collections.management.CollectionFilter;
import com.lucas.ferreira.maburn.model.collections.management.CollectionGridPane;
import com.lucas.ferreira.maburn.model.collections.management.CollectionLoadArea;
import com.lucas.ferreira.maburn.model.collections.management.CollectionSearch;
import com.lucas.ferreira.maburn.model.collections.management.CollectionStatus;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.enums.CollectionFilterType;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.LanguageReader;
import com.lucas.ferreira.maburn.util.Resources;
import com.lucas.ferreira.maburn.view.Interfaces;
import com.lucas.ferreira.maburn.view.navigator.Navigator;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CollectionInterfaceController implements Initializable {

	@FXML
	private StackPane collectionStackPane;

	@FXML
	private GridPane itensImagesGridPane;

	@FXML
	private ScrollPane itemsImagesScroll;

	@FXML
	private AnchorPane collectionAnchorPane;

	@FXML
	private AnchorPane vboxLoadArea;
	@FXML
	private GridPane searchItemGridPane;

	@FXML
	private TextField txtSearchBar;

	@FXML
	private Label lblLoadDataBase;

	@FXML
	private Label lblLoadFolderCollectionRead;

	@FXML
	private Label lblLoadFolderItemRead;

	@FXML
	private Label lblPorcentage;

	@FXML
	private Button btnSearch;

	@FXML
	private Label lblSearch;

	@FXML
	private Label lblCollectionName;

	@FXML
	private Label lblPath;

	@FXML
	private ImageView loadImageLoadArea;

	@FXML
	private ImageView imgReload;

	@FXML
	private ImageView imgFilter;

	@FXML
	private ProgressBar pbReadProgress;
	@FXML
	private ProgressIndicator sortCollectionLoad;

	private ObjectProperty<CollectionStatus> propertyStatus = new SimpleObjectProperty<CollectionStatus>();

	private Navigator navigator = new Navigator();
	private Collections collection;
	private CollectionFilter filter = new CollectionFilter();
	private CollectionLoadArea collectionLoadArea;
	private CollectionGridPane collectionGridPane = new CollectionGridPane();
	private CollectionSearch collectionSearch;
	private Category category;
	private String querry;
	private StringProperty querryProperty = new SimpleStringProperty();
	private IntegerProperty propertyItemsTotal = new SimpleIntegerProperty();
	private BooleanProperty propertyFullLoaded = new SimpleBooleanProperty(false);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HomeInterfaceController homeController = (HomeInterfaceController) Navigator.getMapNavigator()
				.get(Interfaces.HOME);
		if (homeController == null || homeController.getCategory() == null) {
			return;
		}
		category = homeController.getCategory();

		collectionLoadArea = new CollectionLoadArea(vboxLoadArea, lblLoadDataBase, lblLoadFolderItemRead,
				loadImageLoadArea, sortCollectionLoad, lblPath, lblPorcentage, pbReadProgress,
				collectionGridPane.getDataFetcher());

		collectionSearch = new CollectionSearch(sortCollectionLoad, loadImageLoadArea);

		propertyFullLoaded.set(false);

		collectionGridPane.build(category, itensImagesGridPane, itemsImagesScroll, collectionLoadArea, querryProperty);

		final ObservableList<Node> children = itensImagesGridPane.getChildren();

		children.addListener((InvalidationListener) o -> {
			int size = children.size();
			propertyItemsTotal.set(size);
		});

		imagesAndIconsSetter();

		setCollectionName();

		collectionLoadArea.bindLoadInfo();
		onClickOnImageGridPane();
		onSearchBarType();

	}

	private void imagesAndIconsSetter() {
		loadImageLoadArea.setImage(new Image(Resources.getResourceAsStream("icons/load_collection_icon.png")));
		reloadIcon();
		filterIcon();
	}

	private void reloadIcon() {

		Image imgSynchWhite = new Image(Resources.getResourceAsStream("icons/sync_white.png"));
		Image imgSynchRed = new Image(Resources.getResourceAsStream("icons/sync_red.png"));

		imgReload.setImage(imgSynchWhite);
		imgReload.setUserData(false);
		imgReload.hoverProperty().addListener((o -> {
			if (!(Boolean) imgReload.getUserData())
				if (imgReload.isHover()) {
					imgReload.setImage(imgSynchRed);
				} else {
					imgReload.setImage(imgSynchWhite);

				}
		}));
		imgReload.setOnMouseClicked(event -> {

			imgReload.setUserData(true);
			imgReload.setImage(imgSynchRed);
			imgReload.setRotate(30);
			collectionGridPane.reload();

			sortCollectionLoad.visibleProperty().addListener((obs, oldvalue, newvalue) -> {
				if (!newvalue) {
					imgReload.setRotate(0);
					imgReload.setImage(imgSynchWhite);
					imgReload.setUserData(false);
				}
			});

		});
	}

	private void filterIcon() {

		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOnAction(e -> System.out.print(((MenuItem) e.getTarget()).getText()));

		List<MenuItem> items = Arrays.asList(CollectionFilterType.values()).stream().map(f -> new MenuItem(f.name()))
				.collect(Collectors.toList());

		contextMenu.getItems().addAll(items);

		imgFilter.setOnContextMenuRequested(event -> {
			contextMenu.show(imgFilter, event.getScreenX(), event.getScreenY());
		});
		Image imgFilterWhite = new Image(Resources.getResourceAsStream("icons/filter_white.png"));
		Image imgFilterRed = new Image(Resources.getResourceAsStream("icons/filter_red.png"));

		imgFilter.setImage(imgFilterWhite);
		imgFilter.setUserData(false);

		imgFilter.setOnMouseClicked(event -> {

			if (event.getButton() == MouseButton.SECONDARY) {
				return;
			}

			if (!(Boolean) imgFilter.getUserData()) {
				imgFilter.setUserData(true);
				imgFilter.setImage(imgFilterRed);
				onClickFilter();
			} else {
				imgFilter.setImage(imgFilterWhite);
				imgFilter.setUserData(false);
				onClickFilter();

			}
		});

	}

	public void onClickOnImageGridPane() {
		itensImagesGridPane.setOnMouseClicked(event -> {

			if (event.getPickResult().getIntersectedNode().getParent() instanceof AnchorPane) {
				AnchorPane pane = (AnchorPane) event.getPickResult().getIntersectedNode().getParent();
				ImageView image = (ImageView) pane.getChildren().get(0);
				if (image.getUserData() instanceof CollectionItem) {

					CollectionItem item = (CollectionItem) image.getUserData();
					collectionGridPane.getCollection().setActualItem(item);
					if (propertyStatus.get() == CollectionStatus.COLLECTION_LOCAL) {
						navigator.open(Interfaces.TITLE);
						return;
					}
					if (propertyStatus.get() == CollectionStatus.COLLECTION_SEARCH) {
						navigator.open(Interfaces.TITLE_SEARCH);
						return;

					}
				}
			}

		});
	}

	public void onSearchBarType() {

		ObjectProperty<CollectionStatus> propertyStatus = collectionGridPane.getPropertyStatus();
		BooleanProperty propertyCollectionEmpty = collectionGridPane.getPropertyCollectionEmpty();

		propertyStatus.addListener((obs, oldvalue, newvalue) -> {
			CollectionStatus status = newvalue;
			if (status == CollectionStatus.COLLECTION_LOCAL) {
				setCollectionName();

				collectionGridPane.sortGridPane();

			}

			if (status == CollectionStatus.COLLECTION_SEARCH) {
				Platform.runLater(() -> lblCollectionName.setText("Result"));
			}

		});

		propertyCollectionEmpty.addListener((obs, oldvalue, newvalue) -> {
			if (newvalue) {
				showSearchOption();
			} else {
				hideSearchOption();
			}
		});

		querryProperty.addListener((obs, oldvalue, newvalue) -> {

			querry = newvalue;
			updateSearchLabel(querry);

		});

		txtSearchBar.textProperty().bindBidirectional(querryProperty);
		txtSearchBar.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				onClickButtonSearch();
			}
		});
	}

	private void setCollectionName() {
		if (category == Category.ANIME)
			Platform.runLater(() -> lblCollectionName.setText("Anime Collection"));
		else if (category == Category.MANGA) {
			Platform.runLater(() -> lblCollectionName.setText("Manga Collection"));

		}
	}

	private void showSearchOption() {

		lblSearch.setVisible(true);
		btnSearch.setVisible(true);
		searchItemGridPane.setVisible(true);
		itemsImagesScroll.setContent(searchItemGridPane);
	}

	private void updateSearchLabel(String value) {
		String search = LanguageReader.read("LABEL_SEARCH").replace("${value}", "\"" + value + "\"");
		lblSearch.setText(search);
	}

	private void hideSearchOption() {
		Platform.runLater(() -> {
			updateSearchLabel("");
			lblSearch.setVisible(false);
			btnSearch.setVisible(false);
			searchItemGridPane.setVisible(false);
			itemsImagesScroll.setContent(itensImagesGridPane);
		});
	}

	@FXML
	public void onClickFilter() {

		collectionGridPane.filter();

	}

	public void onClickButtonSearch() {
		// TODO Auto-generated method stub

		new Thread(() -> {

			GridPaneTable searchTable = collectionSearch.search(querry, collection, category);
			Platform.runLater(() -> collectionGridPane.swichTableSearch(searchTable, querry));

			Platform.runLater(() -> {

				txtSearchBar.clear();

			});

		}).start();

	}

	public GridPane getItensImagesGridPane() {
		// TODO Auto-generated method stub
		return itensImagesGridPane;
	}

	public BooleanProperty propertyFullLoaded() {
		return propertyFullLoaded;
	}

	public IntegerProperty propertyItemTotal() {
		return propertyItemsTotal;
	}

	public CollectionGridPane getCollectionGridPane() {
		return collectionGridPane;
	}

	public ObjectProperty<CollectionFilterType> propertyFilter() {
		return null;
	}

	public Category getCategory() {
		return category;
	}

	public CollectionFilter getFilter() {
		return filter;
	}

}
