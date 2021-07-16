package com.lucas.ferreira.maburn.controller.title.download;

import com.lucas.ferreira.maburn.controller.title.download.register.FetchableTittle;

public interface FetchAction {
	public FetchableTittle action(FetchableTittle fetchableTittle) throws Exception ;
}
