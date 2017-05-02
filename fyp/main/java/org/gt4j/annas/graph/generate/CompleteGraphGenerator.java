package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates a Complete Graph @see <a
 * href="http://mathworld.wolfram.com/CompleteGraph.html"> shown here</a>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class CompleteGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of vertices in the target graph
	 */
	private int order;

	/**
	 * Constructs a complete graph generator
	 * 
	 * @param order
	 *            Order of the graph
	 */
	public CompleteGraphGenerator(int order) {
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

		for (int i = 0; i < this.order; i++) {
			V newVertex = vertexFactory.createVertex();
			target.addVertex(newVertex);
		}

		for (V v : target.getVertices()) {
			for (V u : target.getVertices()) {
				if (v != u)
					target.addEdge(v, u);
			}
		}

	}
}
