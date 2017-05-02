package org.gt4j.annas.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of a union/find data structure
 * 
 * @author Sam Wilson
 * 
 * @param <E>
 *            Element type
 */
public class DisjointSet<E> {

	private final HashMap<E, Identifier<E>> objectToSetIdentifiers;

	/**
	 * Constructs a DisjointSet, making a new set for each item in the given Set
	 * 
	 * @param vs Set of elements to be members of disjoint sets
	 */
	public DisjointSet(Set<E> vs) {
		super();
		this.objectToSetIdentifiers = new HashMap<E, Identifier<E>>();
		for (E v : vs) {
			this.makeSet(v);
		}
	}

	/**
	 * Default constructor
	 */
	public DisjointSet() {
		super();
		this.objectToSetIdentifiers = new HashMap<E, Identifier<E>>();
	}

	/**
	 * Finds the identifier of a given element
	 * 
	 * @param o
	 *            element to consider
	 * @return Sent identifier
	 */
	public E findSet(E o) {
		DisjointSet.Identifier<E> node = this.objectToSetIdentifiers.get(o);
		if (node == null) {
			return null;
		}
		if (o != node.parent) {
			node.setParent(this.findSet(node.parent));
		}
		return node.getParent();
	}

	/**
	 * Adds a new set to the group of disjoint sets for the given object. It is
	 * assumed that the object does not yet belong to any set.
	 * 
	 * @param o
	 *            The object to add to the set
	 */
	public void makeSet(E o) {
		this.objectToSetIdentifiers.put(o, new Identifier<E>(o, 0));
	}

	/**
	 * Removes all elements belonging to the set of the given object.
	 * 
	 * @param o
	 *            The object to remove
	 */
	public void removeSet(E o) {
		E set = this.findSet(o);
		if (set == null)
			return;
		for (Iterator<E> it = this.objectToSetIdentifiers.keySet().iterator(); it
				.hasNext();) {
			E next = it.next();
			// remove the set representative last, otherwise findSet will fail
			if (next != set && findSet(next) == set) {
				it.remove();
			}
		}
		this.objectToSetIdentifiers.remove(set);
	}

	/**
	 * Unions the set represented by token x with the set represented by token
	 * y. Has no effect if either x or y is not in the disjoint set, or if they
	 * already belong to the same set.
	 * 
	 * @param x
	 *            The first set to union
	 * @param y
	 *            The second set to union
	 */
	public void union(E x, E y) {
		E setX = this.findSet(x);
		E setY = this.findSet(y);
		if (setX == null || setY == null || setX == setY)
			return;
		Identifier<E> nodeX = this.objectToSetIdentifiers.get(setX);
		Identifier<E> nodeY = this.objectToSetIdentifiers.get(setY);

		if (nodeX.getRank() > nodeY.getRank()) {
			nodeY.setParent(x);
		} else {
			nodeX.setParent(y);

			if (nodeX.getRank() == nodeY.getRank()) {
				nodeY.setRank(nodeY.getRank() + 1);
			}
		}
	}

	/**
	 * A Identifier in the disjoint set forest. Each tree in the forest is a
	 * disjoint set, where the root of the tree is the set identifier.
	 */
	private static class Identifier<V> {

		/**
		 * Used for optimization
		 */
		private int rank;

		/**
		 * Identifier of the disjoint set
		 */
		private V parent;

		Identifier(V parent, int rank) {
			this.parent = parent;
			this.rank = rank;
		}

		public int getRank() {
			return this.rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		public V getParent() {
			return this.parent;
		}

		public void setParent(V parent) {
			this.parent = parent;
		}

	}
}
