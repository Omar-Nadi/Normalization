package com.progressoft.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NormalizerTest {

	private Normalizer normalizer;

	@BeforeEach
	public void beforeEach() {
		normalizer = new NormalizerImpl();
	}

	public Normalizer normalizer() {
		if (normalizer == null)
			Assertions.fail("normalizer is not initialized");
		return normalizer;
	}

	@Test
	public void givenInvalidInput_whenZscore_thenThrowException() throws IOException {
		Normalizer normalizer = normalizer();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.zscore(Paths.get("no_exists"), null, null));
		Assertions.assertEquals("destination path is null", exception.getMessage());

		Path source = copyFile("/marks.csv", Files.createTempFile("marks", ".csv"));
		exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.zscore(source, Files.createTempFile("target", ".csv"), "Salary"));
		Assertions.assertEquals("column Salary not found", exception.getMessage());

		exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.zscore(source, Files.createTempFile("target", ".csv"), "TESt"));
		Assertions.assertEquals("column TESt not found", exception.getMessage());
	}

	@Test
	public void givenMarksCSVFileToScale_whenMarkColumnIsZScored_thenNewCSVFileIsGeneratedWithAdditionalZScoreColumn()
			throws IOException {
		String filename = "marks.csv";
		Path induction = Files.createTempDirectory("induction");
		String columnName = "mark";
		Path csvPath = induction.resolve(filename);
		Path destPath = induction.resolve("marks_scaled.csv");
		copyFile("/marks.csv", csvPath);
		Assertions.assertTrue(Files.exists(csvPath));

		Normalizer normalizer = normalizer();
		ScoringSummary summary = normalizer.zscore(csvPath, destPath, columnName);
		Assertions.assertNotNull(summary, "the returned summary is null");

		Assertions.assertEquals(new BigDecimal("66.00"), summary.mean(), "invalid mean");
		Assertions.assertEquals(new BigDecimal("16.73"), summary.standardDeviation(), "invalid standard deviation");
		Assertions.assertEquals(new BigDecimal("280.00"), summary.variance(), "invalid variance");
		Assertions.assertEquals(new BigDecimal("65.00"), summary.median(), "invalid median");
		Assertions.assertEquals(new BigDecimal("40.00"), summary.min(), "invalid min value");
		Assertions.assertEquals(new BigDecimal("95.00"), summary.max(), "invalid maximum value");

		Assertions.assertTrue(Files.exists(destPath), "the destination file does not exists");
		Assertions.assertFalse(Files.isDirectory(destPath), "the destination is not a file");

		List<String> generatedLines = Files.readAllLines(destPath);
		Path assertionPath = copyFile("/marks_z.csv", induction.resolve("marks_z.csv"));
		List<String> expectedLines = Files.readAllLines(assertionPath);
		assertLines(expectedLines, generatedLines);
	}

	@Test
	public void givenEmployeesCSVFileToScale_whenSalaryColumnIsZScored_thenNewCSVFileIsGeneratedWithAdditionalZScoreColumn()
			throws IOException {
		String filename = "employees.csv";
		Path induction = Files.createTempDirectory("induction");
		String columnName = "salary";
		Path csvPath = induction.resolve(filename);
		Path destPath = induction.resolve("employees_scaled.csv");
		copyFile("/employees.csv", csvPath);
		Assertions.assertTrue(Files.exists(csvPath));

		Normalizer normalizer = normalizer();
		ScoringSummary summary = normalizer.zscore(csvPath, destPath, columnName);
		Assertions.assertNotNull(summary, "the returned summary is null");

		Assertions.assertEquals(new BigDecimal("1702.00"), summary.mean(), "invalid mean");
		Assertions.assertEquals(new BigDecimal("785.19"), summary.standardDeviation(), "invalid standard deviation");
		Assertions.assertEquals(new BigDecimal("616523.00"), summary.variance(), "invalid variance");
		Assertions.assertEquals(new BigDecimal("1758.00"), summary.median(), "invalid median");
		Assertions.assertEquals(new BigDecimal("299.00"), summary.min(), "invalid min value");
		Assertions.assertEquals(new BigDecimal("2965.00"), summary.max(), "invalid maximum value");

		Assertions.assertTrue(Files.exists(destPath), "the destination file does not exists");
		Assertions.assertFalse(Files.isDirectory(destPath), "the destination is not a file");

		List<String> generatedLines = Files.readAllLines(destPath);
		Path assertionPath = copyFile("/employees_z.csv", induction.resolve("employees_z.csv"));
		List<String> expectedLines = Files.readAllLines(assertionPath);
		assertLines(expectedLines, generatedLines);
	}

	@Test
	public void givenInvalidInput_whenMinMaxScale_thenThrowException() throws IOException {
		Normalizer normalizer = normalizer();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.minMaxScaling(Paths.get("no_exists"), null, null));
		Assertions.assertEquals("destination path is null", exception.getMessage());

		Path source = copyFile("/marks.csv", Files.createTempFile("marks", ".csv"));
		exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.minMaxScaling(source, Files.createTempFile("target", ".csv"), "Kalven"));
		Assertions.assertEquals("column Kalven not found", exception.getMessage());

		exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.minMaxScaling(source, Files.createTempFile("target", ".csv"), "TESt2"));
		Assertions.assertEquals("column TESt2 not found", exception.getMessage());
	}

	@Test
	public void givenMarksCSVFileToScale_whenMarkColumnIsMinMaxScaled_thenNewCSVFileIsGeneratedWithAdditionalMinMaxScoreColumn()
			throws IOException {
		String filename = "marks.csv";
		Path induction = Files.createTempDirectory("induction");
		String columnName = "mark";
		Path csvPath = induction.resolve(filename);
		Path destPath = induction.resolve("marks_scaled.csv");
		copyFile("/marks.csv", csvPath);
		Assertions.assertTrue(Files.exists(csvPath));

		Normalizer normalizer = normalizer();
		ScoringSummary summary = normalizer.minMaxScaling(csvPath, destPath, columnName);
		Assertions.assertNotNull(summary, "the returned summary is null");

		Assertions.assertEquals(new BigDecimal("66.00"), summary.mean(), "invalid mean");
		Assertions.assertEquals(new BigDecimal("16.73"), summary.standardDeviation(), "invalid standard deviation");
		Assertions.assertEquals(new BigDecimal("280.00"), summary.variance(), "invalid variance");
		Assertions.assertEquals(new BigDecimal("65.00"), summary.median(), "invalid median");
		Assertions.assertEquals(new BigDecimal("40.00"), summary.min(), "invalid min value");
		Assertions.assertEquals(new BigDecimal("95.00"), summary.max(), "invalid maximum value");

		Assertions.assertTrue(Files.exists(destPath), "the destination file does not exists");
		Assertions.assertFalse(Files.isDirectory(destPath), "the destination is not a file");

		List<String> generatedLines = Files.readAllLines(destPath);
		Path assertionPath = copyFile("/marks_mm.csv", induction.resolve("marks_mm.csv"));
		List<String> expectedLines = Files.readAllLines(assertionPath);
		assertLines(expectedLines, generatedLines);
	}

	@Test
	public void givenEmployeesCSVFileToScale_whenSalaryColumnIsMinMaxScaled_thenNewCSVFileIsGeneratedWithAdditionalMinMaxScoreColumn()
			throws IOException {
		String filename = "employees.csv";
		Path induction = Files.createTempDirectory("induction");
		String columnName = "salary";
		Path csvPath = induction.resolve(filename);
		Path destPath = induction.resolve("employees_scaled.csv");
		copyFile("/employees.csv", csvPath);
		Assertions.assertTrue(Files.exists(csvPath));

		Normalizer normalizer = normalizer();
		ScoringSummary summary = normalizer.minMaxScaling(csvPath, destPath, columnName);
		Assertions.assertNotNull(summary, "the returned summary is null");

		Assertions.assertEquals(new BigDecimal("1702.00"), summary.mean(), "invalid mean");
		Assertions.assertEquals(new BigDecimal("785.19"), summary.standardDeviation(), "invalid standard deviation");
		Assertions.assertEquals(new BigDecimal("616523.00"), summary.variance(), "invalid variance");
		Assertions.assertEquals(new BigDecimal("1758.00"), summary.median(), "invalid median");
		Assertions.assertEquals(new BigDecimal("299.00"), summary.min(), "invalid min value");
		Assertions.assertEquals(new BigDecimal("2965.00"), summary.max(), "invalid maximum value");

		Assertions.assertTrue(Files.exists(destPath), "the destination file does not exists");
		Assertions.assertFalse(Files.isDirectory(destPath), "the destination is not a file");

		List<String> generatedLines = Files.readAllLines(destPath);
		Path assertionPath = copyFile("/employees_mm.csv", induction.resolve("employees_mm.csv"));
		List<String> expectedLines = Files.readAllLines(assertionPath);
		assertLines(expectedLines, generatedLines);
	}

	/**
	 * Test case: Verify that an IllegalArgumentException is thrown when the
	 * destination path is null during Z-score normalization. Reason: The
	 * destination path is a required parameter for Z-score normalization, and
	 * passing a null value should result in an exception.
	 */

	@Test
	public void givenInvalidInput_whenZscoreDestinationPathIsNull_thenThrowException() {
		Normalizer normalizer = normalizer();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.zscore(Paths.get("no_exists"), null, "Salary"));
		Assertions.assertEquals("destination path is null", exception.getMessage());
	}

	/**
	 * Test case: Verify that an IllegalArgumentException is thrown when the column
	 * name to standardize is null during Z-score normalization. Reason: The column
	 * name to standardize is a required parameter for Z-score normalization, and
	 * passing a null value should result in an exception.
	 */

	@Test
	public void givenInvalidInput_whenZscoreColumnNameIsNull_thenThrowException() {
		Normalizer normalizer = normalizer();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.zscore(Paths.get("marks.csv"), Files.createTempFile("target", ".csv"), null));
		Assertions.assertEquals("column is null", exception.getMessage());
	}

	/**
	 * Test case: Verify that an IllegalArgumentException is thrown when the
	 * destination path is null during Min-Max scaling. Reason: The destination path
	 * is a required parameter for Min-Max scaling, and passing a null value should
	 * result in an exception.
	 */

	@Test
	public void givenInvalidInput_whenMinMaxScalingDestinationPathIsNull_thenThrowException() {
		Normalizer normalizer = normalizer();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.minMaxScaling(Paths.get("no_exists"), null, "Salary"));
		Assertions.assertEquals("destination path is null", exception.getMessage());
	}

	/**
	 * Test case: Verify that an IllegalArgumentException is thrown when the column
	 * name to normalize is null during Min-Max scaling. Reason: The column name to
	 * normalize is a required parameter for Min-Max scaling, and passing a null
	 * value should result in an exception.
	 * 
	 * @throws IOException
	 */

	@Test
	public void givenInvalidInput_whenMinMaxScalingColumnNameIsNull_thenThrowException() throws IOException {
		Normalizer normalizer = normalizer();
		String filename = "marks.csv";
		Path induction = Files.createTempDirectory("induction");
		Path csvPath = induction.resolve(filename);
		Path destPath = induction.resolve("marks_scaled.csv");
		copyFile("/marks.csv", csvPath);

		Assertions.assertTrue(Files.exists(csvPath));

		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> normalizer.minMaxScaling(csvPath, destPath, "test"));
		Assertions.assertEquals("column test not found", exception.getMessage());
	}

	private final Path copyFile(String resource, Path path) throws IOException {
		try (InputStream is = this.getClass().getResourceAsStream(resource)) {
			try (OutputStream os = Files.newOutputStream(path)) {
				int b;
				while ((b = is.read()) != -1) {
					os.write(b);
				}
			}
		}
		return path;
	}

	private void assertLines(List<String> expectedLines, List<String> actualLines) {
		Assertions.assertTrue(actualLines.size() == expectedLines.size(), "lines are not identical");
		for (int i = 0; i < actualLines.size(); i++) {
			Assertions.assertEquals(expectedLines.get(i), actualLines.get(i));
		}
	}
}
