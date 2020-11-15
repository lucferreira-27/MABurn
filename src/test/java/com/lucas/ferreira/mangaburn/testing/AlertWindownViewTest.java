package com.lucas.ferreira.mangaburn.testing;

import org.junit.Test;

import com.lucas.ferreira.maburn.view.AlertWindowView;

public class AlertWindownViewTest {
	
	@Test
	public void showDialogAlertError() {
		AlertWindowView alert = new AlertWindowView();
		alert.errorAlert("test", "test", "test");
	}
	
}
