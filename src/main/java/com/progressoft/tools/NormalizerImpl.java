package com.progressoft.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * NormalizerImpl is responsible for normalizing data using various techniques
 * such as Z-score or Min-Max scaling. It provides methods to apply
 * normalization to datasets and calculate summary statistics.
 *
 * @author Omar Abu-Nadi
 * @version 1.0
 * @since 2024-04-28
 */
public class NormalizerImpl implements Normalizer {

	/**
	 * Applies Z-score normalization to a specified column in a CSV file and writes
	 * the normalized data to a destination file.
	 *
	 * @param csvPath             The path to the source CSV file.
	 * @param destPath            The path to the destination file where the
	 *                            normalized data will be written.
	 * @param columnToStandardize The name of the column to be normalized.
	 * @return A ScoringSummary object containing the summary statistics of the
	 *         normalized data.
	 * @throws NullPointerException     If any of the input parameters are null.
	 * @throws IllegalArgumentException If the source file is not found, the
	 *                                  specified column does not exist, or any
	 *                                  other argument-related issues.
	 */

	@Override
	public ScoringSummary zscore(Path csvPath, Path destPath, String columnToStandardize) throws NullPointerException {

		List<List<String>> lines = validateTheInputs(csvPath, destPath, columnToStandardize);

		// Get the values in the column without the name of it
		int columnIndex = lines.get(0).indexOf(columnToStandardize);
		List<String> valuesOfColumnToStandardize = new ArrayList<String>();
		valuesOfColumnToStandardize = lines.stream().map(l -> l.get(columnIndex)).collect(Collectors.toList());
		valuesOfColumnToStandardize.remove(0);

		// Convert the values from String to BigDecimal
		List<BigDecimal> valuesInsideCol = convertListFromStringToBigDecimal(valuesOfColumnToStandardize);

		// Apply scoringSummary on the values inside column
		ScoringSummary scoringSummary = new ScoringSummaryImpl(valuesInsideCol);

		/*
		 * Apply Z-Score Normalization on values The formula for Z-score normalization
		 * is : (value-mean)/standardDeviation
		 */

		List<BigDecimal> valuesAfterScaling = valuesInsideCol
				.stream().map(value -> ((value.subtract(scoringSummary.mean()))
						.divide(scoringSummary.standardDeviation(), RoundingMode.HALF_EVEN)))
				.collect(Collectors.toList());

		// Add column with this form:colToStandardize_z
		String colAfterStandardize = lines.get(0).get(columnIndex) + "_" + "z";
		lines.get(0).add(columnIndex + 1, colAfterStandardize);

		// fill the lines with value after Z-Score Normalization
		fillTheLinesWithValues(lines, columnIndex, valuesAfterScaling);

		setLinesInCsv(destPath, lines);

		return scoringSummary;
	}

	/**
	 * Applies Min-Max scaling to a specified column in a CSV file and writes the
	 * normalized data to a destination file.
	 *
	 * @param csvPath        The path to the source CSV file.
	 * @param destPath       The path to the destination file where the normalized
	 *                       data will be written.
	 * @param colToNormalize The name of the column to be normalized.
	 * @return A ScoringSummary object containing the summary statistics of the
	 *         normalized data.
	 * @throws NullPointerException     If any of the input parameters are null.
	 * @throws IllegalArgumentException If the source file is not found, the
	 *                                  specified column does not exist, or any
	 *                                  other argument-related issues.
	 */

	@Override
	public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {

		List<List<String>> lines = validateTheInputs(csvPath, destPath, colToNormalize);

		// Get the values in the column without the name of it
		int indexOfColomn = lines.get(0).indexOf(colToNormalize);
		List<String> valuesOfColToStandardize = new ArrayList<String>();
		valuesOfColToStandardize = lines.stream().map(l -> l.get(indexOfColomn)).collect(Collectors.toList());
		valuesOfColToStandardize.remove(0);

		List<BigDecimal> valuesOfCol = convertListFromStringToBigDecimal(valuesOfColToStandardize);

		// Apply scoringSummary on the values inside column
		ScoringSummary scoringSummary = new ScoringSummaryImpl(valuesOfCol);

		/*
		 * Apply Min-Max Normalization Normalization on values The formula for Min-Max
		 * Normalization normalization is : (value-min)/(max-min)
		 */

		List<BigDecimal> valuesAfterNormalize = valuesOfCol.stream()
				.map(value -> ((value.subtract(scoringSummary.min()))
						.divide((scoringSummary.max().subtract(scoringSummary.min())), RoundingMode.HALF_EVEN)))
				.collect(Collectors.toList());

		// Add column with this form: colToNormalize_mm
		String colAfterNormalize = lines.get(0).get(indexOfColomn) + "_" + "mm";
		lines.get(0).add(indexOfColomn + 1, colAfterNormalize);

		// fill the lines with value after minMaxScaling Normalization
		fillTheLinesWithValues(lines, indexOfColomn, valuesAfterNormalize);

		setLinesInCsv(destPath, lines);

		return scoringSummary;
	}

	/**
	 * check the parameters is null and column is exist.
	 * 
	 * @param csvPath
	 * @param destPath
	 * @param colToNormalize
	 * @return The lines as a list of list of strings.
	 */

	private List<List<String>> validateTheInputs(Path csvPath, Path destPath, String colToNormalize) {
		// check null values
		if (csvPath == null)
			throw new IllegalArgumentException("source file is null");
		else if (destPath == null)
			throw new IllegalArgumentException("destination path is null");
		else if (colToNormalize == null)
			throw new IllegalArgumentException("column is null");

		// Get All Lines inside CSV
		List<List<String>> lines = getLinesFromCsv(csvPath);

		// Check if column name is exist in the fist line(titles)
		if (!lines.get(0).contains(colToNormalize))
			throw new IllegalArgumentException("column " + colToNormalize + " not found");

		return lines;
	}

	/**
	 * get the lines from the source csv file.
	 *
	 * @return The lines as a list of list of strings.
	 */

	public List<List<String>> getLinesFromCsv(Path csvPath) {
		String delimiter = ",";
		List<List<String>> lines = new ArrayList<List<String>>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvPath.toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> values = Arrays.stream(line.split(delimiter)).collect(Collectors.toList());
				lines.add(values);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * convert string list to BigDecimal list.
	 * 
	 * @param list of values as string
	 * @return the converted list as BigDecimal
	 */

	private List<BigDecimal> convertListFromStringToBigDecimal(List<String> valuesOfColumnToStandardize) {
		List<BigDecimal> valuesInsideCol = new ArrayList<BigDecimal>();
		for (String value : valuesOfColumnToStandardize) {
			valuesInsideCol.add(new BigDecimal(value, new MathContext(Integer.parseInt(value))));
		}
		return valuesInsideCol;
	}

	/**
	 * set the lines in the new csv file.
	 *
	 */

	public void setLinesInCsv(Path destPath, List<List<String>> lines) {
		try (FileWriter file = new FileWriter(destPath.toFile()); PrintWriter write = new PrintWriter(file)) {
			for (List<String> lineWrite : lines) {
				write.println(String.join(",", lineWrite));
			}
		} catch (IOException exe) {
			System.out.println("Cannot create file");
		}
	}

	/**
	 * fill the lines with value.
	 *
	 */

	private void fillTheLinesWithValues(List<List<String>> lines, int columnIndex,
			List<BigDecimal> valuesAfterScaling) {
		for (int l = 1; l < lines.size(); l++) {
			lines.get(l).add(columnIndex + 1, valuesAfterScaling.get(0).toString());
			valuesAfterScaling.remove(0);
		}
	}
}