package org.gt4j.annas.graph.classifier;

import org.gt4j.annas.exception.IncompatibleGraphException;
import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.util.BiconnectedComponent;

/**
 * @author Sam Wilson
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 *
 */
public class BiconnectedClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		if (graph.isDirected()) {
			throw new IncompatibleGraphException(
					"Biconnected component classifier is only applicable for Undirected graphs");
		}
		BiconnectedComponent<V, E> bic = new BiconnectedComponent<V, E>(
				(UndirectedGraph<V, E>) graph);
		return bic.isBiconnected();
	}

}