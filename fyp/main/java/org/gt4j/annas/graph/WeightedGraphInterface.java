package org.gt4j.annas.graph;

/**
 * Interface for weighted graphs
 * 
 * @author Sam Wilson
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 * 
 */
public interface WeightedGraphInterface<V, E extends WeightedEdgeInterface<V>>
		extends GraphInterface<V, E> {

}
