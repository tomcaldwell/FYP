package org.gt4j.annas.graph.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Class to calculate a set of cycles that form a cycle basis of the given
 * graph.
 * 
 * A cycle basis of a graph is a minimum set of cycles such that any cycle in
 * the graph can be expressed as the summation of cycles in the basis, in this
 * context summation refers to the symmetric different of the two cycles.
 * 
 * @author Sam Wilson
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 * 
 */
public class CycleBasis<V, E extends EdgeInterface<V>> implements Algorithm<Collection<Collection<V>>> {

	private GraphInterface<V, E> graph;

	/**
	 * Constructs a CycleBasis calculator for a given graph
	 * 
	 * @param graph
	 *            Input graph
	 */
	public CycleBasis(GraphInterface<V, E> graph) {
		super();
		this.graph = graph;
	}

	/**
	 * Calculates a minimum cycle basis
	 * 
	 * @return a list of lists containing vertices that for a cycle basis
	 */
	public Collection<Collection<V>> call() {
		Collection<Collection<V>> cycles = new ArrayList<Collection<V>>();
		Deque<V> vertices = new ArrayDeque<V>(this.graph.getVertices());

		V root = null;
		Deque<V> stack = null;
		Map<V, V> pred = null;
		Map<V, Collection<V>> used = null;
		Collection<V> zused = null;
		V z = null;
		Collection<V> forget = new ArrayList<V>();

		for (V v : vertices) {
			if (!forget.contains(v)) {
				stack = new ArrayDeque<V>();
				pred = new Hashtable<V, V>();
				used = new Hashtable<V, Collection<V>>();
				if (root == null) {
					root = vertices.pop();
				}
				stack.push(root);
				pred.put(root, root);
				used.put(root, new ArrayList<V>());
				while (!stack.isEmpty()) {
					z = stack.pop();
					zused = used.get(z);

					for (V nbr : Utilities.getOpenNeighbourhood(this.graph, z)) {

						if (!used.containsKey(nbr)) {
							pred.put(nbr, z);
							stack.push(nbr);
							Collection<V> tmp = new ArrayList<V>();
							tmp.add(z);
							used.put(nbr, tmp);
						} else if (nbr.equals(z)) {
							List<V> tmp = new ArrayList<V>();
							tmp.add(z);
							cycles.add(tmp);
						} else if (!zused.contains(nbr)) {
							Collection<V> pn = used.get(nbr);
							List<V> cycle = new ArrayList<V>();
							cycle.add(nbr);
							cycle.add(z);
							V p = pred.get(z);

							while (!pn.contains(p)) {
								cycle.add(p);
								p = pred.get(p);
							}
							cycle.add(p);
							cycles.add(cycle);
							used.get(nbr).add(z);
						}
					}
				}
				forget.addAll(pred.keySet());
				root = null;
			}

		}

		return cycles;
	}

}
