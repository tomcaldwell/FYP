package org.gt4j.annas.graph;

import java.util.Set;

/**
 * Interface for directed graphs {@link org.gt4j.annas.graph.DirectedGraph}
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface DirectedGraphInterface<V, E extends EdgeInterface<V>> extends
		GraphInterface<V, E> {

	/**
	 * Get the edges into a vertex
	 * 
	 * @param vertex
	 *            head of the edges
	 * @return set of edges into a vertex
	 */
	public Set<E> getInEdges(V vertex);

	/**
	 * Get the edges out of a vertex
	 * 
	 * @param vertex
	 *            tail vertex
	 * @return set of edges out of a vertex
	 */
	public Set<E> getOutEdges(V vertex);

	/**
	 * Get the number of edges into a vertex
	 * 
	 * @param vertex vertex to get in degree of
	 * @return number of edges into a vertex
	 */
	public int getInDegree(V vertex);

	/**
	 * Get the number of edges out of a vertex
	 * 
	 * @param vertex vertex to get out degree of
	 * @return number of edges out of a vertex
	 */
	public int getOutDegree(V vertex);
}
