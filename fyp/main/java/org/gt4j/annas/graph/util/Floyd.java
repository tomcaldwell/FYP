package org.gt4j.annas.graph.util;

import java.util.ArrayList;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.WeightedEdgeInterface;
import org.gt4j.annas.misc.Path;

/**
 * Determines all pair shortest paths, as described <a
 * href="http://mathworld.wolfram.com/Floyd-WarshallAlgorithm.html"> here</a>
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Floyd<V, E extends EdgeInterface<V>>  {

	private static final double DEFAULT_EDGE_WEIGHT = 1.0d;

	/**
	 * Graph to perform the algorithm on.
	 */
	private final GraphInterface<V, E> graph;

	/**
	 * Integer array containing the route from source to destination.
	 */
	private int[][] R;

	/**
	 * Double array containing the distance from source to destination.
	 */
	private double[][] D;

	/**
	 * Default constructor
	 * 
	 * @param g
	 *            Graph o perform algorithm on.
	 */
	public Floyd(final GraphInterface<V, E> g) {
		this.graph = g;
		makeMatrix();
		run();

	}

	/**
	 * The route matrix for the current graph.
	 * 
	 * @return The route matrix.
	 */
	public int[][] getR() {
		return this.R;
	}

	/**
	 * The distance matrix for the current graph.
	 * 
	 * @return distance matrix
	 */
	public double[][] getD() {
		return this.D;
	}

	/**
	 * Finds the distance between the two nodes, by looking in the distance
	 * matrix.
	 * 
	 * @param a
	 *            Source
	 * @param b
	 *            Destination
	 * @return Distance between the nodes.
	 */
	public double getDistance(V a, V b) {
		return this.D[indexOf(a)][indexOf(b)];
	}

	/**
	 * Find the route through the Graph from the source node to the destination
	 * node, by using the route matrix.
	 * 
	 * @param a
	 *            Source
	 * @param b
	 *            Destination
	 * @return Graph of the route between the two nodes.
	 */
	public Path<V, E> getRoute(V a, V b) {
		Path<V, E> m = new Path<V, E>();
		V t = a;
		int col = indexOf(b);
		int row = indexOf(a);
		int temp = row;

		ArrayList<V> fg = new ArrayList<V>(this.graph.getVertices());

		while (temp != col) {
			row = this.R[col][row] - 1;

			m.addEdge(this.graph.getEdges(t, fg.get(row)).iterator().next());
			t = fg.get(row);
			temp = row;
		}

		return m;
	}

	/**
	 * Runs the algorithm that finds the shortest path between all pairs.
	 */
	private void run() {
		int loc;
		for (int it = 1; it <= this.D.length; it++) {
			loc = it - 1;

			for (int x = 0; x < this.D.length; x++) {
				for (int y = 0; y < this.D[0].length; y++) {
					if ((this.D[loc][y] + this.D[x][loc] < this.D[x][y])
							&& (x != loc || y != loc)) {
						this.D[x][y] = this.D[it - 1][y] + this.D[x][loc];
						this.R[x][y] = this.R[loc][y];

					}
				}
			}

		}
	}

	private int indexOf(V vertex) {
		int index = 0;
		for (V v : this.graph.getVertices()) {
			if (v == vertex) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * Sets up the matrices, and converts from the adjacent list format into
	 * distance matrix.
	 */
	private void makeMatrix() {
		int SIZE = this.graph.getOrder();
		this.D = new double[SIZE][SIZE];
		this.R = new int[SIZE][SIZE];

		for (int i = 0; i < this.R.length; i++) {
			for (int j = 0; j < this.R[0].length; j++) {
				this.R[i][j] = i + 1;
			}
		}

		for (int i = 0; i < this.D.length; i++) {
			for (int j = 0; j < this.D[0].length; j++) {
				this.D[i][j] = Double.MAX_VALUE;
			}
		}

		for (V current : this.graph.getVertices()) {
			int x = indexOf(current);

			for (E edge : this.graph.getEdges(current)) {

				this.D[x][indexOf(edge.getOtherEndpoint(current))] = getWeight(edge);

			}

		}

	}

	private double getWeight(E edge) {
		if (edge instanceof WeightedEdgeInterface<?>) {
			return ((WeightedEdgeInterface<?>) edge).getWeight();
		}

		return DEFAULT_EDGE_WEIGHT;
	}

}
