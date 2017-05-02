package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates a Star Graph @see <a
 * href="http://mathworld.wolfram.com/StarGraph.html"> shown here</a>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class StarGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of vertices in the target graph
	 */
	private int order;

	/**
	 * Constructs a star graph generators
	 * 
	 * @param order
	 *            Order of the graph
	 */
	public StarGraphGenerator(int order) {
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

		V Hub = vertexFactory.createVertex();
		V tmp = null;
		target.addVertex(Hub);
		for (int i = 0; i < this.order - 1; i++) {
			tmp = vertexFactory.createVertex();
			target.addVertex(tmp);
			target.addEdge(Hub, tmp);
		}
	}

}
