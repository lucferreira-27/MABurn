package com.lucas.ferreira.maburn.model;

public class MarkTime {
	
	private Long time;
	private Long begin;
	private Long end;
	
	public void begin() {
		begin = System.currentTimeMillis() / 1000;
	}
	
	public Long end() throws Exception {
		if(begin == null) {
			throw new Exception();
		}
		end = System.currentTimeMillis() / 1000;
		
	return end - begin;
	}
}
