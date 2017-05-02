package org.gt4j.annas.graph;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 
 * A MultiMap is a Map with slightly different semantics. Putting a value into
 * the map will add the value to a Collection at that key. Getting a value will
 * return a Collection, holding all the values put to that key.
 * 
 * This implementation uses an ArrayList as the collection. The internal storage
 * list is made available without cloning via the get(Object) and entrySet()
 * methods. The implementation returns null when there are no values mapped to a
 * key.
 * 
 * @param <K>
 *            Key type
 * @param <V>
 *            Value type
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MultiHashMap<K, V> extends HashMap {

	/**
	 * Inner iterator to view the elements.
	 */
	private class ValueIterator implements Iterator<V> {
		private Iterator<V> backedIterator;
		private Iterator<V> tempIterator;

		private ValueIterator() {
			this.backedIterator = MultiHashMap.super.values().iterator();
		}

		@Override
		public boolean hasNext() {
			return searchNextIterator();
		}

		@Override
		public V next() {
			if (searchNextIterator() == false) {
				throw new NoSuchElementException();
			}
			return this.tempIterator.next();
		}

		@Override
		public void remove() {
			if (this.tempIterator == null) {
				throw new IllegalStateException();
			}
			this.tempIterator.remove();
		}

		private boolean searchNextIterator() {
			while (this.tempIterator == null
					|| this.tempIterator.hasNext() == false) {
				if (this.backedIterator.hasNext() == false) {
					return false;
				}
				this.tempIterator = ((Collection) this.backedIterator.next())
						.iterator();
			}
			return true;
		}

	}

	/**
	 * Inner class to view the elements.
	 */
	private class Values extends AbstractCollection {

		@Override
		public void clear() {
			MultiHashMap.this.clear();
		}

		@Override
		public Iterator iterator() {
			return new ValueIterator();
		}

		@Override
		public int size() {
			int compt = 0;
			Iterator it = iterator();
			while (it.hasNext()) {
				it.next();
				compt++;
			}
			return compt;
		}

	}

	// backed values collection
	private transient Collection values;

	private static final long serialVersionUID = 1943563828307035349L;

	/**
	 * Constructor.
	 */
	public MultiHashMap() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param initialCapacity
	 *            the initial map capacity
	 */
	public MultiHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Constructor.
	 * 
	 * @param initialCapacity
	 *            the initial map capacity
	 * @param loadFactor
	 *            the amount 0.0-1.0 at which to resize the map
	 */
	public MultiHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	/**
	 * Clear the map.
	 * 
	 * This clears each collection in the map, and so may be slow.
	 */
	@Override
	public void clear() {
		// For gc, clear each list in the map
		Set pairs = super.entrySet();
		Iterator pairsIterator = pairs.iterator();
		while (pairsIterator.hasNext()) {
			Map.Entry keyValuePair = (Map.Entry) pairsIterator.next();
			Collection coll = (Collection) keyValuePair.getValue();
			coll.clear();
		}
		super.clear();
	}

	// -----------------------------------------------------------------------
	/**
	 * Clones the map creating an independent copy.
	 * 
	 * The clone will shallow clone the collections as well as the map.
	 * 
	 * @return the cloned map
	 */
	@Override
	public Object clone() {
		MultiHashMap cloned = (MultiHashMap) super.clone();

		// clone each Collection container
		for (Iterator it = cloned.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Collection coll = (Collection) entry.getValue();
			Collection newColl = createCollection(coll);
			entry.setValue(newColl);
		}
		return cloned;
	}

	/**
	 * Checks whether the map contains the value specified.
	 * 
	 * This checks all collections against all keys for the value, and thus
	 * could be slow.
	 * 
	 * @param value
	 *            the value to search for
	 * @return true if the map contains the value
	 */
	@Override
	public boolean containsValue(Object value) {
		Set pairs = super.entrySet();

		if (pairs == null) {
			return false;
		}
		Iterator pairsIterator = pairs.iterator();
		while (pairsIterator.hasNext()) {
			Map.Entry keyValuePair = (Map.Entry) pairsIterator.next();
			Collection coll = (Collection) keyValuePair.getValue();
			if (coll.contains(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the collection at the specified key contains the value.
	 * 
	 * @param key
	 *            the key to associate the value with
	 * @param value
	 *            the value to search for
	 * @return true if the map contains the value
	 * 
	 */
	public boolean containsValue(Object key, Object value) {
		Collection coll = getCollection(key);
		if (coll == null) {
			return false;
		}
		return coll.contains(value);
	}

	/**
	 * Creates a new instance of the map value Collection container.
	 * 
	 * This method can be overridden to use your own collection type.
	 * 
	 * @param coll
	 *            the collection to copy, may be null
	 * @return the new collection
	 */
	protected Collection createCollection(Collection coll) {
		if (coll == null) {
			return new ArrayList();
		} else {
			return new ArrayList(coll);
		}
	}

	/**
	 * Gets the collection mapped to the specified key. This method is a
	 * convenience method to typecast the result of get(key).
	 * 
	 * @param key
	 *            the key to retrieve
	 * @return the collection mapped to the key, null if no mapping
	 * 
	 */
	public Collection getCollection(Object key) {
		return (Collection) get(key);
	}

	/**
	 * Gets an iterator for the collection mapped to the specified key.
	 * 
	 * @param key
	 *            the key to get an iterator for
	 * @return the iterator of the collection at the key, empty iterator if key
	 *         not in map
	 * 
	 */
	public Iterator iterator(Object key) {
		Collection coll = getCollection(key);
		if (coll == null) {
			return new ArrayList().iterator();
		}
		return coll.iterator();
	}

	/**
	 * Adds the value to the collection associated with the specified key.
	 * 
	 * Unlike a normal Map the previous value is not replaced. Instead the new
	 * value is added to the collection stored against the key.
	 * 
	 * @param key
	 *            the key to store against
	 * @param value
	 *            the value to add to the collection at the key
	 * @return the value added if the map changed and null if the map did not
	 *         change
	 */
	@Override
	public Object put(Object key, Object value) {
		// NOTE:: put is called during deserialization in JDK < 1.4 !!!!!!
		// so we must have a readObject()
		Collection coll = getCollection(key);
		if (coll == null) {
			coll = createCollection(null);
			super.put(key, coll);
		}
		boolean results = coll.add(value);
		return (results ? value : null);
	}

	/**
	 * Adds a collection of values to the collection associated with the
	 * specified key.
	 * 
	 * @param key
	 *            the key to store against
	 * @param values
	 *            the values to add to the collection at the key, null ignored
	 * @return true if this map changed
	 * 
	 */
	public boolean putAll(Object key, Collection values) {
		if (values == null || values.size() == 0) {
			return false;
		}
		Collection coll = getCollection(key);
		if (coll == null) {
			coll = createCollection(values);
			if (coll.size() == 0) {
				return false;
			}
			super.put(key, coll);
			return true;
		} else {
			return coll.addAll(values);
		}
	}

	/**
	 * Read the object during deserialization.
	 * @param s ObjectInputStream 
	 * 
	 * @throws IOException Caused by the object failing to be reconstructed
	 * @throws ClassNotFoundException Caused if the class can not be found for the object being constructed
	 */
	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();

		String version = "1.2";

		version = System.getProperty("java.version");

		if (version.startsWith("1.2") || version.startsWith("1.3")) {
			for (Iterator iterator = entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				// put has created a extra collection level, remove it
				super.put(entry.getKey(), ((Collection) entry.getValue())
						.iterator().next());
			}
		}
	}

	/**
	 * Removes a specific value from map.
	 * 
	 * The item is removed from the collection mapped to the specified key.
	 * Other values attached to that key are unaffected.
	 * 
	 * If the last value for a key is removed, null will be returned from a
	 * subsequant get(key).
	 * 
	 * @param key
	 *            the key to remove from
	 * @param item
	 *            the value to remove
	 * @return the value removed (which was passed in), null if nothing removed
	 */
	public boolean remove(Object key, Object item) {
		Collection valuesForKey = getCollection(key);
		if (valuesForKey == null) {
			return false;
		}
		valuesForKey.remove(item);

		if (valuesForKey.isEmpty()) {
			remove(key);
		}
		return true;
	}

	/**
	 * Gets the size of the collection mapped to the specified key.
	 * 
	 * @param key
	 *            the key to get size for
	 * @return the size of the collection at the key, zero if key not in map
	 * 
	 */
	public int size(Object key) {
		Collection<K> coll = getCollection(key);
		if (coll == null) {
			return 0;
		}
		return coll.size();
	}

	/**
	 * Gets the total size of the map by counting all the values.
	 * 
	 * @return the total size of the map counting all values
	 * 
	 */
	public int totalSize() {
		int total = 0;
		Collection<V> values = super.values();
		for (Iterator<V> it = values.iterator(); it.hasNext();) {
			Collection<V> coll = (Collection<V>) it.next();
			total += coll.size();
		}
		return total;
	}

	/**
	 * Gets a collection containing all the values in the map.
	 * 
	 * This returns a collection containing the combination of values from all
	 * keys.
	 * 
	 * @return a collection view of the values contained in this map
	 */
	@Override
	public Collection<V> values() {
		Collection<V> vs = this.values;
		return (vs != null ? vs : (this.values = new Values()));
	}

}
