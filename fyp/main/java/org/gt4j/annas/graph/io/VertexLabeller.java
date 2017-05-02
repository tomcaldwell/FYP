package org.gt4j.annas.graph.io;

/**
 * Gets a label for the supplied vertex.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public interface VertexLabeller<V> {

	/**
	 * Provides a label for the given vertex
	 * 
	 * @param v
	 *            Vertex to consider
	 * @return Label for the given vertex
	 */
	public String getLabel(V v);
}
