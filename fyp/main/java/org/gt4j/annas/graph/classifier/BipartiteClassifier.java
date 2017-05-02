package org.gt4j.annas.graph.classifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies if a graph is bipartite
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class BipartiteClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		for (Collection<V> c : Utilities.getConnectedComponents(graph)) {
			if( !new BreadthFirst<V, E>(graph, c.iterator().next())
					.isBipartite()){
				return false;
			}
		}
		return true;

	}

	/**
	 * Private class that computes the breadth first search labeling the
	 * vertices as being reachable from an odd or even length path from a
	 * vertex. If a vertex has an odd and even length path then the graph is not
	 * bipartite.
	 * 
	 * @author Sam Wilson
	 * 
	 * @param <V>
	 * @param <E>
	 */
	@SuppressWarnings("hiding")
	private class BreadthFirst<V, E extends EdgeInterface<V>> {

		private GraphInterface<V, E> graph;

		private LinkedList<V> Order;

		private HashMap<V, Integer> map;

		private boolean Bipartite = true;

		private V s;

		public BreadthFirst(GraphInterface<V, E> g, V s) {
			super();
			this.graph = g;
			this.s = s;
			this.map = new HashMap<V, Integer>();
			this.Order = new LinkedList<V>();
			this.BF(this.s);
		}

		private void BF(V n) {
			Queue<V> l = new LinkedList<V>();

			this.map.put(n, 0);
			l.offer(n);

			while (l.size() > 0) {
				V m = l.poll();
				this.Order.add(m);
				Set<E> edges = this.graph.getEdges(m);
				for (E e : edges) {

					V head = this.getOtherEndPoint(m, e);
					if (!this.Order.contains(head)) {

						l.offer(head);
					}

					if (!this.map.containsKey(head)) {
						this.map.put(head, this.label(this.map.get(m)));

					} else {
						if (this.map.get(this.getOtherEndPoint(m, e)) % 2 == this.map
								.get(m) % 2) {
							this.Bipartite = false;
						}
					}
				}
			}

		}

		private int label(int i) {
			return i + 1;
		}

		private V getOtherEndPoint(V tail, E edge) {

			if (edge.getHead() == edge.getTail()) {
				return edge.getHead();
			} else {
				if (edge.getHead() == tail) {
					return edge.getTail();
				} else {
					return edge.getHead();
				}
			}
		}

		public boolean isBipartite() {
			return this.Bipartite;
		}

	}

}
