package org.gt4j.annas.graph;

/**
 * A simple directed graph is a directed graph without multiple edge or loops.
 * 
 * <br><br>
 * The easiest was to construct a SimpleDirectedGraph is <br>
 * <br>
 * 
 * <pre>
 * GraphInterface&lt;Integer, IntegerEdge&gt; graph = new SimpleDirectedGraph(
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
public class SimpleDirectedGraph<V, E extends EdgeInterface<V>> extends
		DirectedGraph<V, E> {

	/**
	 * Constructs a {@link org.gt4j.annas.graph.SimpleDirectedGraph} with a given class
	 * that implements {@link org.gt4j.annas.graph.EdgeInterface}
	 * 
	 * @param edgeClass
	 *            Edge class
	 */
	public SimpleDirectedGraph(Class<? extends E> edgeClass) {
		super(edgeClass);
		this.allowloops = false;
		this.allowparallelEdges = false;
	}

	/**
	 * Constructs a {@link org.gt4j.annas.graph.SimpleDirectedGraph} with a given
	 * {@link org.gt4j.annas.graph.EdgeFactory}
	 * 
	 * @param edgeFactory
	 *            Edge factory
	 */
	public SimpleDirectedGraph(EdgeFactory<V, E> edgeFactory) {
		super(edgeFactory);
		this.allowloops = false;
		this.allowparallelEdges = false;
	}

}
