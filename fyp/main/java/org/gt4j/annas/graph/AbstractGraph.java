package org.gt4j.annas.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.gt4j.annas.exception.VertexNotInGraphException;
import org.gt4j.annas.util.ArraySet;
import org.gt4j.annas.util.EqualityChecker;

/**
 * Base class for all graphs using the default implementation.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public abstract class AbstractGraph<V, E extends EdgeInterface<V>> implements
		GraphInterface<V, E> {

	/**
	 * Graph Observer
	 */
	protected GraphObserver<V, E> go;

	/**
	 * Factory to construct edges
	 */
	protected EdgeFactory<V, E> edgeFactory;

	/**
	 * Vertex map
	 */
	protected Map<V, MultiHashMap<V, E>> vertexMap;

	/**
	 * Edge equality checker
	 */
	protected EqualityChecker<E> checker;

	/**
	 * Are loops allowed? (An edge with identical end points).
	 */
	protected boolean allowloops;

	/**
	 * Are parallel edges allowed?
	 */
	protected boolean allowparallelEdges;

	/**
	 * Is the graph directed?
	 */
	protected boolean directed;

	/**
	 * Constructs an AbstractGraph with the given edge factory.
	 * 
	 * @param edgeFactory
	 *            EdgeFactory to use
	 * @param directed
	 *            true if the graph is directed
	 */
	protected AbstractGraph(EdgeFactory<V, E> edgeFactory, boolean directed) {
		super();
		if (edgeFactory == null) {
			throw new NullPointerException("edge factory can not be null");
		}

		this.edgeFactory = edgeFactory;
		this.vertexMap = new Hashtable<V, MultiHashMap<V, E>>();
		this.allowloops = true;
		this.allowparallelEdges = true;
		this.directed = directed;
	}

	/**
	 * Constructs an AbstractGraph with the given edge factory.
	 * 
	 * @param edgeClass
	 *            Edge class
	 * @param directed
	 *            true if the graph is directed
	 */
	protected AbstractGraph(Class<? extends E> edgeClass, boolean directed) {
		this(new ClassEdgeFactory<V, E>(edgeClass), directed);
	}

	@Override
	public boolean addVertex(V vertex) {
		if (!this.containsVertex(vertex)) {
			return this.vertexMap.put(vertex, new MultiHashMap<V, E>()) == null;
		}
		return false;
	}

	@Override
	public boolean addVertices(Collection<? extends V> vertices) {
		if (vertices == null) {
			return false;
		}
		boolean successful = true;
		for (V vertex : vertices) {
			successful &= this.addVertex(vertex);
		}
		return successful;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean addVertices(V... vs) {
		boolean successful = true;
		for (V vertex : vs) {
			successful &= this.addVertex(vertex);
		}
		return successful;
	}

	@Override
	public boolean removeVertices(Collection<? extends V> vertices) {
		if (vertices == null) {
			return false;
		}
		boolean successful = true;
		for (V vertex : vertices) {
			successful &= this.removeVertex(vertex);
		}
		return successful;
	}

	@Override
	public boolean removeEdges(Collection<? extends E> edges) {
		if (edges == null) {
			return false;
		}
		boolean successful = true;
		for (E edge : edges) {
			successful &= this.removeEdge(edge);
		}
		return successful;
	}

	@Override
	public Set<V> getVertices() {
		return Collections.unmodifiableSet(this.vertexMap.keySet());
	}

	@Override
	public boolean resetEdges() {
		return this.removeEdges(this.getEdges());
	}

	@Override
	public boolean containsEdge(E edge) {
		return this.getEdges().contains(edge);
	}

	@Override
	public boolean containsEdge(V tail, V head) {
		return !this.getEdges(tail, head).isEmpty();
	}

	@Override
	public boolean containsVertex(V vertex) {
		return this.getVertices().contains(vertex);
	}

	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		return this.edgeFactory;
	}

	@Override
	public int getSize() {
		return this.getEdges().size();
	}

	@Override
	public int getOrder() {
		return this.getVertices().size();
	}

	@Override
	public GraphObserver<V, E> getObserver() {
		return this.go;
	}

	@Override
	public void setObserver(GraphObserver<V, E> graphObserver) {
		if (graphObserver == null) {
			throw new NullPointerException();
		}
		this.go = graphObserver;
	}

	/**
	 * Decides if the vertex in a member of the vertex set.
	 * 
	 * @param vertex
	 *            Vertex to consider
	 * @return true if the vertex is a member of the vertex set.
	 */
	protected boolean assertVertexExist(V vertex) {
		if (containsVertex(vertex)) {
			return true;
		} else if (vertex == null) {
			throw new NullPointerException();
		} else {
			return false;
		}
	}

	@Override
	public Set<E> getEdges(V tail) {
		Set<E> edges = new ArraySet<E>(this.checker);
		Collection<E> es = new ArrayList<E>();

		try {
			Collection<E> h = this.vertexMap.get(tail).values();
			es.addAll(h);

			for (E edge : es) {
				edges.add(edge);
			}
			return Collections.unmodifiableSet(edges);
		} catch (NullPointerException e) {
			return Collections.unmodifiableSet(edges);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<E> getEdges(V tail, V head) {
		Set<E> edges = new ArraySet<E>(this.checker);
		Collection<E> es = new ArrayList<E>();

		Map<V, V> vmap = this.vertexMap.get(tail);
		Collection<E> h = null;
		if (vmap != null) {
			h = this.vertexMap.get(tail).getCollection(head);
		}
		if (h == null) {
			return Collections.unmodifiableSet(edges);
		}
		es.addAll(h);

		for (E edge : es) {
			edges.add(edge);
		}
		return Collections.unmodifiableSet(edges);
	}
	
	@Override
	public void forEachVertex(Consumer<V> consumer){
		this.getVertices().forEach(consumer);
	}
	
	@Override
	public void forEachEdge(Consumer<E> consumer){
		this.getEdges().forEach(consumer);
	}

	@Override
	public Set<E> getEdges() {
		Set<E> edges = new ArraySet<E>(
				new UndirectedEdgeEqualityChecker<V, E>());
		Collection<E> es = new ArrayList<E>();

		for (V vertex : this.vertexMap.keySet()) {

			es.addAll(this.vertexMap.get(vertex).values());

		}

		for (E edge : es) {
			edges.add(edge);
		}
		return Collections.unmodifiableSet(edges);
	}

	@Override
	public int getDegree(V vertex) {
		if (vertex == null || !this.containsVertex(vertex)) {
			throw new VertexNotInGraphException(vertex
					+ " is not a vertex of this graph");
		} else {
			return this.getEdges(vertex).size();
		}
	}

	/**
	 * Does the graph allow loops?
	 * 
	 * @return true if the graph allows loops
	 */
	public boolean allowLoops() {
		return this.allowloops;
	}

	/**
	 * Does the graph allow multiple edges
	 * 
	 * @return true if the graph allows multiple edges
	 */
	public boolean allowMultipleEdges() {
		return this.allowparallelEdges;
	}

	@Override
	public boolean isDirected() {
		return this.directed;
	}

	@Override
	public Class<?> getEdgeClass() {
		return this.edgeFactory.getEdgeClass();
	}

	@Override
	public String toString() {
		String ret = "Graph on ";
		switch (this.getOrder()) {
		case 0:
			return "Null graph";
		case 1:
			ret += "1 vertex ";
			break;

		default:
			ret += this.getOrder() + " vertices ";
			break;
		}

		ret += "with ";
		switch (this.getSize()) {
		case 0:
			ret += "no edges";
			break;
		case 1:
			ret += "1 edge";
			break;

		default:
			ret += this.getSize() + " edges";
			break;
		}

		ret += ".";

		return ret;
	}
}
