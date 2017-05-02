package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates a Wheel Graph @see <a
 * href="http://mathworld.wolfram.com/WheelGraph.html"> shown here</a>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class WheelGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of nodes in the target graph
	 */
	private int order;

	/**
	 * Constructs a wheel graph generator
	 * 
	 * @param order
	 *            Order of the graph
	 */
	public WheelGraphGenerator(int order) {
		super();
		if (order < 4) {
			throw new IllegalArgumentException("order must be >= 3");
		}
		this.order = order;
	}

	@Override
	public void generateGraph(GraphInterface<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {
		if (target == null)
			return;

		V Hub = vertexFactory.createVertex();
		V newnode = vertexFactory.createVertex();
		V tmp;
		V start_node = newnode;
		target.addVertex(newnode);
		tmp = newnode;
		for (int i = 0; i < this.order - 2; i++) {
			newnode = vertexFactory.createVertex();
			target.addVertex(newnode);
			target.addEdge(tmp, newnode);
			tmp = newnode;
		}
		V end_node = tmp;
		target.addEdge(end_node, start_node);

		target.addVertex(Hub);

		for (V tmp1 : target.getVertices()) {
			if (tmp1 != Hub) {
				target.addEdge(Hub, tmp1);
			}
		}

	}

}
