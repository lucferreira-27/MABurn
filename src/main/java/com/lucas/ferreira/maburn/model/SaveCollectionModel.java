package com.lucas.ferreira.maburn.model;

import org.w3c.dom.Document;

import com.lucas.ferreira.maburn.model.itens.CollectionItem;

public class SaveCollectionModel {
	private DocumentCollectionReaderModel documentCollection;
	private Document doc;

	public SaveCollectionModel(Document doc) {
		this.doc = doc;
		this.documentCollection = new DocumentCollectionReaderModel(doc);
	}

	public CollectionItem loadDatas(CollectionItem item) {
		CollectionItem resultItem;
		resultItem = documentCollection.getItemInDocument(item);
		return resultItem;
	}

	public void saveDatas(CollectionItem item) {
		documentCollection.writeItem(item);

		System.out.println("WRITE ITEM: " + item.getTitleDataBase());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
