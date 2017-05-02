package org.gt4j.annas.math;

import java.util.Iterator;

/**
 * Model for Arithmetic series.
 * 
 * @author Sam Wilson
 * 
 */
public class ArithmeticSeries implements Iterable<Double> {

	/**
	 * initial value
	 */
	private double initialValue;

	/**
	 * difference between terms
	 */
	private double difference;

	/**
	 * Constructs an Arithmetic series given the initial value and the
	 * difference between terms
	 * 
	 * @param initialValue
	 *            initial value
	 * @param difference
	 *            difference
	 */
	public ArithmeticSeries(double initialValue, double difference) {
		super();
		this.initialValue = initialValue;
		this.difference = difference;
	}

	/**
	 * Constructs an Arithmetic series given a sequence of terms. The
	 * constructor throws an IllegalArgumentException if the number of values
	 * passed is too small or if there is no common difference between terms.
	 * 
	 * @param values terms of the series
	 */
	public ArithmeticSeries(double... values) {
		super();
		if (values != null && values.length > 1) {
			this.initialValue = values[0];
			this.difference = values[1] - values[0];
			for (int i = 1; i < values.length; i++) {
				if (values[i] - values[i - 1] != this.difference) {
					throw new IllegalArgumentException(
							"there is no common ratio");
				}
			}
		} else {
			throw new IllegalArgumentException(
					"must provide at least 2 values of ther series");
		}
	}

	/**
	 * Gets the sum of the Arithmetic series
	 * 
	 * @return if the difference is greater than 0 then the method returns
	 *         {@link java.lang.Double#POSITIVE_INFINITY} else if the difference
	 *         is less than 0 then it returns
	 *         {@link java.lang.Double#POSITIVE_INFINITY} otherwise the method
	 *         returns the initial value
	 */
	public double getSum() {
		if (this.difference > 0) {
			return Double.POSITIVE_INFINITY;
		} else if (this.difference < 0) {
			return Double.NEGATIVE_INFINITY;
		} else {
			return this.initialValue;
		}
	}

	/**
	 * gets the sum of the first n term in the series
	 * 
	 * @param n
	 *            number of terms to sum
	 * @return sum of the first n terms in the series
	 */
	public double getSumofN(int n) {
		if (n >= 0) {
			return n * ((this.initialValue + this.getTerm(n-1)) / 2);
		} else {
			throw new IllegalArgumentException("n >= 0");
		}
	}

	/**
	 * gets the nth term in the arithmetic series
	 * 
	 * @param n
	 *            index of the term to get. The initial value has index 0.
	 * @return the nth term in the series
	 */
	public double getTerm(int n) {
		if (n >= 0) {
			return this.initialValue + (n * this.difference);
		} else {
			throw new IllegalArgumentException("value must be greater than 0");
		}
	}

	@Override
	public Iterator<Double> iterator() {
		return new ArithmeticSeriesIterator(this.initialValue, this.difference);
	}

	/**
	 * gets the initial value
	 * 
	 * @return initial value
	 */
	public double getInitialValue() {
		return initialValue;
	}

	/**
	 * gets the common difference
	 * 
	 * @return common difference
	 */
	public double getDifference() {
		return difference;
	}

	/**
	 * Arithmetic series iterator
	 * 
	 * @author Sam Wilson
	 * 
	 */
	private class ArithmeticSeriesIterator implements Iterator<Double> {

		/**
		 * initial value
		 */
		private double initialValue;

		/**
		 * difference between terms
		 */
		private double difference;

		/**
		 * current position in the series
		 */
		private int n;

		/**
		 * Constructs an ArithmeticSeriesIterator given an initial value and a
		 * common difference
		 * 
		 * @param initialValue
		 *            initial value
		 * @param difference
		 *            difference between terms
		 */
		public ArithmeticSeriesIterator(double initialValue, double difference) {
			super();
			this.initialValue = initialValue;
			this.difference = difference;
			this.n = -1;
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Double next() {
			this.n++;
			return this.initialValue + (n * this.difference);

		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove is not supported");
		}

	}

}
