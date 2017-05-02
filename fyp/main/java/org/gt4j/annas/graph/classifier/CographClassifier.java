package org.gt4j.annas.graph.classifier;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Classifies if a graph is a cograph or not.
 * 
 * @author Sam Wilson
 * 
 * @param <V> Vertex type
 * @param <E> Edge type
 */
public class CographClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		//TODO Implement cograph recognition
		return false;
	}	
}