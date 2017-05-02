package org.gt4j.annas.graph.generate;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.SimpleDirectedGraph;
import org.gt4j.annas.graph.SimpleUndirectedGraph;
import org.gt4j.annas.graph.VertexFactory;

/**
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class RandomGraph<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	private int order;

	private int size;

	/**
	 * Constructs a random graph generator
	 * 
	 * @param order
	 *            Order of the graphs
	 * @param size
	 *            Number of edges
	 */
	public RandomGraph(int order, int size) {
		super();
		if (order < 0) {
			throw new IllegalArgumentException("order must be >= 0");
		}
		if (size < 0) {
			throw new IllegalArgumentException("size must be >= 0");
		}
		this.order = order;
		this.size = size;
	}

	@Override
	public void generateGraph(GraphInterface<V, E> graph,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {
		int maxSize = Integer.MAX_VALUE;
		if (graph instanceof SimpleDirectedGraph) {
			maxSize = this.order * this.order - 1;
		} else if (graph instanceof SimpleUndirectedGraph) {
			maxSize = (this.order * (this.order - 1)) / 2;
		}

		if (this.size <= maxSize) {
			for (int i = 0; i < this.order; i++) {
				graph.addVertex(vertexFactory.createVertex());
			}
			while (graph.getSize() != this.size) {
				V head = randomSelect(graph.getVertices());
				V tail = randomSelect(graph.getVertices());
				graph.addEdge(tail, head);
			}
		} else {
			throw new IllegalArgumentException(
					"the graph has too few vertices to have the specified number of edges");
		}
	}

	@SuppressWarnings("unchecked")
	private V randomSelect(Collection<V> col) {
		Random r = new Random();
		return ((V[]) col.toArray())[r.nextInt(col.size())];
	}

}
