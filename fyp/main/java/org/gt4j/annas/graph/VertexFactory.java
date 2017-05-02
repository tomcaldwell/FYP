package org.gt4j.annas.graph;

/**
 * Factory for creating vertices
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public interface VertexFactory<V> {

	/**
	 * Creates a new vertex
	 * 
	 * @return a new vertex
	 */
	public V createVertex();

}
