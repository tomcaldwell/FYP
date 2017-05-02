package org.gt4j.annas.graph.classifier;

import java.util.Collection;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies if a graph is a tree (connected and acyclic)
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class TreeClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		Collection<?> comps = Utilities.getConnectedComponents(graph);

		return comps.size() == 1 && new Acyclic<V, E>().classify(graph);
	}

}
