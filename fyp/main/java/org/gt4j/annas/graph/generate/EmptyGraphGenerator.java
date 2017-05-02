package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates an empty Graph @see <a
 * href="http://mathworld.wolfram.com/EmptyGraph.html"> shown here</a>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class EmptyGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of nodes in the target graph
	 */
	private int order;

	/**
	 * Constructs an empty graph generator
	 * 
	 * @param order
	 *            Order of the empty graph
	 */
	public EmptyGraphGenerator(int order) {
		super();
		if (order < 0) {
			throw new IllegalArgumentException("order must be >= 0");
		}
		this.order = order;
	}

	@Override
	public void generateGraph(GraphInterface<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {
		if (target == null)
			return;

		for (int i = 0; i < this.order; i++)
			target.addVertex(vertexFactory.createVertex());

	}

}
