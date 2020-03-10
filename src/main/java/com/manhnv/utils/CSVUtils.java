package com.manhnv.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * 
 * @author User
 *
 */
public class CSVUtils {
	/**
	 * 
	 * @param csvData
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> convertCSVData(String csvData) throws IOException {
		Reader reader = new StringReader(csvData);
		CSVReader csvReader = new CSVReader(reader);
		List<String[]> list = new ArrayList<>();
		list = csvReader.readAll();
		reader.close();
		csvReader.close();
		return list;
	}

	/**
	 * 
	 * @param csvFile
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> convertCSVData(MultipartFile csvFile) throws IOException {
		Reader reader = new InputStreamReader(csvFile.getInputStream());
		CSVReader csvReader = new CSVReader(reader);
		List<String[]> list = new ArrayList<>();
		list = csvReader.readAll();
		reader.close();
		csvReader.close();
		return list;
	}

	/**
	 * 
	 * @param csvFile
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> convertCSVData(File csvFile) throws IOException {
		Reader reader = new FileReader(csvFile);
		CSVReader csvReader = new CSVReader(reader);
		List<String[]> list = new ArrayList<>();
		list = csvReader.readAll();
		reader.close();
		csvReader.close();
		return list;
	}

	/**
	 * 
	 * @param <T>
	 * @param csvData
	 * @param clazz
	 * @param separator
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> fromCSV(String csvData, Class<T> clazz, Character separator) {
		CsvToBean reader = new CsvToBeanBuilder(new StringReader(csvData)).withType(clazz).withSeparator(separator)
				.build();
		return reader.parse();
	}

	/**
	 * 
	 * @param <T>
	 * @param csvFile
	 * @param clazz
	 * @param separator
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> fromCSV(MultipartFile csvFile, Class<T> clazz, Character separator)
			throws IllegalStateException, IOException {
		CsvToBean reader = new CsvToBeanBuilder(new InputStreamReader(csvFile.getInputStream())).withType(clazz)
				.withSeparator(separator).build();
		return reader.parse();
	}

	/**
	 * 
	 * @param <T>
	 * @param file
	 * @param clazz
	 * @param separator
	 * @return
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> formCSV(File file, Class<T> clazz, Character separator)
			throws IllegalStateException, FileNotFoundException {
		CsvToBean reader = new CsvToBeanBuilder(new FileReader(file)).withType(clazz).withSeparator(separator).build();
		return reader.parse();
	}

	/**
	 * 
	 * @param <T>
	 * @param data
	 * @param clazz
	 * @param separator
	 * @return
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> String toCSV(List<T> data, Class<T> clazz, Character separator)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		Writer writer = new StringWriter();
		StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder(writer)
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(separator).build();
		beanToCsv.write(data);
		return writer.toString();
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ColumnPositionMappingStrategy columMapping(Class<?> clazz) {
		ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
		strategy.setType(clazz);
		List<Field> fields = FieldUtils.getFieldsListWithAnnotation(clazz, CsvBindByPosition.class);
		String[] columns = new String[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			columns[i] = fields.get(i).getName();
		}
		strategy.setColumnMapping(columns);
		return strategy;
	}
}
