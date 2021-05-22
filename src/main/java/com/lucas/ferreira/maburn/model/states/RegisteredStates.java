package com.lucas.ferreira.maburn.model.states;

import java.util.ArrayList;
import java.util.List;

public class RegisteredStates {
	private static List<State> states = new ArrayList<State>();

	public static void saveState(StateAdapter state) {
		if (states.contains(state)) {

		}
	}
	
	public static void replaceOldState(StateAdapter stateAdapter) {
		
	}
	
	
	public static StateAdapter recoverState() {

		states.stream().filter((s) -> {
			return true;
		});

		return null;
	}

	public static void removeState(StateAdapter state) {
		states.remove(state);
	}

}
