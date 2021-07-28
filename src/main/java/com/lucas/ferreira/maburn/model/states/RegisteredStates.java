package com.lucas.ferreira.maburn.model.states;

import java.util.HashMap;
import java.util.Map;

public class RegisteredStates<T> {
	public static Map<String, InterfaceState> states = new HashMap<String, InterfaceState>();
	public static final String TEMP_FOLDER = System.getProperty("java.io.tmpdir");

	public static void saveState(InterfaceState state, String id) {
		if (state == null) {
			throw new NullPointerException();
		}
		System.out.println("Save " + state + " with id \"" + id + "\" in RegistereadStates");
		states.put(id, state);

	}

	public static InterfaceState recoverState(String id) {
		try {
			System.out.println("Recover state with id: " + id);

			InterfaceState recover =  states.get(id);
			return recover;

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalAccessError();
		}
	}

	public static boolean containsState(String id) {
		return states.containsKey(id);
	}

	public static void removeState(String id) {
		if (containsState(id))
			states.remove(id);
	}

}
