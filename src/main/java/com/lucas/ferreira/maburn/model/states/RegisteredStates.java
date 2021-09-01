package com.lucas.ferreira.maburn.model.states;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RegisteredStates<T> {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static Map<String, InterfaceState> states = new HashMap<String, InterfaceState>();
	public static final String TEMP_FOLDER = System.getProperty("java.io.tmpdir");

	public static void saveState(InterfaceState state, String id) {
		if (state == null) {
			throw new NullPointerException();
		}
		LOGGER.config("Save " + state + " with id \"" + id + "\" in RegistereadStates");
		states.put(id, state);

	}

	public static InterfaceState recoverState(String id) {
		try {
			LOGGER.config("Recover state with id: " + id);

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
