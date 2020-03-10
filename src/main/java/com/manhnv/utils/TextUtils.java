package com.manhnv.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TextUtils {
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() <= 0;
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static final String fileToStringJava8(File file) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
		stream.forEach(s -> contentBuilder.append(s).append("\n"));
		return contentBuilder.toString();
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final String fileToStringJava7(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}

	public static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
