package com.lucas.ferreira.maburn.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lucas.ferreira.maburn.exceptions.DocumentException;
import com.lucas.ferreira.maburn.model.bean.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class DocumentCollectionReaderModel {

	private Document doc;
	private CollectionItem item;
	private Element docElement;
	private ParseXMLDocumentModel parser;

	public DocumentCollectionReaderModel(Document doc) {
		this.doc = doc;
		this.docElement = doc.getDocumentElement();
		this.parser = new ParseXMLDocumentModel();
	}

	public void defineItemByCategory(String category) {
		if (category.equalsIgnoreCase("manga"))
			item = new MangaDownloaded(); // If the category is a manga
		else if (category.equalsIgnoreCase("anime"))
			item = new AnimeDownloaded(); // if the category is a anime
		else
			throw new DocumentException("It is something wrong with the CollectionDates.xml file ["
					+ CollectionDatasReaderModel.DATE_LOCAL + "]");
	}

	public void defineItemByItemInstance(CollectionItem item) {
		if (item instanceof MangaDownloaded)
			this.item = new MangaDownloaded(); // If the category is a manga
		else if (item instanceof AnimeDownloaded)
			this.item = new AnimeDownloaded(); // if the category is a anime
		else
			throw new DocumentException("It is something wrong with the CollectionDates.xml file ["
					+ CollectionDatasReaderModel.DATE_LOCAL + "]");
	}

	// Read the element by category in the document and return a list of all
	// elements witch
	// tagname "item"
	public List<Element> readElementItemByCategory() {

		NodeList nList = docElement.getElementsByTagName("item");
		List<Element> listElemetns = new ArrayList<>();

		for (int i = 0; i < nList.getLength(); i++) {
			Element e = (Element) nList.item(i);
			String categoryAttr = e.getAttribute("category"); // Capture attribute that has the item category
			if (!categoryAttr.equalsIgnoreCase(item.getCategory().name().toLowerCase()))
				continue;

			listElemetns.add(e);
		}

		return listElemetns;
	}

	// Parse all items in the document to Collection Item object return a list
	public List<CollectionItem> parseByItens() {
		List<CollectionItem> resultItens = new ArrayList<>();
		for (Element e : readElementItemByCategory()) {
			resultItens.add(parseItem(e));
		}
		return resultItens;
	}

	// Parse all items in the document to Collection Item object return a list
	public List<CollectionItem> parseAllItens() {
		List<CollectionItem> resultItens = new ArrayList<>();

		for (Element e : readAllElementItensInDocument()) {
			resultItens.add(parseItem(e));
		}
		return resultItens;
	}

	// Get a CollectionItem in the document, first check if the document contains
	// the item else if return null.
	public CollectionItem getItemInDocument(CollectionItem item) {
		CollectionItem resultItem = null;
		if (xmlContains(item.getDestination())) {
			System.out.println(item.getDestination());
			resultItem = parseItem(getElementByCollectionItem(item));
		}
		return resultItem;

	}

	public int getBigElementId() {
		List<Integer> ids = new ArrayList<>();
		for (Element e : readAllElementItensInDocument()) {
			ids.add(Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent()));
		}
		if (ids == null || ids.isEmpty())
			return 0;
		return Collections.max(ids);
	}

	public int getItensSize() {
		int size = 0;
		size = readAllElementItensInDocument().size();
		return size;
	}

	// Get an element in the document, is used the item destination to filter the
	// items.
	public Element getElementByCollectionItem(CollectionItem item) {
		defineItemByItemInstance(item);
		List<Element> els = readElementItemByCategory();
		for (Element e : els) {
			String destination = e.getElementsByTagName("destination").item(0).getTextContent();

			if (item.getDestination().equalsIgnoreCase(destination))
				return e;
		}
		return null;
	}

	// Parse elements from element item to CollectionItem.
	public CollectionItem parseItem(Element e) {

		String name = e.getElementsByTagName("title").item(0).getTextContent();
		String imageUrl = e.getElementsByTagName("image_url").item(0).getTextContent();
		String destination = e.getElementsByTagName("destination").item(0).getTextContent();
		String hospedSite = e.getElementsByTagName("hospedSite").item(0).getTextContent();
		String dataUrl = e.getElementsByTagName("data_url").item(0).getTextContent();
		String titleDataBase = e.getElementsByTagName("title_database").item(0).getTextContent();

		String link = e.getElementsByTagName("link").item(0).getTextContent();
		String id = e.getElementsByTagName("id").item(0).getTextContent();

		item.setName(name);
		item.setImageUrl(imageUrl);
		item.setLink(link);
		item.setDestination(destination);
		item.setDataBaseUrl(dataUrl);
		item.setTitleFromDataBase(titleDataBase);
		item.setHospedSite(hospedSite);
		item.setId(Integer.parseInt(id));

		return item;

	}

	// Check if the document contains the item.
	public boolean xmlContains(String destination) {

		List<Element> els = readAllElementItensInDocument();

		for (Element e : els) {
			String eDestination = e.getElementsByTagName("destination").item(0).getTextContent();

			if (destination.equals(eDestination))
				return true;
		}
		return false;

	}

	// Check if the document contains the item filtered by category.

	public boolean xmlContains(String destination, Category category) {

		List<Element> els = readElementItensInDocumentByCategory(category.name());

		for (Element e : els) {
			String eDestination = e.getElementsByTagName("destination").item(0).getTextContent();

			if (destination.equals(eDestination))
				return true;
		}
		return false;

	}

	public boolean xmlContains(int id, String category) {
		List<Element> els = readElementItensInDocumentByCategory(category);

		for (Element e : els) {
			String eId = e.getElementsByTagName("id").item(0).getTextContent();

			if (String.valueOf(id).equals(eId))
				return true;
		}
		return false;

	}

	private List<Element> readAllElementItensInDocument() {
		NodeList nList = docElement.getElementsByTagName("item");
		List<Element> listElemetns = new ArrayList<>();

		for (int i = 0; i < nList.getLength(); i++) {
			Element e = (Element) nList.item(i);
			listElemetns.add(e);
		}

		return listElemetns;
	}

	private List<Element> readElementItensInDocumentByCategory(String category) {
		NodeList nList = docElement.getElementsByTagName("item");
		List<Element> listElemetns = new ArrayList<>();

		for (int i = 0; i < nList.getLength(); i++) {
			Element e = (Element) nList.item(i);
			if (e.getAttribute("category").equalsIgnoreCase(category)) {
				listElemetns.add(e);
			}
		}

		return listElemetns;
	}

	public void writeItem(CollectionItem item) {
		// TODO Auto-generated method stub

		Element root = doc.getDocumentElement();
		Element itemElement = doc.createElement("item");
		Attr category = doc.createAttribute("category");
		if (item instanceof MangaDownloaded)
			category.setValue("manga");
		if (item instanceof AnimeDownloaded)
			category.setValue("anime");

		itemElement.setAttributeNode(category);
		root.appendChild(itemElement);

		ArrayList<Element> elements = getElementsInItem(item);
		for (Element e : elements) {
			itemElement.appendChild(e);
		}
		parser.tranformContentToXML(doc, CollectionDatasReaderModel.DATE_LOCAL);

	}

	// Get all elemnents inside item (title, image_url, link, destination and
	// others)
	public ArrayList<Element> getElementsInItem(CollectionItem item) {
		ArrayList<Element> elements = new ArrayList<>();
		
		defineItemByItemInstance(item);
		elements.add(addElementsInDocument("title", item.getName()));
		elements.add(addElementsInDocument("image_url", item.getImageUrl()));
		elements.add(addElementsInDocument("link", item.getLink()));
		elements.add(addElementsInDocument("destination", item.getDestination()));
		elements.add(addElementsInDocument("data_url", item.getDataBaseUrl()));
		elements.add(addElementsInDocument("title_database", item.getTitleDataBase()));
		elements.add(addElementsInDocument("hospedSite", item.getHospedSite()));
		elements.add(addElementsInDocument("id", String.valueOf(item.getId())));

		return elements;
	}

	// add element in document
	public Element addElementsInDocument(String tagName, String value) {
		Element e = createElement(tagName, value); // Create element in Document
		writeTextInElement(e, value); // Write text (value) in document
		return e;
	}

	public void editElementById(int id, String tagName, String value, String category) {
		Element childElement = null;
		if (xmlContains(id, category)) {
			NodeList nList = doc.getElementsByTagName("id");
			for (int i = 0; i < nList.getLength(); i++) {
				String textContent = nList.item(i).getTextContent();
				if (textContent.equals(String.valueOf(id))) {
					childElement = (Element) nList.item(i);
					break;
				}
			}
			Element parentElement = (Element) childElement.getParentNode();
			Element editElement = (Element) parentElement.getElementsByTagName(tagName).item(0);
			editElement.setTextContent(value);
			parser.tranformContentToXML(doc, CollectionDatasReaderModel.DATE_LOCAL);
		}

	}

	public Element createElement(String tagName, String value) {
		Element el = doc.createElement(tagName); // Create a new element
		return el;
	}

	public void writeTextInElement(Element e, String value) {
		if (value == null)
			value = "";
		e.appendChild(doc.createTextNode(value));// Add value in element

	}

	// Select an item in document by category (Anime/Manga) and get her
	public List<CollectionItem> getItemsInDocumentByCategory(String category) {

		defineItemByCategory(category); // Define Collection item instance
		List<CollectionItem> elementsItens = parseByItens(); // Parse all items that are part of the category
		return elementsItens;
	}

	public Document getDoc() {
		return doc;
	}

	public static void createDocument() {
		// TODO Auto-generated method stub
		CollectionDatasReaderModel datesReader = new CollectionDatasReaderModel();
		ParseXMLDocumentModel parser = new ParseXMLDocumentModel();
		Document doc = datesReader.createCollectionDateDocument();
		Element collection = doc.createElement("collection");

		doc.appendChild(collection);

		parser.tranformContentToXML(doc, CollectionDatasReaderModel.DATE_LOCAL);
	}

}
