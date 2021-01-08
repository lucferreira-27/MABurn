package com.lucas.ferreira.maburn.model.loader;

import com.lucas.ferreira.maburn.model.collections.Collections;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.itens.CollectionItem;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public abstract class CollectionLoader extends Task<Collections>  {
	protected int itemsLength;
	protected IntegerProperty connectionItemLength = new SimpleIntegerProperty();
	protected StringProperty loadProgressLength = new SimpleStringProperty();
	protected IntegerProperty writeItemLength = new SimpleIntegerProperty();
	
	{	
		
		loadProgressLength.addListener((observable, oldvalue, newvalue) -> {

			//CustomLogger.log("Load Progress: " + newvalue);
			//CustomLogger.log("[" + newvalue + "/" + itemsLength + "]");
		
		});

		connectionItemLength.addListener((observable, oldvalue, newvalue) -> {

//			CustomLogger.log("Connection Progress: " + newvalue);
//			CustomLogger.log("[" + newvalue + "/" + itemsLength + "]");
		});

		writeItemLength.addListener((observable, oldvalue, newvalue) -> {

			//CustomLogger.log("Write Progress: " + newvalue);
			//CustomLogger.log("[" + newvalue + "/" + itemsLength + "]");

		});
	}

	public abstract Collections loadCollection(String destination, Category category);

	public abstract void loadItem(CollectionItem item);

	public abstract void setDestination(String destination);
	
	public IntegerProperty getConnectinoItemLength() {
		return connectionItemLength;
	}
	public StringProperty getLoadItemLength() {
		return loadProgressLength;
	}
	public IntegerProperty getWriteItemLength() {
		return writeItemLength;
	}
	
}
