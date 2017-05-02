package org.gt4j.annas.util;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of a set using an array.
 * 
 * @author Sam Wilson
 * 
 * @param <E>
 *            Element type
 */
public class ArraySet<E> extends AbstractSet<E> {

	/**
	 * The array buffer into which the elements of the ArraySet are stored. The
	 * capacity of the ArraySet is the length of this array buffer.
	 */
	private transient Object[] elementData;

	/**
	 * The size of the ArraySet (the number of elements it contains).
	 * 
	 * @serial
	 */
	private int size;

	private EqualityChecker<E> checker;

	private int modCount;

	/**
	 * Constructs an empty list with the specified initial capacity.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the list
	 * @exception IllegalArgumentException
	 *                if the specified initial capacity is negative
	 */
	public ArraySet(int initialCapacity) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: "
					+ initialCapacity);
		this.elementData = new Object[initialCapacity];
		this.checker = new DefaultEqualityChecker<E>();
	}

	/**
	 * Constructs an empty list with the specified initial capacity and given
	 * equality checker.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the list
	 * @param checker
	 *            equality checker
	 */
	public ArraySet(int initialCapacity, EqualityChecker<E> checker) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: "
					+ initialCapacity);
		this.elementData = new Object[initialCapacity];
		this.checker = checker;
	}

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 * 
	 * @param checker
	 *            equality checker
	 */
	public ArraySet(EqualityChecker<E> checker) {
		this(10);
		if (checker != null) {
			this.checker = checker;
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * Default Constructor
	 */
	public ArraySet() {
		this(10);
		this.checker = new DefaultEqualityChecker<E>();
	}

	/**
	 * Trims the capacity of this <tt>ArraySet</tt> instance to be the list's
	 * current size. An application can use this operation to minimize the
	 * storage of an <tt>ArraySet</tt> instance.
	 */
	public void trimToSize() {
		this.modCount++;
		int oldCapacity = this.elementData.length;
		if (this.size < oldCapacity) {
			this.elementData = Arrays.copyOf(this.elementData, this.size);
		}
	}

	/**
	 * Increases the capacity of this <tt>ArraySet</tt> instance, if necessary,
	 * to ensure that it can hold at least the number of elements specified by
	 * the minimum capacity argument.
	 * 
	 * @param minCapacity
	 *            the desired minimum capacity
	 */
	public void ensureCapacity(int minCapacity) {
		this.modCount++;
		int oldCapacity = this.elementData.length;
		if (minCapacity > oldCapacity) {

			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			this.elementData = Arrays.copyOf(this.elementData, newCapacity);
		}
	}

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Clears the data structure
	 */
	@Override
	public void clear() {
		this.elementData = new Object[0];
		this.ensureCapacity(10);
		this.size = 0;
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * 
	 * @return <tt>true</tt> if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element. More
	 * formally, returns <tt>true</tt> if and only if this list contains at
	 * least one element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * 
	 * @param o
	 *            element whose presence in this list is to be tested
	 * @return <tt>true</tt> if this list contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	/**
	 * Returns the index of the first occurrence of the specified element in
	 * this list, or -1 if this list does not contain the element. More
	 * formally, returns the lowest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 * 
	 * @param o
	 *            element to get index of
	 * @return index of o
	 */
	public int indexOf(Object o) {
		if (o == null) {
			return -1;
		} else {
			for (int i = 0; i < this.size; i++)
				if (this.checker.check(o, this.elementData[i]))
					return i;
		}
		return -1;
	}

	// Positional Access Operations

	@SuppressWarnings("unchecked")
	private E elementData(int index) {
		return (E) this.elementData[index];
	}

	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this list
	 * 
	 */
	public E get(int index) {
		rangeCheck(index);

		return elementData(index);
	}

	/**
	 * Appends the specified element to the end of this list.
	 * 
	 * @param e
	 *            element to be appended to this list
	 * @return <tt>true</tt> (as specified by {@link Collection#add})
	 */
	@Override
	public boolean add(E e) {
		if (e == null || this.contains(e)) {
			return false;
		}
		this.modCount += 1;
		ensureCapacity(this.size + 1); // Increments modCount!!
		this.elementData[this.size++] = e;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean successful = true;
		for (E o : c) {
			successful &= this.add(o);
		}

		return successful;
	}

	/**
	 * Checks if the given index is in range. If not, throws an appropriate
	 * runtime exception. This method does *not* check if the index is negative:
	 * It is always used immediately prior to an array access, which throws an
	 * ArrayIndexOutOfBoundsException if index is negative.
	 */
	private void rangeCheck(int index) {
		if (index >= this.size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	/**
	 * Constructs an IndexOutOfBoundsException detail message. Of the many
	 * possible refactorings of the error handling code, this "outlining"
	 * performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + this.size;
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * 
	 * <p>
	 * The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
	 * 
	 * @return an iterator over the elements in this list in proper sequence
	 */
	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	/**
	 * Default Equality checker, uses the equals method on the object
	 * 
	 * @author Sam Wilson
	 * 
	 * @param <E>
	 */
	@SuppressWarnings("hiding")
	private class DefaultEqualityChecker<E> implements EqualityChecker<E> {

		@Override
		public boolean check(Object a, Object b) {
			return a.equals(b);
		}

	}

	/**
	 * An optimized version of AbstractList.Itr
	 */
	private class Itr implements Iterator<E> {

		private int cursor; // index of next element to return

		private int lastRet = -1; // index of last element returned; -1 if no

		@Override
		public boolean hasNext() {
			return this.cursor != ArraySet.this.size;
		}

		@Override
		@SuppressWarnings("unchecked")
		public E next() {

			int i = this.cursor;
			if (i >= ArraySet.this.size)
				throw new NoSuchElementException();
			Object[] elementData = ArraySet.this.elementData;
			this.cursor = i + 1;
			this.lastRet = i;
			return (E) elementData[this.lastRet];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove is not supported");
		}

	}
}
