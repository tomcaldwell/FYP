package org.gt4j.annas.graph.classifier;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Interface for all graph classifiers to implement
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface Classifier<V, E extends EdgeInterface<V>> {

	/**
	 * Classifies if the graph has a property.
	 * 
	 * @param graph graph to classify
	 * @return true is the graph has the property.
	 */
	public boolean classify(GraphInterface<V, E> graph);
}
