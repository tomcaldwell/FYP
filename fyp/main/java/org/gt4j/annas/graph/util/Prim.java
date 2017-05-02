package org.gt4j.annas.graph.util;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.WeightedEdgeInterface;

/**
 * An implementation of Prim's algorithm for minimum spanning tree
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Prim<V, E extends EdgeInterface<V>> extends AbstractMinimumSpanningTree<V, E>{

	private static final double DEFAULT_EDGE_WEIGHT = 1d;

	private final GraphInterface<V, E> graph;

	private PriorityQueue<E> EdgeList;

	/**
	 * Constructs Prim's algorithm object with the given graph
	 * 
	 * @param graph
	 *            Graph to perform Prims's algorithm on
	 */
	public Prim(final GraphInterface<V, E> graph) {
		this.graph = graph;
		this.EdgeList = new PriorityQueue<E>(graph.getSize(),
				new DoubleFieldComparator());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GraphInterface<V, E> call() {
		GraphInterface<V, E> tree = new UndirectedGraph(
				this.graph.getEdgeClass());

		V vertex = this.graph.getVertices().iterator().next();
		tree.addVertex(vertex);
		this.EdgeList.addAll(this.graph.getEdges(vertex));

		while (tree.getOrder() != this.graph.getOrder()
				&& !this.EdgeList.isEmpty()) {
			E edge = this.EdgeList.poll();
			V v = this.getOtherEnd(tree, edge);

			if (!tree.containsVertex(v)) {

				tree.addVertex(v);
				EdgeInterface<V> e = tree.addEdge(edge.getTail(),
						edge.getHead());
				if (edge instanceof WeightedEdgeInterface) {
					((WeightedEdgeInterface<V>) e)
							.setWeight(((WeightedEdgeInterface<V>) edge)
									.getWeight());
				}
				this.EdgeList.remove(edge);
				this.EdgeList.addAll(this.graph.getEdges(v));
			}
		}
		return tree;
	}

	private V getOtherEnd(GraphInterface<V, ?> g, E edge) {
		if (g.containsVertex(edge.getHead())) {
			return edge.getTail();
		} else {
			return edge.getHead();
		}
	}

	
	public double getWeight() {
		GraphInterface<?, E> graph = this.call();
		double d = 0;
		for (E edge : graph.getEdges()) {
			if (edge instanceof WeightedEdgeInterface<?>) {
				d += ((WeightedEdgeInterface<?>) edge).getWeight();
			} else {
				d += 1;
			}
		}

		return d;
	}

	class DoubleFieldComparator implements Comparator<E> {

		@Override
		public int compare(E arg0, E arg1) {
			Double d = DEFAULT_EDGE_WEIGHT;
			Double e = DEFAULT_EDGE_WEIGHT;
			if (arg0 instanceof WeightedEdgeInterface<?>) {
				d = ((WeightedEdgeInterface<?>) arg0).getWeight();
			}

			if (arg1 instanceof WeightedEdgeInterface<?>) {
				e = ((WeightedEdgeInterface<?>) arg1).getWeight();
			}
			return d.compareTo(e);
		}
	}
}
