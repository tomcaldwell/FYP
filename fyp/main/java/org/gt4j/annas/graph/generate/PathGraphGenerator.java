package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates a path Graph, a graph which is two regular with the exception of
 * two vertices of degree 1.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class PathGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Start vertex
	 */
	private V start_vertex;

	/**
	 * End vertex
	 */
	private V end_vertex;

	/**
	 * Number of vertices in the target graph
	 */
	private int length;

	/**
	 * Constructs a linear graph generator
	 * 
	 * @param length
	 *            Length of the path
	 */
	public PathGraphGenerator(int length) {
		super();
		if (length < 0) {
			throw new IllegalArgumentException("order must be >= 0");
		}
		this.length = length;
	}

	/**
	 * @return the start_vertex
	 */
	public V getStart_vertex() {
		return this.start_vertex;
	}

	/**
	 * @return the end_vertex
	 */
	public V getEnd_vertex() {
		return this.end_vertex;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return this.length;
	}

	@Override
	public void generateGraph(GraphInterface<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {

		if (target == null)
			return;
		V newvertex = vertexFactory.createVertex();
		V tmp;

		this.start_vertex = newvertex;
		target.addVertex(newvertex);
		tmp = newvertex;
		for (int i = 0; i < this.length - 1; i++) {
			newvertex = vertexFactory.createVertex();
			target.addVertex(newvertex);
			target.addEdge(tmp, newvertex);
			tmp = newvertex;
		}
		this.end_vertex = tmp;

	}

}
