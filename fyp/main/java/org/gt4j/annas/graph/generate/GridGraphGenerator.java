package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Generates a grid graph
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class GridGraphGenerator<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, E> {

	/**
	 * Number of columns
	 */
	private int col;

	/**
	 * Number of rows
	 */
	private int row;

	/**
	 * Constructs a grid graph generator
	 * 
	 * @param c
	 *            Number of columns
	 * @param r
	 *            Number of rows
	 */
	public GridGraphGenerator(int c, int r) {
		super();
		if (c < 0 && r < 0) {
			throw new IllegalArgumentException(
					"Number of rows and columns must be >= 0");
		}
		this.col = c;
		this.row = r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void generateGraph(GraphInterface<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo) {
		if (target == null) {
			return;
		}

		Object[][] vs = new Object[this.col][this.row];
		V v = null;
		for (int i = 0; i < this.col; i++) {
			for (int j = 0; j < this.row; j++) {
				v = vertexFactory.createVertex();
				target.addVertex(v);
				vs[i][j] = v;
			}

		}

		for (int i = 0; i < vs.length - 1; i++) {
			for (int j = 0; j < vs[0].length; j++) {
				target.addEdge((V) vs[i][j], (V) vs[i + 1][j]);
			}

		}

		for (int i = 0; i < vs.length; i++) {
			for (int j = 0; j < vs[0].length - 1; j++) {
				target.addEdge((V) vs[i][j], (V) vs[i][j + 1]);
			}

		}

	}
}
