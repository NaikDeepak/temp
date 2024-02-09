package com.jda.snew.config;

import java.util.HashMap;
import java.util.Map;

public class SNEWCache {
	private static SNEWCache instance = null;
	private static Map<String, String> snewProperties = new HashMap<String, String>();
	private static Map<String, Object> snewObjects = new HashMap<String, Object>();

	protected SNEWCache() {
		// Exists only to defeat instantiation.
	}

	public static SNEWCache getInstance() {
		if (instance == null) {
			instance = new SNEWCache();
		}
		return instance;
	}

	public void setProperty(String key, String value) {
		snewProperties.put(key, value);
	}

	public String getProperty(String key) {
		return snewProperties.get(key);
	}

	public void setObject(String key, Object value) {
		snewObjects.put(key, value);
	}

	public Object getObject(String key) {
		return snewObjects.get(key);
	}

}
