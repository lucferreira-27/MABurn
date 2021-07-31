package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.UserSystem;
import com.lucas.ferreira.maburn.model.browser.Platform;

public class UserSystemTest {
	
	@Test
	public void shouldFoundWindowns64() {
		Platform platformExpect = Platform.WINDOWS_64;
		UserSystem userSystem = new UserSystem("win","64");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
	@Test
	public void shouldFoundWindowns32() {
		Platform platformExpect = Platform.WINDOWS_32;
		UserSystem userSystem = new UserSystem("win","32");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
	@Test
	public void shouldFoundMac() {
		Platform platformExpect = Platform.MAC;
		UserSystem userSystem = new UserSystem("mac");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
	@Test
	public void shouldFoundUnixButReturnLinux() {
		Platform platformExpect = Platform.LINUX;
		UserSystem userSystem = new UserSystem("unix");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
	@Test
	public void shouldFoundNixAndReturnLinux() {
		Platform platformExpect = Platform.LINUX;
		UserSystem userSystem = new UserSystem("nix");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
	@Test
	public void shouldFoundNuxAndReturnLinux() {
		Platform platformExpect = Platform.LINUX;
		UserSystem userSystem = new UserSystem("nux");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
	@Test
	public void shouldFoundAixAndReturnLinux() {
		Platform platformExpect = Platform.LINUX;
		UserSystem userSystem = new UserSystem("aix");
		Platform platformResult = userSystem.getUserPlataform();
		assertThat(platformResult, is(platformExpect));
	}
}
