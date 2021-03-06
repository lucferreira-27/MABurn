package com.lucas.ferreira.maburn.util.response;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FutureResponseUtil <T> extends ResponseUtil {
	private List<Future<T>> future;
	public FutureResponseUtil(List<Future<T>> future) {
		
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
				}
				Thread.sleep(100);

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public void await() {
		
		waitAllFuture();
		
	}

	@Override
	protected Void call() throws Exception {
		
		return null;
	}

	@Override
	public void listen() {
		
		
	}
}


