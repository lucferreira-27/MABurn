package com.lucas.ferreira.maburn.util;

import javafx.concurrent.Task;

public abstract class ResponseUtil extends Task<Void> {
	
	public abstract void await();
	public abstract void listen();

}
