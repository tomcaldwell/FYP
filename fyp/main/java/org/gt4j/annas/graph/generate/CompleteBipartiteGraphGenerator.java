package org.gt4j.annas.graph.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;


/**
 * Generates a Complete Bipartite Graph @see <a
 * href="http://mathworld.wolfram.com/CompleteBipartiteGraph.html"> shown
 * here</a>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class CompleteBipartiteGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of vertices in one partition
	 */
	private int a;

	/**
	 * Number of vertices in the other partition
	 */
	private int b;

	/**
	 * Constructs a complete graph generator
	 * 
	 * @param order
	 *            Order of the graph
	 */
	public CompleteBipartiteGraphGenerator(int a, int b) {
		super();
		if (a < 0 || b < 0) {
			throw new IllegalArgumentException("paramters must be >= 0");
		}
		this.a = a;
		this.b = b;
	}

	@Override
	public void generateGraph(GraphInterface<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {
		if (target == null)
			return;

		List<V> x = new ArrayList<V>(a);
		for (int i = 0; i < this.a; i++) {
			V newVertex = vertexFactory.createVertex();
			target.addVertex(newVertex);
			x.add(newVertex);
		}

		List<V> y = new ArrayList<V>(b);
		for (int i = 0; i < this.b; i++) {
			V newVertex = vertexFactory.createVertex();
			target.addVertex(newVertex);
			y.add(newVertex);
		}

		for (V v : x) {
			for (V u : y) {
				target.addEdge(v, u);
			}
		}

	}
}
