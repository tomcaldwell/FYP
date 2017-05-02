package org.gt4j.annas.graph.util.traverse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Implementation of the breadth first traversal of a graph
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public class BreadthFirst<V> implements Iterable<V> {

	private GraphInterface<V, ?> graph;

	private V startVertex;

	/**
	 * Constructor given the graph
	 * 
	 * @param graph graph to traverse
	 */
	public BreadthFirst(GraphInterface<V, ?> graph) {
		super();
		this.graph = graph;
	}

	/**
	 * Constructor given the graph and a starting vertex
	 * 
	 * @param graph
	 *            graph to traverse
	 * @param startV
	 *            start vertex
	 */
	public BreadthFirst(GraphInterface<V, ?> graph, V startV) {
		super();
		this.graph = graph;
		this.startVertex = startV;
	}

	/**
	 * Gets the start vertex
	 * 
	 * @return start vertex
	 */
	public V getStartVertex() {
		return this.startVertex;
	}

	/**
	 * Sets the start vertex for the traversal
	 * 
	 * @param startVertex
	 *            start vertex
	 */
	public void setStartVertex(V startVertex) {
		this.startVertex = startVertex;
	}

	@Override
	public Iterator<V> iterator() {
		if (this.startVertex != null) {
			return new BreadthFirstIterator(this.graph, this.startVertex);
		} else {

			return new BreadthFirstIterator(this.graph, this.graph
					.getVertices().iterator().next());
		}
	}

	private class BreadthFirstIterator implements Iterator<V> {

		private Deque<V> stack;

		private GraphInterface<V, ?> graph;

		private Collection<V> visited;

		public BreadthFirstIterator(GraphInterface<V, ?> graph, V startVertex) {
			super();
			this.graph = graph;
			this.stack = new ArrayDeque<V>();
			this.visited = new ArrayList<V>();
			if (this.graph.containsVertex(startVertex)) {
				this.stack.add(startVertex);
			} else {
				throw new IllegalArgumentException(startVertex
						+ " not in graph");
			}
		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		@Override
		public V next() {
			if (!this.stack.isEmpty()) {
				V retval = this.stack.pop();
				for (V u : Utilities.getOpenNeighbourhood(this.graph, retval)) {
					if (!this.visited.contains(u) && !this.stack.contains(u)) {
						this.stack.add(u);
					}
				}
				this.visited.add(retval);
				return retval;
			} else {
				throw new NoSuchElementException("No more elements");
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

}
