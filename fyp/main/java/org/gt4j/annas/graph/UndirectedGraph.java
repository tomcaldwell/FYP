package org.gt4j.annas.graph;

import java.util.Set;

/**
 * 
 * Representation of an undirected graph. For a simple undirected graph see
 * {@link org.gt4j.annas.graph.SimpleUndirectedGraph}. <br>
 * <br>
 * 
 * The easiest was to construct an UndirectedGraph is <br>
 * <br>
 * 
 * <pre>
 * GraphInterface&lt;Integer, IntegerEdge&gt; graph = new UndirectedGraph(
 * 		IntegerEdge.class);
 * </pre>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class UndirectedGraph<V, E extends EdgeInterface<V>> extends
		AbstractGraph<V, E> {

	/**
	 * Constructs an {@link org.gt4j.annas.graph.UndirectedGraph} with a given
	 * {@link org.gt4j.annas.graph.EdgeFactory}
	 * 
	 * @param edgeFactory
	 *            Edge factory
	 */
	public UndirectedGraph(EdgeFactory<V, E> edgeFactory) {
		super(edgeFactory, false);
		this.checker = new UndirectedEdgeEqualityChecker<V, E>();
	}

	/**
	 * Constructs an {@link org.gt4j.annas.graph.UndirectedGraph} with a given class that
	 * implements {@link org.gt4j.annas.graph.EdgeInterface}
	 * 
	 * @param edgeClass
	 *            Edge class
	 */
	public UndirectedGraph(Class<E> edgeClass) {
		super(edgeClass, false);
		this.checker = new UndirectedEdgeEqualityChecker<V, E>();
	}

	@Override
	public E addEdge(V tail, V head) {
		if (!this.allowparallelEdges && this.containsEdge(tail, head)) {
			return null;
		}

		if (!this.allowloops && tail == head) {
			return null;
		}

		E edge = this.edgeFactory.create(tail, head);

		edge.setHead(head);
		edge.setTail(tail);

		boolean added = this.vertexMap.get(tail).put(head, edge) != null;
		added &= this.vertexMap.get(head).put(tail, edge) != null;
		if (added) {
			return edge;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeEdge(E edge) {
		return this.vertexMap.get(edge.getTail()).remove(edge.getHead(), edge)
				&& this.vertexMap.get(edge.getHead()).remove(edge.getTail(),
						edge);

	}

	@Override
	public boolean removeEdge(V tail, V head) {
		Set<E> edges = this.getEdges(tail, head);
		boolean successful = true;
		for (E e : edges) {
			successful &= this.removeEdge(e);
		}
		return successful;
	}

	@Override
	public boolean removeEdge(V tail) {
		Set<E> edges = this.getEdges(tail);
		boolean successful = true;
		for (E e : edges) {
			successful &= this.removeEdge(e);
		}
		return successful;
	}

	@Override
	public boolean removeVertex(V vertex) {
		this.removeEdge(vertex);
		return this.vertexMap.remove(vertex) != null;
	}
}
