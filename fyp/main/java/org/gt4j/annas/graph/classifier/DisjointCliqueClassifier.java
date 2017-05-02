package org.gt4j.annas.graph.classifier;

import java.util.Collection;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies if a graph is the disjoint union of cliques
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class DisjointCliqueClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		Collection<Collection<V>> C = Utilities.getConnectedComponents(graph);
		if(C.size()==0){
			return false;
		}
		for (Collection<V> c : C) {
			int d = c.size() - 1;
			for (V v : c) {
				if (graph.getDegree(v) != d) {
					return false;
				}

			}
		}
		return true;
	}

}
