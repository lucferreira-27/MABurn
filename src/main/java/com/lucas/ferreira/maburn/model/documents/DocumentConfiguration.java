package com.lucas.ferreira.maburn.model.documents;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lucas.ferreira.maburn.exceptions.DocumentException;
import com.lucas.ferreira.maburn.model.bean.downloaded.AnimeDownloaded;
import com.lucas.ferreira.maburn.model.bean.downloaded.MangaDownloaded;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class DocumentConfiguration {
	private Document doc;
	private ParseXMLDocument parser;

	public DocumentConfiguration(Document doc) {
		// TODO Auto-generated constructor stub
		this.doc = doc;
		this.parser = new ParseXMLDocument();
	}

	public String getPath(Category itemCategory) {
		String path;
		if (itemCategory == Category.ANIME)
			path = readerDocument("anime_destination");
		else if (itemCategory == Category.MANGA)
			path = readerDocument("manga_destination");
		else
			throw new DocumentException("Path (" + itemCategory + ") category don't found in configuration file");
		if (path == null) {
			throw new DocumentException("Item (" + itemCategory + ") category don't found in configuration file");
		}
		return path;
	}

	public String getItemPath(CollectionItem item) {
		if (item instanceof AnimeDownloaded)
			return readerDocument("anime_destination");
		else if (item instanceof MangaDownloaded)
			return readerDocument("manga_destination");
		throw new DocumentException("Item category don't found in configuration file");
	}

	private String readerDocument(String elementName) {
		Element element = findElementByTagName(elementName);
		return element.getTextContent();

	}

	private Element findElementByTagName(String elementName) {
		// TODO Auto-generated method stub
		Element el = doc.getDocumentElement();
		NodeList nList = el.getElementsByTagName(elementName);

		return (Element) nList.item(0);
	}

	private void editDocument(String elementName, String value) {
		Element root = doc.getDocumentElement();
		Element element = (Element) doc.getElementsByTagName(elementName).item(0);

		root.appendChild(element);
		element.setTextContent(value);
		CustomLogger.log("Elemenet name: " + elementName);
		CustomLogger.log("Value " + value);
		parser.tranformContentToXML(doc, Documents.CONFIG_LOCAL);
	}

	public void setPath(String definePath, CollectionItem item) {
		if (item.getCategory().name().equalsIgnoreCase("Anime")) {
			editDocument("anime_destination", definePath);

		} else if (item.getCategory().name().equalsIgnoreCase("Manga")) {
			editDocument("manga_destination", definePath);
		}
	}

	public void setPath(String definePath, Category category) {
		if (category == Category.ANIME) {
			editDocument("anime_destination", definePath);

		} else if (category == Category.MANGA) {
			editDocument("manga_destination", definePath);

		}
	}

	public static void createDocument() {
		// TODO Auto-generated method stub
		ConfigurationReader configReader = new ConfigurationReader();
		ParseXMLDocument parser = new ParseXMLDocument();
		Document doc = configReader.createConfigurationDocument();
		Element config = doc.createElement("config");

		doc.appendChild(config);

		Element mangaDestination = doc.createElement("manga_destination");
		Element animeDestination = doc.createElement("anime_destination");

		config.appendChild(animeDestination);
		config.appendChild(mangaDestination);

		parser.tranformContentToXML(doc, Documents.CONFIG_LOCAL);
	}

	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

}
