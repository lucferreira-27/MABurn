package com.lucas.ferreira.maburn.util;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FutureResponseUtil <T> extends ResponseUtil {
	private IntegerProperty elementsCompleted = new SimpleIntegerProperty();
	private List<Future<T>> future;
	public FutureResponseUtil(List<Future<T>> future) {
		// TODO Auto-generated constructor stub
		this.future = future;
	}
	
	public boolean waitAllFuture() {
		int itensDone = 0;
		int last = 0;
		try {
			while (itensDone < future.size()) {
				itensDone = future.stream().filter(futureItem -> futureItem.isDone()).collect(Collectors.toList())
						.size();
				if (itensDone > last) {
					last = itensDone;
					elementsCompleted.set(elementsCompleted.get() + 1);
				}

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}
	public IntegerProperty getElementsCompleted() {
		return elementsCompleted;
	}
	@Override
	public void await() {
		// TODO Auto-generated method stub
		waitAllFuture();
		
	}

	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}


