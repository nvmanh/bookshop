package com.manhnv.utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.manhnv.model.request.BookRequest;

@SpringBootTest
public class TextUtilsTest {

	final String sampleFile = "C:\\Users\\User\\Documents\\spring-boot-workspace\\book-shop\\string-array-sample.csv";
	final String sampleFileFake = "C:\\Users\\User\\Documents\\spring-boot-workspace\\book-shop\\string-array-sample.cs";

	@Test
	public void testIsEmpty() {
		assertTrue(TextUtils.isEmpty(null));
		assertTrue(TextUtils.isEmpty("               "));
		assertFalse(TextUtils.isEmpty("assas     "));
		assertFalse(TextUtils.isEmpty("adasds"));
	}

	@Test
	public void testFileToStringJava8() throws Exception {
		String data = TextUtils.fileToStringJava8(new File(sampleFile));
		assertTrue("OK", !TextUtils.isEmpty(data));
		assertThatThrownBy(() -> TextUtils.fileToStringJava8(new File(sampleFileFake))).isInstanceOf(IOException.class);
	}

	@Test
	public void testFileToStringJava7() throws Exception {
		String data = TextUtils.fileToStringJava7(new File(sampleFile));
		assertTrue("OK", !TextUtils.isEmpty(data));
		assertThatThrownBy(() -> TextUtils.fileToStringJava7(new File(sampleFileFake))).isInstanceOf(IOException.class);
	}

	@Test
	public void test_asJsonString() throws Exception {
		BookRequest bookRequest = new BookRequest();
		bookRequest.setAuthor("manhnv");
		String result = TextUtils.asJsonString(bookRequest);
		assertTrue("OK", !TextUtils.isEmpty(result));
		//assertThatThrownBy(() -> TextUtils.asJsonString(bookRequest)).isInstanceOf(JsonProcessingException.class);
	}
}
