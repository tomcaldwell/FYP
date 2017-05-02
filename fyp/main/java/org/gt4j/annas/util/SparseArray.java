/*
 * annas : Graph implementation and algorithm package
 * 
 * Project Info:  http://annas.googlecode.com
 * Project Creator:  Sam Wilson
 * 
 * Copyright (C) 2008-2014  Sam Wilson
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gt4j.annas.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SparseArrays map integers to Objects. Unlike a normal array of Objects, there
 * can be gaps in the indices. It is intended to be more efficient than using a
 * HashMap to map Integers to Objects.
 * 
 * @param <T>
 *            element type
 */
public class SparseArray<T> implements Iterable<T> {

	private static final Object DELETED = new Object();
	private boolean mGarbage;

	private int[] mKeys;
	private Object[] mValues;
	private int mSize;

	/**
	 * Creates a new SparseArray containing no mappings.
	 */
	public SparseArray() {
		this(10);
	}

	/**
	 * Creates a new SparseArray containing no mappings that will not require
	 * any additional memory allocation to store the specified number of
	 * mappings.
	 * 
	 * @param initialCapacity
	 *            ensures the array can accommodate a given value without
	 *            resizing
	 */
	public SparseArray(int initialCapacity) {
		initialCapacity = idealArraySize(initialCapacity);

		this.mKeys = new int[initialCapacity];
		this.mValues = new Object[initialCapacity];
		this.mSize = 0;
	}

	/**
	 * Gets the Object mapped from the specified key, or <code>null</code> if no
	 * such mapping has been made.
	 * 
	 * @param key
	 *            index
	 * @return value associated with the value
	 */
	public T get(int key) {
		return get(key, null);
	}

	/**
	 * Gets the Object mapped from the specified key, or the specified Object if
	 * no such mapping has been made.
	 * 
	 * @param key
	 *            key
	 * @param valueIfKeyNotFound
	 *            default value if key is not in the array
	 * @return value or given value
	 */
	@SuppressWarnings("unchecked")
	public T get(int key, T valueIfKeyNotFound) {
		int i = binarySearch(this.mKeys, 0, this.mSize, key);

		if (i < 0 || this.mValues[i] == DELETED) {
			return valueIfKeyNotFound;
		} else {
			return (T) this.mValues[i];
		}
	}

	/**
	 * Removes the mapping from the specified key, if there was any.
	 * 
	 * @param key
	 *            key to delete
	 */
	public void delete(int key) {
		int i = binarySearch(this.mKeys, 0, this.mSize, key);

		if (i >= 0) {
			if (this.mValues[i] != DELETED) {
				this.mValues[i] = DELETED;
				this.mGarbage = true;
			}
		}
	}

	/**
	 * Alias for {@link #delete(int)}.
	 * 
	 * @param key
	 *            key to remove
	 */
	public void remove(int key) {
		delete(key);
	}

	/**
	 * Adds a mapping from the specified key to the specified value, replacing
	 * the previous mapping from the specified key if there was one.
	 * @param key  key of value
	 * @param value value of the key
	 */
	public void put(int key, T value) {
		int i = binarySearch(this.mKeys, 0, this.mSize, key);

		if (i >= 0) {
			this.mValues[i] = value;
		} else {
			i = ~i;

			if (i < this.mSize && this.mValues[i] == DELETED) {
				this.mKeys[i] = key;
				this.mValues[i] = value;
				return;
			}

			if (this.mGarbage && this.mSize >= this.mKeys.length) {
				gc();

				// Search again because indices may have changed.
				i = ~binarySearch(this.mKeys, 0, this.mSize, key);
			}

			if (this.mSize >= this.mKeys.length) {
				int n = idealArraySize(this.mSize + 1);

				int[] nkeys = new int[n];
				Object[] nvalues = new Object[n];

				System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
				System.arraycopy(this.mValues, 0, nvalues, 0,
						this.mValues.length);

				this.mKeys = nkeys;
				this.mValues = nvalues;
			}

			if (this.mSize - i != 0) {

				System.arraycopy(this.mKeys, i, this.mKeys, i + 1, this.mSize
						- i);
				System.arraycopy(this.mValues, i, this.mValues, i + 1,
						this.mSize - i);
			}

			this.mKeys[i] = key;
			this.mValues[i] = value;
			this.mSize++;
		}
	}

	/**
	 * Returns the number of key-value mappings that this SparseArray currently
	 * stores.
	 * @return gets the number of key-value mappings
	 */
	public int size() {
		if (this.mGarbage) {
			gc();
		}

		return this.mSize;
	}

	/**
	 * Given an index in the range <code>0...size()-1</code>, returns the key
	 * from the <code>index</code>th key-value mapping that this SparseArray
	 * stores.
	 * 
	 * @param index index of key to get
	 * @return gets the key at the given index
	 */
	public int keyAt(int index) {
		if (this.mGarbage) {
			gc();
		}

		return this.mKeys[index];
	}

	/**
	 * Given an index in the range <code>0...size()-1</code>, returns the value
	 * from the <code>index</code>th key-value mapping that this SparseArray
	 * stores.
	 * 
	 * @param index index of element to get
	 * @return value associated with the index
	 */
	@SuppressWarnings("unchecked")
	public T valueAt(int index) {
		if (this.mGarbage) {
			gc();
		}

		return (T) this.mValues[index];
	}

	/**
	 * Given an index in the range <code>0...size()-1</code>, sets a new value
	 * for the <code>index</code>th key-value mapping that this SparseArray
	 * stores.
	 * 
	 * @param index index
	 * @param value value
	 */
	public void setValueAt(int index, T value) {
		if (this.mGarbage) {
			gc();
		}

		this.mValues[index] = value;
	}

	/**
	 * Returns the index for which {@link #keyAt} would return the specified
	 * key, or a negative number if the specified key is not mapped.
	 * 
	 * @param key key
	 * @return index for which {@link #keyAt} would return the specified key
	 */
	public int indexOfKey(int key) {
		if (this.mGarbage) {
			gc();
		}

		return binarySearch(this.mKeys, 0, this.mSize, key);
	}

	/**
	 * Returns an index for which {@link #valueAt} would return the specified
	 * key, or a negative number if no keys map to the specified value. Beware
	 * that this is a linear search, unlike lookups by key, and that multiple
	 * keys can map to the same value and this will find only one of them.
	 * 
	 * @param value value
	 * @return first instance which is encountered that maps the index to the
	 *         value
	 */
	public int indexOfValue(T value) {
		if (this.mGarbage) {
			gc();
		}

		for (int i = 0; i < this.mSize; i++)
			if (this.mValues[i] == value)
				return i;

		return -1;
	}

	/**
	 * Removes all key-value mappings from this SparseArray.
	 */
	public void clear() {
		int n = this.mSize;
		Object[] values = this.mValues;

		for (int i = 0; i < n; i++) {
			values[i] = null;
		}

		this.mSize = 0;
		this.mGarbage = false;
	}

	/**
	 * Puts a key/value pair into the array, optimizing for the case where the
	 * key is greater than all existing keys in the array.
	 * 
	 * @param key key
	 * @param value value
	 */
	public void append(int key, T value) {
		if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
			put(key, value);
			return;
		}

		if (this.mGarbage && this.mSize >= this.mKeys.length) {
			gc();
		}

		int pos = this.mSize;
		if (pos >= this.mKeys.length) {
			int n = idealArraySize(pos + 1);

			int[] nkeys = new int[n];
			Object[] nvalues = new Object[n];

			System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
			System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);

			this.mKeys = nkeys;
			this.mValues = nvalues;
		}

		this.mKeys[pos] = key;
		this.mValues[pos] = value;
		this.mSize = pos + 1;
	}

	private static int binarySearch(int[] a, int start, int len, int key) {
		int high = start + len, low = start - 1, guess;

		while (high - low > 1) {
			guess = (high + low) / 2;

			if (a[guess] < key)
				low = guess;
			else
				high = guess;
		}

		if (high == start + len)
			return ~(start + len);
		else if (a[high] == key)
			return high;
		else
			return ~high;
	}

	private static int idealArraySize(int need) {
		need = need * 4;
		for (int i = 4; i < 32; i++)
			if (need <= (1 << i) - 12)
				return (1 << i) - 12;

		return need / 4;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterator() {
		List<T> l = new ArrayList<T>(this.mValues.length);
		for (Object t : this.mValues) {
			l.add((T) t);
		}
		return l.iterator();
	}

	private void gc() {

		int n = this.mSize;
		int o = 0;
		int[] keys = this.mKeys;
		Object[] values = this.mValues;

		for (int i = 0; i < n; i++) {
			Object val = values[i];

			if (val != DELETED) {
				if (i != o) {
					keys[o] = keys[i];
					values[o] = val;
				}

				o++;
			}
		}

		this.mGarbage = false;
		this.mSize = o;

	}

}
