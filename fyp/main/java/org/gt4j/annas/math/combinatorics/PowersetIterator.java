package org.gt4j.annas.math.combinatorics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator of powerset for a ground set.
 * 
 * Order is set by Constructor
 * 
 * For reverse order see ReversePowersetIterator
 * 
 * @author Sam
 * 
 * @param <T>
 *            Collection type
 */
public final class PowersetIterator<T> implements Iterator<Collection<T>> {

	private BigInteger index = BigInteger.ZERO;

	private BigInteger numberOfSubsets;

	private ArrayList<T> groundSet;

	private boolean smallestFirst;

	/**
	 * Creates a new power set iterator starting with the empty set.
	 * 
	 * @param input input collection
	 */
	public PowersetIterator(Collection<T> input) {
		super();
		this.groundSet = new ArrayList<T>(input.size());
		this.groundSet.addAll(input);
		this.numberOfSubsets = BigInteger.valueOf((long) Math.pow(2,
				this.groundSet.size()));
		this.smallestFirst = true;
	}

	/**
	 * Creates a new power set iterator starting with the ground set.
	 * 
	 * @param input Input collection
	 * @param reverseOrder if the order is reversed
	 */
	public PowersetIterator(Collection<T> input, boolean reverseOrder) {
		super();
		this.groundSet = new ArrayList<T>(input.size());
		this.groundSet.addAll(input);
		this.numberOfSubsets = BigInteger.valueOf((long) Math.pow(2,
				this.groundSet.size()));
		this.smallestFirst = !reverseOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.index.compareTo(this.numberOfSubsets) < 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Collection<T> next() {

		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Collection<T> ret = new HashSet<T>();
		for (int i = 0; i < this.groundSet.size(); i++) {
			if (this.smallestFirst) {
				if (this.index.testBit(i))
					ret.add(this.groundSet.get(i));
			} else {
				if (!this.index.testBit(i))
					ret.add(this.groundSet.get(i));
			}
		}
		this.index = this.index.add(BigInteger.ONE);
		return ret;
	}

	/*
	 * Method not supported (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Method not supported");
	}
}
