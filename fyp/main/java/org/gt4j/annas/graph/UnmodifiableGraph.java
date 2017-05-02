package org.gt4j.annas.graph;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import org.gt4j.annas.exception.UnmodifiableGraphException;

/**
 * Wraps a graph in order to make it unmodifiable. <br>
 * <br>
 * The easiest was to construct an UnmodifiableGraph is <br>
 * <br>
 * 
 * <pre>
 *
 * GraphInterface&lt;Integer, IntegerEdge&gt; graph =  //some graph
 * <br>
 * GraphInterface&lt;Integer, IntegerEdge&gt; g = new UnmodifiableGraph(graph);
 * </pre>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class UnmodifiableGraph<V, E extends EdgeInterface<V>> implements
		GraphInterface<V, E> {

	private GraphInterface<V, E> graph;

	private static final String err_msg = "Method not supported on Unmodifiable graph";

	/**
	 * Constructs a graph from the given graph that is unmodifiable
	 * 
	 * @param graph
	 *            graph to make an unmodifiable graph from
	 */
	public UnmodifiableGraph(GraphInterface<V, E> graph) {
		super();
		this.graph = graph;
	}

	@Override
	public E addEdge(V tail, V head) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean addVertex(V vertex) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addVertices(V... vs) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean addVertices(Collection<? extends V> vertices) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean containsEdge(V head, V tail) {
		return this.graph.containsEdge(head, tail);
	}

	@Override
	public boolean containsEdge(E edge) {
		return this.graph.containsEdge(edge);
	}

	@Override
	public boolean containsVertex(V vertex) {
		return this.graph.containsVertex(vertex);
	}

	@Override
	public int getDegree(V vertex) {
		return this.graph.getDegree(vertex);
	}

	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		return this.graph.getEdgeFactory();
	}

	@Override
	public Set<E> getEdges(V tail) {
		return this.graph.getEdges(tail);
	}

	@Override
	public Set<E> getEdges(V tail, V head) {
		return this.graph.getEdges(tail, head);
	}

	@Override
	public Set<E> getEdges() {
		return this.graph.getEdges();
	}

	@Override
	public GraphObserver<V, E> getObserver() {
		return this.graph.getObserver();
	}

	@Override
	public int getOrder() {
		return this.graph.getOrder();
	}

	@Override
	public int getSize() {
		return this.graph.getSize();
	}

	@Override
	public Set<V> getVertices() {
		return this.graph.getVertices();
	}

	@Override
	public void forEachVertex(Consumer<V> comsumer) {
		this.graph.forEachVertex(comsumer);
	}

	@Override
	public void forEachEdge(Consumer<E> consumer) {
		this.graph.forEachEdge(consumer);
	}

	@Override
	public boolean removeEdge(E edge) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean removeEdge(V tail, V head) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean removeEdge(V tail) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean removeEdges(Collection<? extends E> edges) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean removeVertex(V vertex) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean removeVertices(Collection<? extends V> vertices) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public boolean resetEdges() {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public void setObserver(GraphObserver<V, E> GO) {
		throw new UnmodifiableGraphException(err_msg);
	}

	@Override
	public Class<?> getEdgeClass() {
		return this.graph.getEdgeClass();
	}

	@Override
	public boolean isDirected() {
		return this.graph.isDirected();
	}

}
