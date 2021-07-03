package com.lucas.ferreira.maburn.view;

import java.io.FileNotFoundException;

public abstract class IconsInitializer  {
	
	protected static String ICON_PATH = "icons/";

	
	protected abstract void initialize() throws FileNotFoundException;
	

}
