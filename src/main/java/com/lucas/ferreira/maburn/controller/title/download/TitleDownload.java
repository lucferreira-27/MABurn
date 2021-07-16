package com.lucas.ferreira.maburn.controller.title.download;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucas.ferreira.maburn.model.enums.FetchItemType;
import com.lucas.ferreira.maburn.model.enums.Sites;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TitleDownload  implements Initializable{
	@FXML
	private AnchorPane apShade;
	
	@FXML
	private AnchorPane apManualSearch;
	@FXML
	private VBox vBoxListDownloads;

	@FXML
	private HBox hboxItemsFields;
	@FXML
	private TextField txtStartItemValue;

	@FXML
	private TextField txtEndItemValue;

	@FXML
	private BorderPane bpThumbnailFull;

	@FXML
	private Button btnDownload;

	@FXML
	private ImageView imgThumbnailFullSize;
	@FXML
	private ImageView imgThumbnail;
	@FXML
	private ImageView imgSource;
	@FXML
	private ImageView imgZoomIn;

	@FXML
	private ImageView imgZoomOut;

	@FXML
	private ImageView imgFetch;
	@FXML
	private ImageView imgRecover;

	@FXML
	private ImageView imgManualSearch;

	@FXML
	private ImageView imgChoose;

	@FXML
	private ImageView imgDownloadStart;

	@FXML
	private ComboBox<FetchItemType> cbSelect;

	@FXML
	private ComboBox<String> cbItems;

	@FXML
	private ComboBox<Sites> cbSource;

	@FXML
	private TextArea txtFetchMsg;
	@FXML
	private TextArea txtAreaChooseMsg;

	@FXML
	private TextArea txtAreaFieldFirstMsg;
	@FXML
	private TextArea txtAreaFieldLastMsg;
	@FXML
	private TextArea txtAreaTotalItems;
	@FXML
	private TextArea txtAreaUpdateItems;
	@FXML
	private Label lblTotal;

	@FXML
	private Label lblComplete;

	@FXML
	private Label lblFailed;

	@FXML
	private Label lblTitle;
	@FXML
	private Label lblMainTitle;

	@FXML
	private Label lblFetch;

	@FXML
	private Label lblRecover;

	@FXML
	private Label lblManualSearch;

	@FXML
	private Label lblStart;

	@FXML
	private Label lblUrl;

	@FXML
	private Label lblSource;

	@FXML
	private Label lblItemsTotal;

	@FXML
	private Label lblSiteTitle;
	
	@FXML
	private Label lblItems;
	
	@FXML
	private Label labelCardsTotal;
	@FXML
	private Label labelCardsDownloading;
	@FXML
	private Label labelCardsCompleted;
	@FXML
	private Label labelCardsFetching;
	@FXML
	private Label labelCardsFailed;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public AnchorPane getApShade() {
		return apShade;
	}

	public AnchorPane getApManualSearch() {

		return apManualSearch;
	}

	public VBox getvBoxListDownloads() {
		return vBoxListDownloads;
	}

	public HBox getHboxItemsFields() {
		return hboxItemsFields;
	}

	public TextField getTxtStartItemValue() {
		return txtStartItemValue;
	}

	public TextField getTxtEndItemValue() {
		return txtEndItemValue;
	}

	public BorderPane getBpThumbnailFull() {
		return bpThumbnailFull;
	}

	public Button getBtnDownload() {
		return btnDownload;
	}

	public ImageView getImgThumbnailFullSize() {
		return imgThumbnailFullSize;
	}

	public ImageView getImgThumbnail() {
		return imgThumbnail;
	}

	public ImageView getImgSource() {
		return imgSource;
	}

	public ImageView getImgZoomIn() {
		return imgZoomIn;
	}

	public ImageView getImgZoomOut() {
		return imgZoomOut;
	}

	public ImageView getImgFetch() {
		return imgFetch;
	}

	public ImageView getImgRecover() {
		return imgRecover;
	}

	public ImageView getImgManualSearch() {
		return imgManualSearch;
	}

	public ImageView getImgChoose() {
		return imgChoose;
	}

	public ImageView getImgDownloadStart() {
		return imgDownloadStart;
	}

	public ComboBox<FetchItemType> getCbSelect() {
		return cbSelect;
	}

	public ComboBox<String> getCbItems() {
		return cbItems;
	}

	public ComboBox<Sites> getCbSource() {
		return cbSource;
	}

	public TextArea getTxtFetchMsg() {
		return txtFetchMsg;
	}

	public TextArea getTxtAreaChooseMsg() {
		return txtAreaChooseMsg;
	}

	public TextArea getTxtAreaFieldFirstMsg() {
		return txtAreaFieldFirstMsg;
	}

	public TextArea getTxtAreaFieldLastMsg() {
		return txtAreaFieldLastMsg;
	}

	public TextArea getTxtAreaTotalItems() {
		return txtAreaTotalItems;
	}

	public TextArea getTxtAreaUpdateItems() {
		return txtAreaUpdateItems;
	}

	public Label getLblTotal() {
		return lblTotal;
	}

	public Label getLblComplete() {
		return lblComplete;
	}

	public Label getLblFailed() {
		return lblFailed;
	}

	public Label getLblTitle() {
		return lblTitle;
	}

	public Label getLblMainTitle() {
		return lblMainTitle;
	}

	public Label getLblFetch() {
		return lblFetch;
	}

	public Label getLblRecover() {
		return lblRecover;
	}

	public Label getLblManualSearch() {
		return lblManualSearch;
	}

	public Label getLblStart() {
		return lblStart;
	}

	public Label getLblUrl() {
		return lblUrl;
	}

	public Label getLblSource() {
		return lblSource;
	}

	public Label getLblItemsTotal() {
		return lblItemsTotal;
	}

	public Label getLblSiteTitle() {
		return lblSiteTitle;
	}

	public Label getLblItems() {
		return lblItems;
	}

	public Label getLabelCardsTotal() {
		return labelCardsTotal;
	}

	public Label getLabelCardsDownloading() {
		return labelCardsDownloading;
	}

	public Label getLabelCardsCompleted() {
		return labelCardsCompleted;
	}

	public Label getLabelCardsFetching() {
		return labelCardsFetching;
	}

	public Label getLabelCardsFailed() {
		return labelCardsFailed;
	}

	@Override
	public String toString() {
		return "TitleDownload [apShade=" + apShade + ", apManualSearch=" + apManualSearch + ", vBoxListDownloads="
				+ vBoxListDownloads + ", hboxItemsFields=" + hboxItemsFields + ", txtStartItemValue="
				+ txtStartItemValue + ", txtEndItemValue=" + txtEndItemValue + ", bpThumbnailFull=" + bpThumbnailFull
				+ ", btnDownload=" + btnDownload + ", imgThumbnailFullSize=" + imgThumbnailFullSize + ", imgThumbnail="
				+ imgThumbnail + ", imgSource=" + imgSource + ", imgZoomIn=" + imgZoomIn + ", imgZoomOut=" + imgZoomOut
				+ ", imgFetch=" + imgFetch + ", imgRecover=" + imgRecover + ", imgManualSearch=" + imgManualSearch
				+ ", imgChoose=" + imgChoose + ", imgDownloadStart=" + imgDownloadStart + ", cbSelect=" + cbSelect
				+ ", cbItems=" + cbItems + ", cbSource=" + cbSource + ", txtFetchMsg=" + txtFetchMsg
				+ ", txtAreaChooseMsg=" + txtAreaChooseMsg + ", txtAreaFieldFirstMsg=" + txtAreaFieldFirstMsg
				+ ", txtAreaFieldLastMsg=" + txtAreaFieldLastMsg + ", txtAreaTotalItems=" + txtAreaTotalItems
				+ ", txtAreaUpdateItems=" + txtAreaUpdateItems + ", lblTotal=" + lblTotal + ", lblComplete="
				+ lblComplete + ", lblFailed=" + lblFailed + ", lblTitle=" + lblTitle + ", lblMainTitle=" + lblMainTitle
				+ ", lblFetch=" + lblFetch + ", lblRecover=" + lblRecover + ", lblManualSearch=" + lblManualSearch
				+ ", lblStart=" + lblStart + ", lblUrl=" + lblUrl + ", lblSource=" + lblSource + ", lblItemsTotal="
				+ lblItemsTotal + ", lblSiteTitle=" + lblSiteTitle + ", lblItems=" + lblItems + ", labelCardsTotal="
				+ labelCardsTotal + ", labelCardsDownloading=" + labelCardsDownloading + ", labelCardsCompleted="
				+ labelCardsCompleted + ", labelCardsFetching=" + labelCardsFetching + ", labelCardsFailed="
				+ labelCardsFailed + "]";
	}
	
	
	
	

}
