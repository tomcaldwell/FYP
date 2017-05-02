package org.gt4j.annas.math.combinatorics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator for the simple combination generator
 * 
 * Adapted from <a
 * href="http://code.google.com/p/combinatoricslib/">combinatoricslib</a>
 * 
 * @author Dmytro Paukov
 * @author Sam Wilson
 * @see List
 * @see SimpleCombinationGenerator
 * @param <T>
 *            Type of the elements in the combinations
 */
public class SimpleCombinationIterator<T> implements Iterator<List<T>> {

	/**
	 * Generator
	 */
	protected final SimpleCombinationGenerator<T> generator;

	/**
	 * Current simple combination
	 */
	protected List<T> currentSimpleCombination;

	/**
	 * Index of the current combination
	 */
	protected long currentIndex;

	/**
	 * Size of the original vector/set
	 */
	protected final int lengthN;

	/**
	 * Size of the generated combination.
	 */
	protected final int lengthK;

	/**
	 * Helper array
	 */
	private int[] bitVector;

	/**
	 * Criteria to stop iteration
	 */
	private int endIndex;

	/**
	 * Constructor
	 * 
	 * @param generator
	 *            Generator of the simple combinations
	 */
	public SimpleCombinationIterator(SimpleCombinationGenerator<T> generator) {
		this.generator = generator;
		this.lengthN = this.generator.getOriginalVector().size();
		this.lengthK = this.generator.getCombinationLength();
		this.currentSimpleCombination = new ArrayList<T>();
		this.bitVector = new int[this.lengthK + 1];
		this.endIndex = 0;
		this.currentIndex = 0;
		this.init();
	}

	/**
	 * Initialization
	 */
	private void init() {

		for (int i = 0; i <= this.lengthK; i++) {
			this.bitVector[i] = i;
		}
		if (this.lengthN > 0) {
			this.endIndex = 1;
		}
		this.currentIndex = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return !((this.endIndex == 0) || (this.lengthK > this.lengthN));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public List<T> next() {
		this.currentIndex++;

		for (int i = 1; i <= this.lengthK; i++) {
			final int index = this.bitVector[i] - 1;
			if (this.generator.getOriginalVector().size() > 0) {
				try {

					this.currentSimpleCombination.set(i - 1, this.generator
							.getOriginalVector().get(index));
				} catch (final IndexOutOfBoundsException ex) {
					this.currentSimpleCombination.add(i - 1, this.generator
							.getOriginalVector().get(index));
				}
			}
		}

		this.endIndex = this.lengthK;

		while (this.bitVector[this.endIndex] == ((this.lengthN - this.lengthK) + this.endIndex)) {
			this.endIndex--;
			if (this.endIndex == 0) {
				break;
			}
		}
		this.bitVector[this.endIndex]++;
		for (int i = this.endIndex + 1; i <= this.lengthK; i++) {
			this.bitVector[i] = this.bitVector[i - 1] + 1;
		}

		// return the current combination
		return new ArrayList<T>(this.currentSimpleCombination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleCombinationIterator=[#" + this.currentIndex + ", "
				+ this.currentSimpleCombination + ']';
	}

}
