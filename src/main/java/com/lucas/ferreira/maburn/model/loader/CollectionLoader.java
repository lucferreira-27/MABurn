package com.lucas.ferreira.maburn.model.loader;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

public abstract class CollectionLoader extends Task<Collections>  {
	protected int itemsLength;
	protected IntegerProperty connectionItemLength = new SimpleIntegerProperty();
	protected IntegerProperty loadProgressLength = new SimpleIntegerProperty();
	protected IntegerProperty writeItemLength = new SimpleIntegerProperty();
	
	{	
		
		loadProgressLength.addListener((observable, oldvalue, newvalue) -> {

			//System.out.println("Load Progress: " + newvalue);
			//System.out.println("[" + newvalue + "/" + itemsLength + "]");
		
		});

		connectionItemLength.addListener((observable, oldvalue, newvalue) -> {

//			System.out.println("Connection Progress: " + newvalue);
//			System.out.println("[" + newvalue + "/" + itemsLength + "]");
		});

		writeItemLength.addListener((observable, oldvalue, newvalue) -> {

			//System.out.println("Write Progress: " + newvalue);
			//System.out.println("[" + newvalue + "/" + itemsLength + "]");

		});
	}

	public abstract Collections loadCollection(String destination, Category category);

	public abstract void loadItem(CollectionItem item);

	public abstract void setDestination(String destination);
	
	public IntegerProperty getConnectinoItemLength() {
		return connectionItemLength;
	}
	public IntegerProperty getLoadItemLength() {
		return loadProgressLength;
	}
	public IntegerProperty getWriteItemLength() {
		return writeItemLength;
	}
	
}
