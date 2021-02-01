package com.lucas.ferreira.maburn.model.documents;

import org.w3c.dom.Document;

import com.lucas.ferreira.maburn.model.items.CollectionItem;
import com.lucas.ferreira.maburn.util.CustomLogger;

public class SaveCollection {
	private DocumentCollectionReader documentCollection;

	public SaveCollection(Document doc) {
	
		this.documentCollection = new DocumentCollectionReader(doc);
	}
	
	public void deleteData(CollectionItem item) {
		documentCollection.deleteItem(item);
	}
	
	public CollectionItem loadDatas(CollectionItem item) {
		CollectionItem resultItem;
		resultItem = documentCollection.getItemInDocument(item);
		return resultItem;
	}

	public void saveDatas(CollectionItem item) {
		documentCollection.writeItem(item);

		CustomLogger.log("WRITE ITEM: " + item.getTitleDataBase());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
