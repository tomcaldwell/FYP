package org.gt4j.annas.graph.classifier;

import java.util.Hashtable;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * ? @ A? A_ B? BO BW
 * 
 * Classifies if a graph is acyclic
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Acyclic<V, E extends EdgeInterface<V>> implements Classifier<V, E> {

	/**
	 * Graph to detect cycles in
	 */
	protected GraphInterface<V, E> graph;

	private Hashtable<V, Integer> map;

	/**
	 * Default constructor
	 */
	public Acyclic() {
		super();
		this.map = new Hashtable<V, Integer>();
	}

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		this.graph = graph;
		return !containsCycle();
	}

	/**
	 * Executes the algorithm
	 * 
	 * @return true if the algorithm discovers a cycles
	 */
	private boolean containsCycle() {

		for (V node : this.graph.getVertices())
			this.map.put(node, -1);

		for (V node : this.graph.getVertices()) {
			if (this.map.get(node) == -1) {
				if (this.visit(node, node)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean visit(V node, V pred) {
		this.map.put(node, 0);

		for (E arc : this.graph.getEdges(node)) {
			V head = arc.getOtherEndpoint(node);
			if (!head.equals(pred)) {
				Integer h = this.map.get(head);
				if (h == 0) {
					return true;
				} else if (h == -1) {
					if (this.visit(head, node)) {
						return true;
					}
				}
			}
		}
		// this.map.put(node, 1);
		return false;

	}

}
