package org.gt4j.annas.math.combinatorics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Adapted from <a
 * href="http://code.google.com/p/combinatoricslib/">combinatoricslib</a>
 * 
 * @author Dmytro Paukov
 * @author Sam Wilson
 * @see SimpleCombinationIterator
 * @param <T>
 *            Type of elements in the combination
 */
public class SimpleCombinationGenerator<T> implements Iterable<List<T>> {

	protected final List<T> originalVector;
	protected final int combinationLength;

	/**
	 * Constructor
	 * 
	 * @param originalVector
	 *            Original vector which is used for generating the combination
	 * @param combinationsLength
	 *            Length of the combinations
	 */
	public SimpleCombinationGenerator(Collection<T> originalVector,
			int combinationsLength) {
		this.originalVector = new ArrayList<T>(originalVector);
		this.combinationLength = combinationsLength;
	}

	/**
	 * Returns the original vector/set
	 * 
	 * @return Returns the originalVector.
	 */
	public List<T> getOriginalVector() {
		return this.originalVector;
	}

	/**
	 * Returns the length of the combinations
	 * 
	 * @return Returns the combinationLength.
	 */
	public int getCombinationLength() {
		return this.combinationLength;
	}

	/**
	 * Returns the number of the generated combinations
	 * 
	 * @return gets the number of generated combinations
	 */
	public long getNumberOfGeneratedObjects() {
		return CombinatoricUtil.nChooseK(this.originalVector.size(),
				this.combinationLength);
	}

	/**
	 * Creates an iterator of the simple combinations (without repetitions)
	 */
	@Override
	public Iterator<List<T>> iterator() {
		return new SimpleCombinationIterator<T>(this);
	}
}
