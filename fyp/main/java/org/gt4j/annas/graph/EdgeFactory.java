package org.gt4j.annas.graph;

import java.io.Serializable;

/**
 * Factory used to construct edge.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface EdgeFactory<V, E extends EdgeInterface<V>> extends
		Serializable {

	/**
	 * Construct a new edge.
	 * 
	 * @param tail
	 *            tail of edge
	 * 
	 * @param head
	 *            head of edge
	 * 
	 * @return newly constructed edge.
	 */
	public E create(V tail, V head);

	/**
	 * Gets the edge class
	 * 
	 * @return edge class
	 */
	public Class<?> getEdgeClass();

}
