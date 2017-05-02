package org.gt4j.annas.graph.classifier;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Classifies if a graph is Eulerian. A graph is Eulerian if it is connected and
 * all vertices have even degree.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class EulerianClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		boolean eulerian = true;

		for (V vertex : graph.getVertices()) {
			eulerian &= graph.getDegree(vertex) % 2 == 0;
		}

		return eulerian && new ConnectedClassifier<V, E>().classify(graph);
	}

}
