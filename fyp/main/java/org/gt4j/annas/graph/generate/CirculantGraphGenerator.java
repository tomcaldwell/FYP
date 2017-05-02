package org.gt4j.annas.graph.generate;

import java.util.ArrayList;
import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates a graph with n vertices which are cyclically order where each
 * vertex is adjacent to a set of vertices specified as parameters.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class CirculantGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of vertices in the target graph
	 */
	private int order;

	private int[] adjacency;

	/**
	 * Constructs a circulant graph generator
	 * 
	 * @param order
	 *            Order of the graph
	 * @param adjacency
	 *            adjacency modulo order
	 */
	public CirculantGraphGenerator(int order, int... adjacency) {
		super();
		if (order < 0) {
			throw new IllegalArgumentException("order must be >= 0");
		}

		for (int i = 0; i < adjacency.length; i++) {
			if (adjacency[i] >= order) {
				throw new IllegalArgumentException("vertex out of bounds");
			}
		}
		this.order = order;
		this.adjacency = adjacency;
	}

	@Override
	public void generateGraph(GraphInterface<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {
		if (target == null)
			return;

		ArrayList<V> vertices = new ArrayList<V>(this.order);
		for (int i = 0; i < this.order; i++) {
			vertices.add(vertexFactory.createVertex());
			target.addVertex(vertices.get(i));
		}

		for (int i = 0; i < this.order; i++) {
			for (int j = 0; j < this.adjacency.length; j++) {
				int adj = ((this.adjacency[j] + i) % (this.order));
				target.addEdge(vertices.get(i), vertices.get(adj));
			}
		}
	}

}
