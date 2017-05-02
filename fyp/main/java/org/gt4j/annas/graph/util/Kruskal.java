package org.gt4j.annas.graph.util;

import java.util.PriorityQueue;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.WeightedEdgeInterface;
import org.gt4j.annas.util.DisjointSet;

/**
 * An implementation of Kruskal's algorithm for minimum weight spanning tree
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Kruskal<V, E extends EdgeInterface<V>> extends AbstractMinimumSpanningTree<V, E> {

	private final GraphInterface<V, E> graph;

	private PriorityQueue<E> EdgeList;

	/**
	 * Constructs Kruskal's algorithm object with the given graph
	 * 
	 * @param g
	 *            Graph to perform Kruskal's algorithm on
	 */
	public Kruskal(final GraphInterface<V, E> g) {
		this.graph = g;
		this.EdgeList = new PriorityQueue<E>(this.graph.getSize(),
				new DoubleFieldComparator());
	}

	@Override
	public GraphInterface<V, E> call() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		GraphInterface<V, E> tree = new UndirectedGraph(
				this.graph.getEdgeClass());
		DisjointSet<V> ds = new DisjointSet<>(this.graph.getVertices());

		this.EdgeList.addAll(this.graph.getEdges());
		System.out.println(EdgeList);

		for (E edge : this.EdgeList) {
			if (!ds.findSet(edge.getHead()).equals(ds.findSet(edge.getTail()))) {
				System.out.println("G has no cycle");
				System.out.println(ds.findSet(edge.getHead()));
				System.out.println(ds.findSet(edge.getTail()));
				tree.addVertex(edge.getHead());
				tree.addVertex(edge.getTail());
				EdgeInterface<V> e = tree.addEdge(edge.getTail(),
						edge.getHead());
				if (edge instanceof WeightedEdgeInterface) {
					((WeightedEdgeInterface<V>) e)
							.setWeight(((WeightedEdgeInterface<V>) edge)
									.getWeight());
				}
				ds.union(edge.getHead(), edge.getTail());
			}
			else {
				System.out.println("G has a cycle");
			}
		}
		return tree;
	}
}
