package org.gt4j.annas.graph;

/**
 * A simple undirected graph is an undirected graph without multiple edges or
 * loops.
 * <br><br>
 * The easiest was to construct a SimpleUndirectedGraph is <br>
 * <br>
 * 
 * <pre>
 * GraphInterface&lt;Integer, IntegerEdge&gt; graph = new SimpleUndirectedGraph(
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
public class SimpleUndirectedGraph<V, E extends EdgeInterface<V>> extends
		UndirectedGraph<V, E> {

	/**
	 * Constructs a {@link org.gt4j.annas.graph.SimpleUndirectedGraph} with a given
	 * class that implements {@link org.gt4j.annas.graph.EdgeInterface}
	 * 
	 * @param edgeClass
	 *            Edge class
	 */
	public SimpleUndirectedGraph(Class<E> edgeClass) {
		super(edgeClass);
		this.allowloops = false;
		this.allowparallelEdges = false;
	}

	/**
	 * Constructs an {@link org.gt4j.annas.graph.SimpleUndirectedGraph} with a given
	 * {@link org.gt4j.annas.graph.EdgeFactory}
	 * 
	 * @param edgeFactory
	 *            Edge factory
	 */
	public SimpleUndirectedGraph(EdgeFactory<V, E> edgeFactory) {
		super(edgeFactory);
		this.allowloops = false;
		this.allowparallelEdges = false;
	}

}
