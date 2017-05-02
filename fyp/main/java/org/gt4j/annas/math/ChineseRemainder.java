package org.gt4j.annas.math;

import java.util.LinkedList;
import java.util.List;


public class ChineseRemainder {

	private List<Long> mod;

	private List<Long> as;

	public ChineseRemainder() {
		super();
		this.mod = new LinkedList<Long>();
		this.as = new LinkedList<Long>();
	}

	public void addEquation(long a, long n) {
		if (!coprime(n)) {
			throw new IllegalArgumentException(
					"second parameter must be coprime with existing mods");
		}
		this.mod.add(n);
		this.as.add(a);
	}

	public long solve() {
		return MathUtil.chinese_remainder(toPrimitives(this.mod),
				toPrimitives(as));
	}

	private boolean coprime(long n) {
		for (long l : this.mod) {
			if (MathUtil.gcd(n, l) != 0) {
				return false;
			}
		}
		return true;
	}

	private long[] toPrimitives(List<Long> objects) {

		long[] primitives = new long[objects.size()];
		for (int i = 0; i < objects.size(); i++)
			primitives[i] = objects.get(i);

		return primitives;
	}
}
