package org.gt4j.annas.misc;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.WeightedEdgeInterface;

/**
 * A represents a path in a graph.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Path<V, E extends EdgeInterface<V>> {

	/**
	 * List of edges that comprise a path
	 */
	private Set<E> edges;

	/**
	 * Indicates the head of the path
	 */
	private V head;

	/**
	 * Default constructor
	 */
	public Path() {
		super();
		this.edges = new LinkedHashSet<E>();
	}

	/**
	 * Adds an edge to the path. This method checks that adding the edge will
	 * results in a path, i.e. the parameter extends the current path and that
	 * the head of the new edge does not already exist in the path.
	 * 
	 * @param e
	 *            Edge to add
	 * @return true if the edge was added successfully
	 */
	public boolean addEdge(E e) {

		V head = null;
		if (this.head == null) {
			this.head = e.getHead();
			return this.edges.add(e);
		} else {
			if (this.head.equals(e.getHead())) {
				head = e.getTail();
			} else if (this.head.equals(e.getTail())) {
				head = e.getHead();
			} else {
				return false;
			}

			if (!contains(head)) {
				this.head = head;
				return this.edges.add(e);
			}
		}
		return false;
	}

	public boolean contains(V v) {
		for (E e : this.edges) {
			if (e.getTail().equals(v) || e.getHead().equals(v)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the distance from the terminal vertices of the path
	 * 
	 * @return distance
	 */
	public double getDistance() {
		double r = 0;
		Iterator<E> i = this.edges.iterator();
		while (i.hasNext()) {
			r += (this.getWeight(i.next()));
		}
		return r;
	}

	/**
	 * Gets the number of edges in the path
	 * 
	 * @return Number of edges in the path
	 */
	public double getLength() {
		int r = 0;
		Iterator<E> i = this.edges.iterator();
		while (i.hasNext()) {
			r++;
			i.next();
		}
		return r == 0 ? 0 : r + 1;
	}

	/**
	 * Decides if the path contains the given edge
	 * 
	 * @param e
	 *            Edge
	 * @return true if the path contains the edge
	 */
	public boolean contains(E e) {
		return this.edges.contains(e);
	}

	/**
	 * Gets an iterator over the path
	 * 
	 * @return Iterator of the edges in the path
	 */
	public Iterator<E> getIterator() {
		return new Iterator<E>() {

			Iterator<E> it = Path.this.edges.iterator();

			@Override
			public boolean hasNext() {
				return this.it.hasNext();
			}

			@Override
			public E next() {
				return this.it.next();
			}

			@Override
			public void remove() {
			}
		};
	}

	private double getWeight(EdgeInterface<V> e) {
		if (e instanceof WeightedEdgeInterface<?>) {
			return ((WeightedEdgeInterface<V>) e).getWeight();
		}
		return 1.0;
	}

}
