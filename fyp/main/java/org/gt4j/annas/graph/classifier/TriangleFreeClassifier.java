package org.gt4j.annas.graph.classifier;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;
import org.gt4j.annas.math.Matrix;

/**
 * Classifies if a graph is triangle free with respect to induced subgraphs
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class TriangleFreeClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		if(graph.getOrder()<3){
			return true;
		}
		Matrix m = Utilities.getAdjacencyMatrix(graph);

		m = m.pow(3);
		if (m.getTrace() != 0)
			return false;
		return true;
	}
}
