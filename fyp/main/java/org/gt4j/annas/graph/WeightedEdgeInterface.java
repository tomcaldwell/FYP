package org.gt4j.annas.graph;

/**
 * Interface for weighted edges
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public interface WeightedEdgeInterface<V> extends EdgeInterface<V> {

	/**
	 * Gets the weight on the edge
	 * 
	 * @return weight of edge
	 */
	public double getWeight();

	/**
	 * Sets the weight on the edge
	 * 
	 * @param weight weight of the edge
	 */
	public void setWeight(double weight);
}
