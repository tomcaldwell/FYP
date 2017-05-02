package org.gt4j.annas.graph.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.math.combinatorics.PowersetIterator;

/**
 * 
 * @author Sam Wilson
 * 
 */
public class InducedSubgraph {

	/**
	 * Returns the induced subgraph by given vertices. Reuses the vertex
	 * objects, but makes new edges.
	 * 
	 * @param graph
	 *            input graph
	 * @param vertices
	 *            vertices
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @return induced graph graph[vertices]
	 */
	public static <V, E extends EdgeInterface<V>> GraphInterface<V, E> inducedSubgraphOf(GraphInterface<V, E> graph,
			Collection<V> vertices) {

		if (!graph.getVertices().containsAll(vertices)) {
			return null;
		}

		GraphInterface<V, E> h = new UndirectedGraph<V, E>(graph.getEdgeFactory());

		for (V n : vertices) {
			h.addVertex(n);
		}

		for (E e : graph.getEdges()) {
			V s = e.getTail();
			V t = e.getHead();
			if (h.containsVertex(s) && h.containsVertex(t)) {
				h.addEdge(s, t);
			}
		}

		return h;
	}

	/**
	 * Provides an iterator for induced subgraphs.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param poss
	 *            Set of vertices to include/exclude from the graph
	 * @return an iterator of graphs which contains the vertices (V \ poss)
	 *         union u where u \in powerset(poss).
	 */
	public static <V, E extends EdgeInterface<V>> Iterator<GraphInterface<V, E>> inducedSubgraphIterator(
			final GraphInterface<V, E> graph, final List<V> poss) {
		return new Iterator<GraphInterface<V, E>>() {

			PowersetIterator<V> subsets = new PowersetIterator<V>(poss);

			@Override
			public boolean hasNext() {
				return this.subsets.hasNext();
			}

			@Override
			public GraphInterface<V, E> next() {
				Collection<V> vertices = this.subsets.next();
				Set<V> n = graph.getVertices();
				n.removeAll(poss);
				n.addAll(vertices);
				return inducedSubgraphOf(graph, n);
			}

			@Override
			public void remove() {
			}
		};

	}

	/**
	 * Induced subgraph iterator. Iterates over all induced subgraphs of the
	 * given graph.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return an iterator over all induced subgraphs of a given graph
	 */
	public static <V, E extends EdgeInterface<V>> Iterator<GraphInterface<V, E>> inducedSubgraphIterator(
			final GraphInterface<V, E> graph) {
		return new Iterator<GraphInterface<V, E>>() {
			PowersetIterator<V> subsets = new PowersetIterator<V>(graph.getVertices());

			@Override
			public boolean hasNext() {
				return this.subsets.hasNext();
			}

			@Override
			public GraphInterface<V, E> next() {
				Collection<V> vertices = this.subsets.next();
				return inducedSubgraphOf(graph, vertices);
			}

			@Override
			public void remove() {
			}
		};
	}

}
