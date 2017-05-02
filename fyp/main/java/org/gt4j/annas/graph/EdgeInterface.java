package org.gt4j.annas.graph;

/**
 * Interface of all edge used in a Graph
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public interface EdgeInterface<V> {

	/**
	 * Gets the head of the Edge
	 * 
	 * @return Vertex at the head of the edge
	 */
	public V getHead();

	/**
	 * Gets the tail of the Edge
	 * 
	 * @return Vertex at the tail of the edge
	 */
	public V getTail();

	/**
	 * Set head of the edge
	 * 
	 * @param vertex vertex to set to head
	 */
	public void setHead(V vertex);

	/**
	 * Set tail of the edge
	 * 
	 * @param vertex vertex to set to tail
	 */
	public void setTail(V vertex);

	/**
	 * Checks if the edge is incident to the given vertex
	 * 
	 * @param vertex vertex to check is incident 
	 * @return True if the edge is incident to the vertex, otherwise false
	 */
	public boolean isIncident(V vertex);

	/**
	 * Retrieves the other end point to the end.
	 * 
	 * @param vertex one end point of the edge
	 * @return the other end of an edge
	 */
	public V getOtherEndpoint(V vertex);


}
