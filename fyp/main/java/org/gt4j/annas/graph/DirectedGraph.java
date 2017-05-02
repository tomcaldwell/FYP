package org.gt4j.annas.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.gt4j.annas.exception.VertexNotInGraphException;
import org.gt4j.annas.util.ArraySet;

/**
 * Representation of a directed graph. For a simple directed graphs see
 * {@link org.gt4j.annas.graph.SimpleDirectedGraph} <br>
 * <br>
 * 
 * The easiest was to construct a DirectedGraph is <br>
 * <br>
 * 
 * <pre>
 * GraphInterface&lt;Integer, IntegerEdge&gt; graph = new DirectedGraph(
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
public class DirectedGraph<V, E extends EdgeInterface<V>> extends
		AbstractGraph<V, E> implements DirectedGraphInterface<V, E> {

	/**
	 * Constructs a {@link org.gt4j.annas.graph.DirectedGraph} with a given
	 * {@link org.gt4j.annas.graph.EdgeFactory}
	 * 
	 * @param edgeFactory
	 *            Edge factory
	 */
	public DirectedGraph(EdgeFactory<V, E> edgeFactory) {
		super(edgeFactory, true);
		this.checker = new DirectedEdgeEqualityChecker<V, E>();
	}

	/**
	 * Constructs a {@link org.gt4j.annas.graph.DirectedGraph} with a given class that
	 * implements {@link org.gt4j.annas.graph.EdgeInterface}
	 * 
	 * @param edgeClass
	 *            Edge class
	 */
	public DirectedGraph(Class<? extends E> edgeClass) {
		super(edgeClass, true);
		this.checker = new DirectedEdgeEqualityChecker<V, E>();
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
		if (added) {
			return edge;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeEdge(E edge) {
		return this.vertexMap.get(edge.getTail()).remove(edge.getHead(), edge);
		// && this.vertexMap.get(edge.getHead()).remove(edge.getTail(),edge);

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
		ArrayList<V> m = new ArrayList<V>(this.vertexMap.keySet());
		for (V mhm : m) {
			this.vertexMap.get(mhm).remove(vertex);
		}
		return this.vertexMap.remove(vertex) != null;
	}

	/**
	 * Gets the number of edges entering a vertex
	 * 
	 * @param vertex
	 *            Vertex to consider
	 * @return number of edges entering a vertex
	 */
	@Override
	public Set<E> getInEdges(V vertex) {
		Set<E> n = this.getEdges();
		Set<E> m = new ArraySet<E>(new UndirectedEdgeEqualityChecker<V, E>());

		for (E e : n) {
			if (e.getHead().equals(vertex)) {
				m.add(e);
			}
		}

		return Collections.unmodifiableSet(m);
	}

	/**
	 * Gets the number of edges leaving a vertex
	 * 
	 * @param vertex
	 *            vertex to consider
	 * @return number of edges leaving a vertex
	 */
	@Override
	public Set<E> getOutEdges(V vertex) {
		if (vertex == null || !this.containsVertex(vertex)) {
			throw new VertexNotInGraphException(vertex
					+ " is not a vertex of this graph");
		} else {
			return this.getEdges(vertex);
		}
	}

	@Override
	public int getInDegree(V vertex) {
		if (vertex == null || !this.containsVertex(vertex)) {
			throw new VertexNotInGraphException(vertex
					+ " is not a vertex of this graph");
		} else {
			return this.getInEdges(vertex).size();
		}

	}

	@Override
	public int getOutDegree(V vertex) {
		return this.getOutEdges(vertex).size();
	}
}
