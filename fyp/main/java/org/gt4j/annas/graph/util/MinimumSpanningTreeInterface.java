package org.gt4j.annas.graph.util;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Interface for all minimum spanning tree algorithms
 * 
 * @author Sam Wilson
 *
 */
public interface MinimumSpanningTreeInterface<V, E extends EdgeInterface<V>> extends Algorithm<GraphInterface<V, E>> {

	/**
	 * Get the weight of the minimum spanning tree
	 * @return weight of a minimum spanning tree
	 */
	public double getWeight();
}
