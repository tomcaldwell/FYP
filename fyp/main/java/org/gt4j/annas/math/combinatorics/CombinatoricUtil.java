package org.gt4j.annas.math.combinatorics;

/**
 * Provides combinatoric utilities
 * 
 * @author Sam Wilson
 * 
 */
public class CombinatoricUtil {

	/**
	 * Calculates the greatest common divisor of two numbers.
	 * 
	 * @param l
	 *            first number
	 * @param m
	 *            second number
	 * @return Greatest common divisor of two given numbers.
	 */
	public static long gcd(long l, long m) {
		if (l == 0)
			return m;
		if (m == 0)
			return l;
		if (l == m)
			return l;
		if (l == 1 || m == 1)
			return 1;
		if ((l % 2 == 0) && (m % 2 == 0))
			return 2 * gcd(l / 2, m / 2);
		if ((l % 2 == 0) && (m % 2 != 0))
			return gcd(l / 2, m);
		if ((l % 2 != 0) && (m % 2 == 0))
			return gcd(l, m / 2);
		return gcd(m, Math.abs(l - m));
	}

	/**
	 * Calculates the lowest common multiple.
	 * 
	 * @param l
	 *            first number
	 * @param m
	 *            second number
	 * @return Lowest common multiple of two given numbers.
	 */
	public static long lcm(long l, long m) {
		return (l * m) / gcd(l, m);
	}

	/**
	 * Calculates the number of ways of selecting k elements from an n element
	 * set where order does not matter.
	 * 
	 * @param n
	 *            Number of elements in the set
	 * @param k
	 *            Number of elements to select
	 * @return Number of ways of selecting k elements from n elements where
	 *         order does not matter.
	 */
	public static long nChooseK(long n, long k) {

		if (0 <= k && k <= n) {
			return factorial(n) / (factorial(k) * factorial(n - k));
		} else {
			throw new IllegalArgumentException("0 <= k <= n");
		}
	}

	/**
	 * Calculates the number of ways of selecting k elements from an n element
	 * set where order matters.
	 * 
	 * @param n
	 *            Number of elements in the set.
	 * @param k
	 *            Number of elements to select.
	 * @return Number of ways of selecting k element from an n element set where
	 *         order matters.
	 */
	public static long permutations(long n, long k) {
		return (long) Math.pow(n, k);
	}

	/**
	 * Calculates the number of ways of selecting k elements from an n element
	 * set without repetitions where order matters.
	 * 
	 * @param n
	 *            Number of elements in the set.
	 * @param k
	 *            Number of elements to select.
	 * @return Number of ways of selecting k element from an n element set
	 *         without repetitions where order matters.
	 */
	public static long permutationsNoRepetition(long n, long k) {
		if (k <= n) {
			return factorial(n) / factorial(n - k);
		} else {
			return 0;
		}
	}

	/**
	 * Calculates the factorial of n
	 * 
	 * @param n
	 *            n must be positive
	 * @return n!
	 */
	public static long factorial(long n) {
		if (n >= 0) {
			long val = 1;

			for (int i = 1; i <= n; i++) {
				val *= i;
			}
			return val;
		} else {
			throw new IllegalArgumentException("n >=0");
		}
	}

	/**
	 * Calculates the nth Catalan number
	 * 
	 * @param n
	 *            nth number
	 * @return the nth Catalan number.
	 */
	public static long catalan(long n) {
		if (n >= 0) {
			return nChooseK(2 * n, n) / (n + 1);
		} else {
			throw new IllegalArgumentException("n >=0");
		}
	}

	/**
	 * Calculates the sterling number given n and k. One interpretation of
	 * sterling numbers is the number of ways to permute an n element set into k
	 * cycles.
	 * 
	 * @param n
	 *            Number of elements in the set
	 * @param k
	 *            Number of cycles.
	 * @return s(n,k)
	 */
	public static long sterling(long n, long k) {
		if (n == 0 && k == 0) {
			return 1;
		}

		if (n == 0 || k == 0) {
			return 0;
		}

		return k * sterling(n - 1, k) + sterling(n - 1, k - 1);
	}

	/**
	 * Returns the number of non-crossing partitions of n elements into k parts
	 * 
	 * @param n number of elements
	 * @param k number of parts
	 * @return number of non-crossing partitions of an n element set into k
	 *         parts
	 */
	public static long narayana(long n, long k) {
		if (k <= n) {
			return CombinatoricUtil.nChooseK(n, k)
					* CombinatoricUtil.nChooseK(n, k - 1) / n;
		} else {
			throw new IllegalArgumentException("k <=n");
		}
	}
}
