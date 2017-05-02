package org.gt4j.annas.math;

import java.util.Iterator;

/**
 * Models a geometric series.
 * 
 * @author Sam Wilson
 * 
 */
public class GeometricSeries implements Iterable<Double> {

	private double initialValue;

	private double ratio;

	/**
	 * Constructs a geometric series given the initial value and the common
	 * ratio.
	 * 
	 * @param initialValue
	 *            first value of the series
	 * @param ratio
	 *            common ratio
	 */
	public GeometricSeries(double initialValue, double ratio) {
		super();
		this.ratio = ratio;
		this.initialValue = initialValue;
	}

	/**
	 * Constructs a geometric series given the starting sequence. If a common
	 * ratio can not be found the constructor throws an
	 * IllegalArgumentException.
	 * 
	 * @param values
	 *            elements of the geometric series
	 */
	public GeometricSeries(double... values) {
		super();
		if (values != null && values.length > 1) {
			this.initialValue = values[0];
			this.ratio = values[1] / values[0];

			for (int i = 1; i < values.length; i++) {
				if (values[i] / values[i - 1] != this.ratio) {
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
	 * gets the initial value
	 * 
	 * @return initial value
	 */
	public double getInitialValue() {
		return initialValue;
	}

	/**
	 * gets the common ratio
	 * 
	 * @return common ratio
	 */
	public double getRatio() {
		return ratio;
	}

	/**
	 * gets the sum of the series as n tends to infinity
	 * 
	 * @return if the series will converge then the sum is returned otherwise
	 *         Double.NaN is returned.
	 */
	public double getSum() {

		if (Math.abs(this.ratio) >= 1) {
			return Double.NaN;
		} else {
			return this.initialValue / (1 - this.ratio);
		}

	}

	/**
	 * gets the sum of the first n elements of the series
	 * 
	 * @param n
	 *            number of elements to sum
	 * @return sum of the first n elements of the series
	 */
	public double getSumofN(int n) {
		if (n >= 0) {
			return this.initialValue
					* ((1 - Math.pow(this.ratio, n)) / (1 - this.ratio));
		} else {
			throw new IllegalArgumentException("n > = 0");
		}
	}

	/**
	 * gets a term of the series
	 * 
	 * @param n
	 *            index of the term to return
	 * @return nth term of the series where the initial term has index 0
	 */
	public double getTerm(int n) {
		if (n >= 0) {
			return this.initialValue * Math.pow(this.ratio, n);
		} else {
			throw new IllegalArgumentException("value must be greater than 0");
		}
	}

	@Override
	public Iterator<Double> iterator() {
		return new GeometricSeriesIterator(this.initialValue, this.ratio);
	}

	/**
	 * Geometric series iterator.
	 * 
	 * @author Sam Wilson
	 * 
	 */
	private class GeometricSeriesIterator implements Iterator<Double> {

		/**
		 * initial value
		 */
		private double initialValue;

		/**
		 * common ratio
		 */
		private double ratio;

		/**
		 * index of current position in the series
		 */
		private int n;

		/**
		 * Constructs a geometric series interator given an initial value and
		 * the common ratio
		 * 
		 * @param initialValue
		 *            initial value
		 * @param ratio
		 *            common ratio
		 */
		public GeometricSeriesIterator(double initialValue, double ratio) {
			super();
			this.initialValue = initialValue;
			this.ratio = ratio;
			this.n = -1;
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Double next() {
			this.n++;
			return this.initialValue * Math.pow(this.ratio, this.n);

		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove is not supported");
		}

	}
}
