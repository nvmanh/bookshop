package com.manhnv.utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
public class CSVUtilsTest {
	final String sampleData = "cdatetime,address,district,beat,grid,crimedescr,ucr_ncic_code,latitude,longitude\r\n"
			+ "1/1/06 0:00,3108 OCCIDENTAL DR,3,3C        ,1115,10851(A)VC TAKE VEH W/O OWNER,2404,38.55042047,-121.3914158\r\n"
			+ "1/1/06 0:00,2082 EXPEDITION WAY,5,5A        ,1512,459 PC  BURGLARY RESIDENCE,2204,38.47350069,-121.4901858\r\n"
			+ "1/1/06 0:00,4 PALEN CT,2,2A        ,212,10851(A)VC TAKE VEH W/O OWNER,2404,38.65784584,-121.4621009";
	final String sampleFile = "C:\\Users\\User\\Documents\\spring-boot-workspace\\book-shop\\string-array-sample.csv";
	final String sampleFileFake = "C:\\Users\\User\\Documents\\spring-boot-workspace\\book-shop\\string-array-sample.cs";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testConvertCSVDataString() throws Exception {
		List<String[]> data = CSVUtils.convertCSVData(sampleData);
		assertTrue(data.size() == 4);
		List<String[]> d1 = CSVUtils.convertCSVData("");
		assertTrue(d1.size() == 0);
	}

	@Test
	public void testConvertCSVDataFile() throws Exception {
		List<String[]> data = CSVUtils.convertCSVData(new File(sampleFile));
		assertFalse(data.size() == 4);
		assertThatThrownBy(() -> CSVUtils.convertCSVData(new File(sampleFileFake))).isInstanceOf(IOException.class);
	}

	@Test
	public void testConvertCSVDataMultiPart() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("user-file", sampleFile, "text/plain",
				"test data".getBytes());
		List<String[]> data = CSVUtils.convertCSVData(mockMultipartFile);
		assertFalse(data.size() == 2);
	}
}
