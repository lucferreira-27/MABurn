package com.lucas.ferreira.maburn.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.model.states.InterfaceState;
import com.lucas.ferreira.maburn.model.states.RegisteredStates;
import com.lucas.ferreira.maburn.model.states.State;

public class RegisteredStatesTest {
	InterfaceState interfaceState = new InterfaceState();

	@Test
	public void testSaveObject() {
		State state = new State();

		interfaceState.setModalStateAdapter(state);
		RegisteredStates.saveState(interfaceState, "ID");
		boolean contains = RegisteredStates.containsState("ID");
		assertTrue(contains);
	}

	@Test
	public void testRecoverObject() throws IOException {

		String name = "DumbState";
		State state = new State();
		state.setName(name);
		interfaceState.setModalStateAdapter(state);
		RegisteredStates.saveState(interfaceState, "ID");

		interfaceState = RegisteredStates.recoverState("ID");

		assertThat(name, is(((State)interfaceState.getModalStateAdapter()).getName()));
	}
}
