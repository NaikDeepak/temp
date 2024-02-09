package com.jda.snew.util;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
	private final static ObjectMapper mapper = new ObjectMapper();

	public static String string(Object obj) {
	try {
		return mapper.writeValueAsString(obj);
	} catch (JsonProcessingException e) {
		throw new RuntimeException("Failed to convert bean to JSON String", e);
	}
	}

	public static <T> T object(String json, Class<T> valueType) {
	try {
		return mapper.readValue(json, valueType);
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("Failed to convert JSON String to Bean", e);
	}
	}

	public static <T> T object(Path path, Class<T> clazz) {
	try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
		return mapper.readValue(br, clazz);
	} catch (Exception e) {
		throw new RuntimeException("Failed to decode JSON", e);
	}
	}

	public static <T> T object(File file, Class<T> clazz) {
	return object(file.toPath(), clazz);
	}

	/* public static <T> T getBeanFromUrl(String url, Class<T> clazz) { try { return mapper.readValue(HTTPClient.getBody(url), clazz); } catch (Exception e) { e.printStackTrace(); throw new RuntimeException(
	 * "Failed to convert JSON String to Bean"); } } */
}
