package org.gt4j.annas.graph.classifier;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Classifies if a graph is a complete graph
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class CompleteClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {

		for (V v : graph.getVertices()) {
			for (V u : graph.getVertices()) {
				if (u != v) {
					if (graph.getEdges(v, u).isEmpty()) {
						return false;
					}
				}
			}
		}

		return true;
	}

}
