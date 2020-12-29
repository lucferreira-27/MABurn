package com.lucas.ferreira.maburn.util;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import com.lucas.ferreira.maburn.model.bean.webdatas.ItemWebData;

public class ItemWebDataResponseUtil extends ResponseUtil{
	private List<ItemWebData> items;

	public ItemWebDataResponseUtil(List<ItemWebData> items) {
		this.items = items;
	}



	public boolean waitAllFuture() {
		int itemsDone = 0;
		try {
			while (itemsDone < items.size()) {
				try {
				itemsDone = items.stream()
						.filter(futureItem -> futureItem.getDownloader().isDone())
						.collect(Collectors.toList()).size();
				Thread.sleep(100);
				updateProgress(itemsDone, items.size());
				}catch (ConcurrentModificationException e) {
					// TODO: handle exception
					continue;
				}
			}
		
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public Void call()  {
		// TODO Auto-generated method stub
		await();
		return null;
	}



	@Override
	public void await() {
		// TODO Auto-generated method stub
		waitAllFuture();
	}



	@Override
	public void listen() {
		// TODO Auto-generated method stub
		
	}

}
