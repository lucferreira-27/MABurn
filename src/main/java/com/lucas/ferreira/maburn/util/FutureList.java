package com.lucas.ferreira.maburn.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureList<T> {
	private List<Future<?>> future;

	public FutureList(List<Future<?>> fuList) {
		this.future = fuList;

	}

	public boolean isDone() {
		for (Future<?> f : future) {
			if (f.isDone() || f.isCancelled()) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public List<Object> get() {
		if (isDone()) {
			List<Object> list = new ArrayList<>();
			future.forEach(f -> {
				try {
					list.add(f.get());
				} catch (InterruptedException | ExecutionException e) {
					 
					e.printStackTrace();
				}
			});
			return list;
		} else {
			return null;
		}

	}

}
