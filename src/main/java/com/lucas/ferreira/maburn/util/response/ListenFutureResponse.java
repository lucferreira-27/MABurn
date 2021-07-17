package com.lucas.ferreira.maburn.util.response;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;

public class ListenFutureResponse<T>  {
	private List<Future<T>> future;
	private IntegerProperty progress;
	private boolean finish = false;
	public ListenFutureResponse(IntegerProperty progress, List<Future<T>> future) {
		
		this.future = future;
		this.progress = progress;
	}
	

	public boolean listenAllFuture() {
		int itensDone = 0;
		int last = 0;
		try {
			while (itensDone < future.size()) {
				itensDone = future.stream().filter(futureItem -> futureItem.isDone()).collect(Collectors.toList())
						.size();
				if (itensDone > last) {
					progress.set(itensDone);
					last = itensDone;
				}
				Thread.sleep(100);
			}
			finish = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}


	
	
	public void listen() {
		
		listenAllFuture();
	}
	
	public void await() {
		while(!finish) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}
		}
	}




}
