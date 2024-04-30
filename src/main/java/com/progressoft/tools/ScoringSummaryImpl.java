package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ScoringSummaryImpl provides methods to calculate and summarize scores from a
 * dataset. It supports various statistical measures such as mean, median,
 * standard deviation, variance, min, and max.
 *
 * @author Omar Abu-Nadi
 * @version 1.0
 * @since 2024-04-28
 */

public class ScoringSummaryImpl implements ScoringSummary {

	private List<BigDecimal> values;

	/**
	 * Constructs a ScoringSummaryImpl object with a list of BigDecimal values.
	 *
	 * @param values A list of BigDecimal values to be analyzed.
	 */

	public ScoringSummaryImpl(List<BigDecimal> values) {
		this.values = values;
	}

	/**
	 * Calculates the mean (average) of the dataset.
	 *
	 * @return The mean of the dataset as a BigDecimal.
	 */

	@Override
	public BigDecimal mean() {
		BigDecimal sum = values.stream().reduce((value, total) -> value.add(total)).get();

		BigDecimal average = sum.divide(BigDecimal.valueOf(values.size()), RoundingMode.HALF_EVEN);
		return average.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Calculates the standard deviation of the dataset.
	 *
	 * @return The standard deviation of the dataset as a BigDecimal.
	 */

	@Override
	public BigDecimal standardDeviation() {
		double standard = Math.sqrt(variance().doubleValue());
		BigDecimal standardDeviation = new BigDecimal(standard);
		standardDeviation = standardDeviation.setScale(2, RoundingMode.HALF_UP);
		return standardDeviation;
	}

	/**
	 * Calculates the variance of the dataset.
	 *
	 * @return The variance of the dataset as a BigDecimal.
	 */

	@Override
	public BigDecimal variance() {
		List<BigDecimal> squares = new ArrayList<BigDecimal>();
		for (BigDecimal value : values) {
			BigDecimal XminMean = value.subtract(mean());
			squares.add(XminMean.pow(2));
		}
		BigDecimal sum = sum(squares);
		BigDecimal variance = sum.divide(BigDecimal.valueOf(values.size()), RoundingMode.HALF_EVEN).setScale(0,
				RoundingMode.HALF_UP);
		return variance.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Calculates the median of the dataset.
	 *
	 * @return The median of the dataset as a BigDecimal.
	 */

	@Override
	public BigDecimal median() {
		BigDecimal median = BigDecimal.ZERO;
		Collections.sort(values);
		if (values.size() % 2 == 0)
			median = (BigDecimal) values.get(values.size() / 2).add((BigDecimal) values.get(values.size() / 2 - 1))
					.divide(BigDecimal.valueOf(2));
		else
			median = (BigDecimal) values.get(values.size() / 2);
		return median.setScale(2, RoundingMode.HALF_EVEN);

	}

	/**
	 * Finds the minimum value in the dataset.
	 *
	 * @return The minimum value of the dataset as a BigDecimal.
	 */

	@Override
	public BigDecimal min() {
		return values.stream().min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO).setScale(2,
				RoundingMode.HALF_EVEN);
	}

	/**
	 * Finds the maximum value in the dataset.
	 *
	 * @return The maximum value of the dataset as a BigDecimal.
	 */

	@Override
	public BigDecimal max() {
		return values.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO).setScale(2,
				RoundingMode.HALF_EVEN);
	}

	/**
	 * Finds the summation of values in the dataset.
	 *
	 * @return The summation of the dataset as a BigDecimal.
	 */

	public BigDecimal sum(List<BigDecimal> values) {
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : values) {
			sum = sum.add(bigDecimal);
		}
		return sum;
	}

}