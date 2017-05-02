package org.gt4j.annas.math;

/**
 * Provides additional methods to {@link java.lang.Math}
 * 
 * @author Sam Wilson
 * 
 */
public class MathUtil {

	private MathUtil() {
	};

	/**
	 * Log of a number given a base
	 * 
	 * @param n
	 *            number
	 * @param base
	 *            base
	 * @return log_base n
	 */
	public static double log(int n, int base) {
		double d = Math.log(n) / Math.log(base);
		if (Math.abs(d) == 0) {
			return 0;
		}
		return d;
	}

	/**
	 * Calculates the complex exponential function. e^(x*i) = cos x + i * sin x
	 * 
	 * @param x
	 *            angle in radians
	 * @return complex exponential function
	 */
	public static Complex cis(double x) {
		return new Complex(Math.cos(x), Math.sin(x));
	}

	/**
	 * Computes the greatest common divisor of the parameters (not recursive).
	 * 
	 * @param a
	 * @param b
	 * @return the greatest common divisor of the two integers
	 */

	public static long gcd(long a, long b) {
		if (a < 0 || b < 0) {
			throw new IllegalArgumentException(
					"Parameters must be nonnegative integers");
		}
		if (b > a) {
			long t = a;
			a = b;
			b = t;
		}
		long tmp;
		while (b != 0) {
			tmp = b;
			b = a % b;
			a = tmp;
		}
		return a;
	}

	/**
	 * Computes the greatest common divisor of the parameters (not recursive).
	 * 
	 * @param a
	 * @param b
	 * @return the greatest common divisor of the two integers
	 */
	public static long gcd(int a, int b) {
		if (a < 0 || b < 0) {
			throw new IllegalArgumentException(
					"Parameters must be nonnegative integers");
		}
		if (b > a) {
			int t = a;
			a = b;
			b = t;
		}
		int tmp;
		while (b != 0) {
			tmp = b;
			b = a % b;
			a = tmp;
		}
		return a;
	}

	/**
	 * Computes the coefficients of the Bezout identity. ax + by = gcd(a,b)
	 * 
	 * @param a
	 * @param b
	 * @return the Bezout coeffecient of the first parameter
	 */
	public static long extended_gcd(long a, long b) {
		long s = 0;
		long t = 1;
		long r = b;
		long s_old = 1;
		long t_old = 0;
		long r_old = a;

		long quotient;
		long tmp;
		while (r != 0) {
			quotient = r_old / r;
			tmp = r_old;
			r_old = r;
			r = tmp - quotient * r;

			tmp = s_old;
			s_old = s;
			s = tmp - quotient * s;

			tmp = t_old;
			t_old = t;
			t = tmp - quotient * t;

		}
		return s_old;
	}

	/**
	 * Computes the coefficients of the Bezout identity. ax + by = gcd(a,b)
	 * 
	 * @param a
	 * @param b
	 * @return the Bezout coeffecient of the first parameter
	 */
	public static int extended_gcd(int a, int b) {
		int s = 0;
		int t = 1;
		int r = b;
		int s_old = 1;
		int t_old = 0;
		int r_old = a;

		int quotient;
		int tmp;
		while (r != 0) {
			quotient = r_old / r;
			tmp = r_old;
			r_old = r;
			r = tmp - quotient * r;

			tmp = s_old;
			s_old = s;
			s = tmp - quotient * s;

			tmp = t_old;
			t_old = t;
			t = tmp - quotient * t;

		}
		return s_old;
	}

	/**
	 * Computes the lowest integer x such that x satisfies;
	 * 
	 * x = a_0 mod n_0 
	 * 		`` '' 
	 * x = a_(k-2) mod n_(k-2)
	 * x = a_(k-1) mod n_(k-1)
	 * 
	 * where k is the number of simultaneous congruences.
	 * 
	 * 
	 * @param n
	 *            a set of positive coprime integers
	 * @param a
	 *            a set of integers
	 * @return an integer satisfying the above set of equations
	 */
	public static int chinese_remainder(int[] n, int[] a) {
		int e;
		int N = 1;
		int sum = 0;

		for (int i = 0; i < n.length; i++) {
			N = N * n[i];
		}

		for (int i = 0; i < n.length; i++) {
			e = N / n[i];
			sum = sum + a[i] * extended_gcd(e, n[i]) * e;
		}

		return sum % N;
	}

	/**
	 * Computes the lowest integer x such that x satisfies;
	 * 
	 * x = a_0 mod n_0
	 * 		 `` '' 
	 * x = a_(k-2) mod n_(k-2) 
	 * x = a_(k-1) mod n_(k-1)
	 * 
	 * where k is the number of simultaneous congruences.
	 * 
	 * 
	 * @param n
	 *            a set of positive coprime integers
	 * @param a
	 *            a set of integers
	 * @return an integer satisfying the above set of equations
	 */
	public static long chinese_remainder(long[] n, long[] a) {
		long e;

		long N = 1;
		long sum = 0;

		for (int i = 0; i < n.length; i++) {
			N = N * n[i];
		}

		for (int i = 0; i < n.length; i++) {
			e = N / n[i];
			sum = sum + a[i] * extended_gcd(e, n[i]) * e;
		}

		return sum % N;
	}

}
