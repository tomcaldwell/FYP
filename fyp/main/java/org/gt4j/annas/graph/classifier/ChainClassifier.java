package org.gt4j.annas.graph.classifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies if a graph is a Chain graph. A graph is a chain graph if it is
 * bipartite and the two colour classes can be ordered in such a way that the
 * vertices have the nested neighborhood property.
 * 
 * @author Sam Wilson
 *
 * @param <V>
 * @param <E>
 */
public class ChainClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	/**
	 * 
	 */
	public ChainClassifier() {
		super();
	}

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		BreadthFirst<V, E> bf;
		List<V> X = new ArrayList<V>();

		List<V> Y = new ArrayList<V>();

		for (Collection<V> c : Utilities.getConnectedComponents(graph)) {
			bf = new BreadthFirst<V, E>(graph, c.iterator().next());
			if (!bf.isBipartite()) {
				return false;
			}
			X.addAll(bf.X);
			Y.addAll(bf.Y);
		}

		X = X.stream().sorted(new Comparator<V>() {

			@Override
			public int compare(V o1, V o2) {
				return graph.getDegree(o2) - graph.getDegree(o1);
			}
		}).collect(Collectors.toList());

		Y = Y.stream().sorted(new Comparator<V>() {

			@Override
			public int compare(V o1, V o2) {
				return graph.getDegree(o2) - graph.getDegree(o1);
			}
		}).collect(Collectors.toList());

		List<V> tmp, tmp1;
		if (X.size() >= 2) {
			Iterator<V> i = X.iterator();
			final V s = i.next();
			tmp = graph.getEdges(s).stream().map(e -> e.getOtherEndpoint(s))
					.collect(Collectors.toList());
			while (i.hasNext()) {
				final V t = i.next();
				tmp1 = graph.getEdges(t).stream()
						.map(e -> e.getOtherEndpoint(t))
						.collect(Collectors.toList());
				if (!tmp.containsAll(tmp1)) {
					return false;
				}
				tmp = tmp1;
			}

		}

		if (Y.size() >= 2) {
			Iterator<V> i = Y.iterator();
			final V s = i.next();
			tmp = graph.getEdges(s).stream().map(e -> e.getOtherEndpoint(s))
					.collect(Collectors.toList());
			while (i.hasNext()) {
				final V t = i.next();
				tmp1 = graph.getEdges(t).stream()
						.map(e -> e.getOtherEndpoint(t))
						.collect(Collectors.toList());
				if (!tmp.containsAll(tmp1)) {
					return false;
				}
				tmp = tmp1;
			}

		}
		return true;
	}

	@SuppressWarnings("hiding")
	private class BreadthFirst<V, E extends EdgeInterface<V>> {

		private GraphInterface<V, E> graph;

		private LinkedList<V> Order;

		private List<V> X;

		private List<V> Y;

		private boolean Bipartite = true;

		private V s;

		public BreadthFirst(GraphInterface<V, E> g, V s) {
			super();
			this.graph = g;
			this.s = s;
			this.X = new ArrayList<V>();
			this.Y = new ArrayList<V>();
			this.Order = new LinkedList<V>();
			this.BF(this.s);
		}

		private void BF(V n) {
			Queue<V> l = new LinkedList<V>();

			this.X.add(n);
			l.offer(n);

			while (l.size() > 0) {
				V m = l.poll();
				this.Order.add(m);
				Set<E> edges = this.graph.getEdges(m);
				for (E e : edges) {

					V head = e.getOtherEndpoint(m);
					if (!this.Order.contains(head)) {

						l.offer(head);
					}

					if (!this.X.contains(head) && !this.Y.contains(head)) {
						if (this.X.contains(m)) {
							this.Y.add(head);
						} else {
							this.X.add(head);
						}

					} else {
						if (this.X.contains(head)) {
							if (this.X.contains(m)) {
								this.Bipartite = false;
							}
						} else {
							if (this.Y.contains(m)) {
								this.Bipartite = false;
							}
						}
					}
				}
			}

		}

		public boolean isBipartite() {
			return this.Bipartite;
		}

	}

}
