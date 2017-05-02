package org.gt4j.annas.graph.classifier;

import java.util.Collection;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies if a graph is connected
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class ConnectedClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		Collection<Collection<V>> c = Utilities.getConnectedComponents(graph);
		if (c.size() == 1 || graph.getOrder() == 0) {
			return true;
		}
		return false;
	}

}
