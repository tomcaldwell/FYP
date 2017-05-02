package org.gt4j.annas.graph.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

import org.gt4j.annas.graph.DirectedGraph;
import org.gt4j.annas.graph.WeightedEdgeInterface;

/**
 * An implementation of Ford-Fulkerson's algorithm for computing the maximum
 * flow in a network
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class FordFulkerson<V, E extends WeightedEdgeInterface<V>> implements MaximumFlowInterface<Double> {

	/**
	 * Holds the flow
	 */
	private Hashtable<E, Double> flow;

	/**
	 * Network
	 */
	private final DirectedGraph<V, E> graph;

	/**
	 * Source
	 */
	private V source;

	/**
	 * Sink
	 */
	private V sink;

	/**
	 * Constructs a Ford-Fulkerson's object for computing maximum flow on the
	 * given graph
	 * 
	 * @param graph
	 *            graph to compute the maximum flow on
	 * @param source
	 *            source of flow
	 * @param sink
	 *            sink of flow
	 */
	public FordFulkerson(final DirectedGraph<V, E> graph, final V source, final V sink) {

		this.graph = graph;
		this.sink = sink;
		this.source = source;
		this.flow = new Hashtable<E, Double>(graph.getSize());
		for (E e : graph.getEdges()) {
			this.flow.put(e, 0d);
		}
	}

	/**
	 * Computes the Maximum flow for the given graph between Sink and Source
	 * 
	 * @return Maximum flow
	 */
	public Double call() {
		ArrayList<E> gp = this.findAugmentingPath(this.source, this.sink, new ArrayList<E>());
		while (gp != null) {
			double leeway = this.getLeeway(gp);
			updateFlow(gp, leeway);
			gp = this.findAugmentingPath(this.source, this.sink, new ArrayList<E>());

		}

		return this.calculateMaxFlow();
	}

	/**
	 * Gets the flow along an edge (once the algorithm has been executed,
	 * otherwise returns 0).
	 * 
	 * @param e
	 *            Edge
	 * @return the flow on an edge
	 */
	public double getFlow(E e) {
		return this.flow.get(e);
	}

	private double calculateMaxFlow() {
		double retval = 0;

		for (E e : this.graph.getEdges(this.source)) {
			retval += this.flow.get(e);
		}
		return retval;
	}

	private void updateFlow(ArrayList<E> gp, double leeway) {
		Iterator<E> it = gp.iterator();
		E e = it.next();
		E tmp = null;
		if (e.getTail() == this.source) {
			this.flow.put(e, this.flow.get(e) + leeway);
		} else {
			this.flow.put(e, this.flow.get(e) - leeway);
		}
		while (it.hasNext()) {
			tmp = it.next();
			if (e.getHead() == tmp.getTail()) {
				this.flow.put(tmp, this.flow.get(tmp) + leeway);
			} else {
				this.flow.put(tmp, this.flow.get(tmp) - leeway);
			}
			e = tmp;
		}
	}

	private ArrayList<E> findAugmentingPath(V source, V sink, ArrayList<E> path) {
		if (source == sink) {
			return path;
		} else {
			for (E e : this.getEdge(source)) {

				if (e.getTail() == source) {
					double res = e.getWeight() - this.flow.get(e);
					if (res > 0 && !path.contains(e)) {
						ArrayList<E> copy = new ArrayList<E>(path);
						copy.add(e);
						ArrayList<E> result = this.findAugmentingPath(e.getHead(), sink, copy);
						if (result != null) {
							return result;
						}
					}
				} else {
					double res = this.flow.get(e);
					if (res > 0 && !path.contains(e)) {
						ArrayList<E> copy = new ArrayList<E>(path);
						copy.add(e);
						ArrayList<E> result = this.findAugmentingPath(e.getHead(), sink, copy);
						if (result != null) {
							return result;
						}
					}
				}
			}
		}
		return null;
	}

	private ArrayList<E> getEdge(V v) {
		ArrayList<E> edges = new ArrayList<E>();
		for (E e : this.graph.getEdges()) {
			if (e.getHead() == v || e.getTail() == v) {
				edges.add(e);
			}
		}
		return edges;
	}

	private double getLeeway(ArrayList<E> gp) {
		ArrayList<Double> leeway = new ArrayList<Double>();

		Iterator<E> it = gp.iterator();
		E e = it.next();
		E tmp = null;
		if (e.getTail() == this.source) {
			leeway.add(this.getExcess(e));
		} else {
			leeway.add(this.flow.get(e));
		}
		while (it.hasNext()) {
			tmp = it.next();
			if (e.getHead() == tmp.getTail()) {
				leeway.add(this.getExcess(tmp));
			} else {
				leeway.add(this.flow.get(tmp));
			}
			e = tmp;
		}

		Collections.sort(leeway);
		return leeway.get(0);
	}

	private double getExcess(E e) {
		return e.getWeight() - this.flow.get(e);
	}

}
