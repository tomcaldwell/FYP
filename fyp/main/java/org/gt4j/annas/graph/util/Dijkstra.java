package org.gt4j.annas.graph.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.WeightedEdgeInterface;
import org.gt4j.annas.misc.Path;

/**
 * Implementation of Dijkstra's algorithm for computing single source shortest
 * path.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Dijkstra<V, E extends EdgeInterface<V>> implements ShortestPathInterface<V, E> {

	private static final double DEFAULT_EDGEWEIGHT = 1.0;

	private final GraphInterface<V, E> graph;

	private final V src;

	private final V sink;

	private Map<V, Marker<V>> lookup;

	private PriorityQueue<Marker<V>> queue;

	/**
	 * Constructs a Dijkstra object with the given graph
	 * 
	 * @param graph
	 *            input graph
	 */
	public Dijkstra(final GraphInterface<V, E> graph, final V src, final V sink) {
		this.graph = graph;
		this.src = src;
		this.sink = sink;
		this.queue = new PriorityQueue<Marker<V>>(this.graph.getVertices().size());
		this.lookup = new Hashtable<V, Marker<V>>(this.graph.getVertices().size());
		for (V n : this.graph.getVertices()) {
			this.lookup.put(n, new Marker<V>(n));
		}
	}

	/**
	 * Executes Dijkstra's algorithm
	 *
	 * @return the shortest path from source to destination
	 */
	public Path<V, E> call() {
		int order = 0;
		Marker<V> m = this.lookup.get(this.src);
		m.setDistance(0);
		this.queue.add(m);
		Marker<V> tmp = m;

		while (tmp.getRepresenting() != this.sink && this.queue.peek() != null) {

			tmp = this.queue.poll();
			tmp.setOrder(order);
			order++;
			tmp.setPermenant();

			for (E edge : this.graph.getEdges(tmp.getRepresenting())) {

				double weight = this.getWeight(edge);
				double distanceThrutmo = tmp.getDistance() + weight;
				Marker<V> mm = this.lookup.get(edge.getOtherEndpoint(tmp.representing));
				if (distanceThrutmo < mm.getDistance()) {
					this.queue.remove(mm);
					mm.setDistance(distanceThrutmo);
					mm.setPrevious(tmp.getRepresenting());
					this.queue.add(mm);

				}
			}

		}
		Path<V, E> gp = new Path<V, E>();

		V tmppp = this.sink;
		V tmpp = null;
		ArrayList<V> path = new ArrayList<V>();
		path.add(tmppp);
		while (tmppp != this.src) {
			tmpp = this.lookup.get(tmppp).getPrevious();
			if (tmpp == null) {
				return gp;
			}
			path.add(tmpp);
			tmppp = tmpp;
		}

		Marker<V> d, s = null;
		for (int i = path.size() - 1; i != 0; i--) {
			d = this.lookup.get(path.get(i - 1));
			s = this.lookup.get(path.get(i));
			gp.addEdge(getEdge(d.representing, s.representing, d.getDistance() - s.getDistance()));
		}

		return gp;
	}

	private E getEdge(V tail, V head, double weight) {
		Collection<E> edges = this.graph.getEdges(tail, head);

		for (E e : edges) {
			if (this.getWeight(e) == weight) {
				return e;
			}
		}

		return null;
	}

	private double getWeight(EdgeInterface<V> e) {
		if (e instanceof WeightedEdgeInterface) {
			return ((WeightedEdgeInterface<V>) e).getWeight();
		}
		return DEFAULT_EDGEWEIGHT;
	}

	private class Marker<N> implements Comparable<Marker<N>> {

		private N representing;

		private int order;

		private double distance;

		private N previous;

		private boolean permenant;

		public Marker(N representing) {
			super();
			this.representing = representing;
			this.order = Integer.MAX_VALUE;
			this.distance = Double.POSITIVE_INFINITY;
			this.permenant = false;
		}

		public N getRepresenting() {
			return this.representing;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public void setPermenant() {
			this.permenant = true;
		}

		public double getDistance() {
			return this.distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}

		@Override
		public int compareTo(Marker<N> o) {
			return Double.compare(this.distance, o.getDistance());
		}

		public N getPrevious() {
			return this.previous;
		}

		public void setPrevious(N previous) {
			this.previous = previous;
		}

		@Override
		public String toString() {
			return "Marker [distance=" + this.distance + ", order=" + this.order + ", permenant=" + this.permenant
					+ ", previous=" + this.previous + ", representing=" + this.representing + ']';
		}

	}

}
